/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.math.BigDecimal;
import java.util.List;
import model.DetailedOrder;
import model.Order;

/**
 *
 * @author Muhammad
 */
public interface OrderDAOInterface {

    public int createOrder(Order order);

    public int placeOrder(List<DetailedOrder> orderDetails, BigDecimal total);

    public List<Order> getAllOrders();

    public List<DetailedOrder> getAllOrderDetailsByOrderId(int id);
}
