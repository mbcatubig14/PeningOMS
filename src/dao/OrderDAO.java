package dao;

import dbutil.DbUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Customer;
import model.Employee;
import model.DetailedOrder;
import model.Order;
import model.Product;

/**
 *
 * @author Muhammad
 */
public class OrderDAO implements OrderDAOInterface {

    private PreparedStatement allOrders, allOrderDetailsByOrderId, anOrder, createOrder, placeOrder, updateTotal;
    private PreparedStatement deleteAnOrder;

    @Override
    public int placeOrder(List<DetailedOrder> orderDetailsList, BigDecimal total) {

        //Place order sql statement
        String placeOrderSql = "INSERT INTO order_details (order_id, product_id, quantity, total, order_description) VALUES (?, ?, ?, ?, ?)";

        ResultSet rs = null;
        String lastIdQuery = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";

        String updateTotalQuery = "UPDATE orders SET total = ? WHERE order_id = ?";

        int placed = 0;

        //Connect to the database
        Connection conn = DbUtil.getConnection();
        try {

            //last generated orderId from orders table
            Statement lastIdStatement = conn.createStatement();
            rs = lastIdStatement.executeQuery(lastIdQuery);
            int lastInsertedOrderId = 0;
            if (rs.next()) {
                lastInsertedOrderId = rs.getInt(1);
            }

            //Update table orders total
            updateTotal = conn.prepareStatement(updateTotalQuery);
            updateTotal.setBigDecimal(1, total);
            updateTotal.setInt(2, lastInsertedOrderId);
            updateTotal.executeUpdate();

            placeOrder = conn.prepareStatement(placeOrderSql);

            //Confirm the order
            int confirmed = JOptionPane.showConfirmDialog(null,
                    "You're placing an order for " + lastInsertedOrderId + ", is that correct?",
                    "Place Order Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            for (DetailedOrder orderDetails : orderDetailsList) {

                //Initialise order details attributes
                int productId = orderDetails.getProducts().getProductId(), quantity = orderDetails.getQuantity();
                BigDecimal subTotal = orderDetails.getTotal();
                String orderDescription = orderDetails.getOrderDescription();

                //Set parameters
                placeOrder.setInt(1, lastInsertedOrderId);
                placeOrder.setInt(2, productId);
                placeOrder.setInt(3, quantity);
                placeOrder.setBigDecimal(4, subTotal);
                placeOrder.setString(5, orderDescription);

                if (confirmed == JOptionPane.YES_OPTION) {
                    placed = placeOrder.executeUpdate();
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(conn, placeOrder, rs);
        }

        return placed;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> ordersList = new ArrayList<>();

        CustomerDAO customerDao = new CustomerDAO();
        EmployeeDAO employeeDao = new EmployeeDAO();

        String allOrdersSql = "SELECT * FROM orders";

        Connection conn = DbUtil.getConnection();
        ResultSet rs = null;

        try {
            allOrders = conn.prepareStatement(allOrdersSql);
            rs = allOrders.executeQuery();

            while (rs.next()) {
                Order orders = new Order();
                final Employee employee = (Employee) employeeDao.getPersonById(rs.getInt("employee_id"));
                final Customer customer = (Customer) customerDao.getPersonById(rs.getInt("customer_id"));

                orders.setOrderId(rs.getInt("order_id"));
                orders.setCourierName(rs.getString("courier_name"));
                orders.setTotal(rs.getBigDecimal("total"));
                orders.setOrderDate(rs.getDate("order_date"));
                orders.setCustomer(customer);
                orders.setEmployee(employee);

                ordersList.add(orders);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(conn, allOrders, rs);
        }

        return ordersList;
    }

    @Override
    public List<DetailedOrder> getAllOrderDetailsByOrderId(int id) {
        List<DetailedOrder> orderDetailsList = new ArrayList<>();

        String allOrderDetailsSql = "SELECT * FROM order_details WHERE order_id = " + id;

        Connection conn = DbUtil.getConnection();
        ResultSet rs = null;

        try {
            allOrderDetailsByOrderId = conn.prepareStatement(allOrderDetailsSql);
            rs = allOrderDetailsByOrderId.executeQuery();

            while (rs.next()) {
                ProductDAO productDao = new ProductDAO();
                final Product productsById = productDao.getProductsById(rs.getInt("product_id"));
                final Order order = getOrderById(rs.getInt("order_id"));

                DetailedOrder orderDetails = new DetailedOrder();
                orderDetails.setOrderDetailsId(rs.getInt("order_details_id"));
                orderDetails.setOrderDescription(rs.getString("order_description"));
                orderDetails.setTotal(rs.getBigDecimal("total"));
                orderDetails.setQuantity(rs.getInt("quantity"));
                orderDetails.setProducts(productsById);

                orderDetails.setOrders(order);

                orderDetailsList.add(orderDetails);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(conn, allOrderDetailsByOrderId, rs);
        }

        return orderDetailsList;
    }

    @Override
    public int createOrder(Order order) {

        //Insert statement for creating order
        String insertOrderSql = "INSERT INTO orders (customer_id, employee_id, courier_name, order_date) \n"
                + "SELECT customers.customer_id,employees.employee_id, ?, ? \n"
                + "FROM customers,employees,employees AS couriers \n"
                + "WHERE customers.first_name = ? AND customers.last_name = ? AND\n"
                + "  employees.first_name = ? AND employees.last_name = ? AND\n"
                + "couriers.first_name = ? AND couriers.last_name = ?\n"
                + "  ";

        //Get connection to the database
        Connection conn = DbUtil.getConnection();

        //Convert date to sql date
        java.sql.Date sqlDate = new java.sql.Date(order.getOrderDate().getTime());

        //Get sub strings for firstname and lastname of the courier
        String courierName = order.getCourierName();
        String courierLastName = courierName.substring(0, courierName.indexOf(",")).trim(),
                courierFirstName = courierName.substring(courierName.indexOf(",") + 1).trim();
        int created = 0;
        try {

            //Prepate statement and set parameters
            createOrder = conn.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS);
            createOrder.setString(1, order.getCourierName());
            createOrder.setDate(2, sqlDate);
            createOrder.setString(3, order.getCustomer().getFirstName());
            createOrder.setString(4, order.getCustomer().getLastName());
            createOrder.setString(5, order.getEmployee().getFirstName());
            createOrder.setString(6, order.getEmployee().getLastName());
            createOrder.setString(7, courierFirstName);
            createOrder.setString(8, courierLastName);

            //Confirm the order
            int confirmed = JOptionPane.showConfirmDialog(null,
                    "You're creating order for " + order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName() + ", is that correct?",
                    "Create Order Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirmed == JOptionPane.YES_OPTION) {
                //If yes execute create order statement
                created = createOrder.executeUpdate();

            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(conn, createOrder, null);
        }

        return created;
    }

    private Order getOrderById(int orderId) {
        Order orders = null;

        Connection conn = DbUtil.getConnection();
        ResultSet rs = null;

        CustomerDAO customerDao = new CustomerDAO();
        EmployeeDAO employeeDao = new EmployeeDAO();

        try {
            anOrder = conn.prepareStatement("SELECT * FROM orders WHERE order_id = ?");
            anOrder.setInt(1, orderId);
            rs = anOrder.executeQuery();

            if (rs.next()) {
                orders = new Order();
                final Employee employee = (Employee) employeeDao.getPersonById(rs.getInt("employee_id"));
                final Customer customer = (Customer) customerDao.getPersonById(rs.getInt("customer_id"));

                orders.setOrderId(rs.getInt("order_id"));
                orders.setCourierName(rs.getString("courier_name"));
                orders.setTotal(rs.getBigDecimal("total"));
                orders.setOrderDate(rs.getDate("order_date"));
                orders.setCustomer(customer);
                orders.setEmployee(employee);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(conn, anOrder, rs);
        }

        return orders;
    }

    public int cancelOrder(int orderId) {
        String deleteOrderSql = "DELETE FROM orders WHERE order_id = ?";

        Connection conn = DbUtil.getConnection();
        int deleted = 0;
        try {
            deleteAnOrder = conn.prepareStatement(deleteOrderSql);
            deleteAnOrder.setInt(1, orderId);

            int confirmed = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete this order?",
                    "Delete Order Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirmed == JOptionPane.YES_OPTION) {
                deleted = deleteAnOrder.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deleted;
    }

    public BigDecimal totalFrom(Date dateCommencing) {
        BigDecimal total = null;
        String totalSql = "SELECT sum(total) FROM peningoms.orders WHERE order_date BETWEEN ? AND CURDATE()";
        Connection conn = DbUtil.getConnection();
        ResultSet rs = null;
        PreparedStatement sumStatement = null;
        try {
            sumStatement = conn.prepareStatement(totalSql);
            sumStatement.setDate(1, new java.sql.Date(dateCommencing.getTime()));
            rs = sumStatement.executeQuery();

            if (rs.next()) {
                total = rs.getBigDecimal("sum(total)");
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(conn, sumStatement, rs);
        }
        return total;
    }

}
