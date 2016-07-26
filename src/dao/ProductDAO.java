
package dao;

import dbutil.DbUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Product;
import model.Supplier;

/**
 *
 * @author Muhammad
 */
public class ProductDAO implements ProductDAOInterface {

    private PreparedStatement aProduct, allProducts, addProduct, updateProduct, deleteProduct;

    @Override
    public Product getProductsById(int id) {

        //Select a product statement
        String sql = "SELECT * FROM products, suppliers WHERE products.product_id=? AND products.supplier_id = suppliers.supplier_id";

        //Initialise product object and result set
        Product product = null;
        ResultSet resultSet = null;

        //Get connection   
        Connection conn = DbUtil.getConnection();

        try {
            //Prepare Statement
            aProduct = conn.prepareStatement(sql);
            aProduct.setInt(1, id);
            resultSet = aProduct.executeQuery();

            //If found one
            if (resultSet.next()) {

                //Get product data
                int productId = resultSet.getInt("product_id"),
                        numberOfStocks = resultSet.getInt("number_of_stocks");
                BigDecimal pricePerUnit = resultSet.getBigDecimal("price_per_unit");
                String productName = resultSet.getString("product_name"), productType = resultSet.getString("product_type"), packagingType = resultSet.getString("packaging_type");

                //Get product's supplier's data
                String supplierName = resultSet.getString("supplier_name"), contactName = resultSet.getString("supplier_contact_name"),
                        address = resultSet.getString("supplier_address"), contactNumber = resultSet.getString("supplier_contact_number");

                //Create new supplier object
                Supplier supplier = new Supplier(supplierName, contactName, address, contactNumber);

                //Create new product object
                product = new Product(productId, productName, productType, numberOfStocks, pricePerUnit, packagingType, supplier);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //Close connection
            DbUtil.close(conn, aProduct, resultSet);
        }

        return product;
    }

    @Override
    public List<Product> getProducts() {
        //Select all product statement
        String sql = "SELECT * FROM products, suppliers WHERE products.supplier_id = suppliers.supplier_id";

        //Initialise product object and result set
        ResultSet resultSet = null;
        List<Product> productList = new ArrayList<>();

        //Get connection   
        Connection conn = DbUtil.getConnection();

        try {
            //Prepare Statement
            allProducts = conn.prepareStatement(sql);
            resultSet = allProducts.executeQuery();

            //If found one
            while (resultSet.next()) {

                //Get product data
                int productId = resultSet.getInt("product_id"),
                        numberOfStocks = resultSet.getInt("number_of_stocks");
                BigDecimal pricePerUnit = resultSet.getBigDecimal("price_per_unit");
                String productName = resultSet.getString("product_name"), productType = resultSet.getString("product_type"), packagingType = resultSet.getString("packaging_type");

                //Get product's supplier's data
                String supplierName = resultSet.getString("supplier_name"), contactName = resultSet.getString("supplier_contact_name"),
                        address = resultSet.getString("supplier_address"), contactNumber = resultSet.getString("supplier_contact_number");

                //Create new supplier object
                Supplier supplier = new Supplier(supplierName, contactName, address, contactNumber);

                //Create new product object
                Product product = new Product(productId, productName, productType, numberOfStocks, pricePerUnit, packagingType, supplier);

                //Add to list
                productList.add(product);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //Close connection
            DbUtil.close(conn, allProducts, resultSet);
        }

        return productList;
    }

    @Override
    public int addProduct(Product product) {

        //find a product
        String checkAllProducts = "SELECT * FROM products WHERE product_name = ?";

        //Insert product and supplier id where supplier exist in suppliers table sql statement
        String insertSql = "INSERT INTO products (product_name, product_type, supplier_id, number_of_stocks, price_per_unit, packaging_type) SELECT ?,?,suppliers.supplier_id,?,?,? FROM suppliers WHERE suppliers.supplier_name = ?";

        //Get connection
        Connection conn = DbUtil.getConnection();

        //Resultset for checking existing products
        ResultSet resultSet = null;

        int inserted = 0;

        try {

            //Prepare check all products statement
            allProducts = conn.prepareStatement(checkAllProducts);
            allProducts.setString(1, product.getProductName());
            resultSet = allProducts.executeQuery();

            //If doesn't exist in products table
            if (!resultSet.next()) {
                //Prepare insert statement
                addProduct = conn.prepareStatement(insertSql);
                //Get product parameter's data
                addProduct.setString(1, product.getProductName());
                addProduct.setString(2, product.getProductType());
                addProduct.setInt(3, product.getNumberOfStocks());
                addProduct.setBigDecimal(4, product.getPricePerUnit());
                addProduct.setString(5, product.getPackagingType());
                addProduct.setString(6, product.getSuppliers().getSupplierName());

                //Confirm insert
                int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to insert this product?", "Insert Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirmation == JOptionPane.YES_OPTION) {
                    //execute insert
                    inserted = addProduct.executeUpdate();
                }

            }//Else don't insert and show error messages.
            else {
                JOptionPane.showMessageDialog(null, "Product already exists.", "Invalid insert.", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(conn, allProducts, resultSet);
            DbUtil.close(conn, addProduct, resultSet);
        }

        return inserted;
    }

    @Override
    public int updateProduct(int id, Product product) {
        int updated = 0;

        //find a product
        String checkAllProducts = "SELECT * FROM products WHERE product_name = ?";

        //Insert product and supplier id where supplier exist in suppliers table sql statement
        String updateSql = "UPDATE products SET product_name = ?, product_type = ?, supplier_id = (SELECT supplier_id FROM suppliers WHERE suppliers.supplier_name = ?), number_of_stocks = ?, price_per_unit = ?, packaging_type = ? WHERE product_id = ?";

        //Get connection
        Connection conn = DbUtil.getConnection();

        //Resultset for checking existing products
        ResultSet resultSet = null;

        try {

            //Prepare check all products statement
            allProducts = conn.prepareStatement(checkAllProducts);
            allProducts.setString(1, product.getProductName());
            resultSet = allProducts.executeQuery();

            //If doesn't exist in products table
            if (!resultSet.next()) {
                //Prepare insert statement
                updateProduct = conn.prepareStatement(updateSql);
                //Get product parameter's data
                updateProduct.setString(1, product.getProductName());
                updateProduct.setString(2, product.getProductType());
                updateProduct.setString(3, product.getSuppliers().getSupplierName());
                updateProduct.setInt(4, product.getNumberOfStocks());
                updateProduct.setBigDecimal(5, product.getPricePerUnit());
                updateProduct.setString(6, product.getPackagingType());
                updateProduct.setInt(7, id);

                //Confirm update
                int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this product?", "Update Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirmation == JOptionPane.YES_OPTION) {
                    //execute insert
                    updated = updateProduct.executeUpdate();
                }

            }//Else don't insert and show error messages.
            else {
                JOptionPane.showMessageDialog(null, "Product already exists.", "Invalid update.", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(conn, allProducts, resultSet);
            DbUtil.close(conn, updateProduct, resultSet);
        }
        return updated;
    }

    public int updateStock(int productId, int quantity) {
        String selectNoOfStocksSql = "SELECT number_of_stocks FROM products WHERE product_id = ?";
        String updateStockSql = "UPDATE products SET number_of_stocks = ? WHERE product_id = ?";

        Connection conn = DbUtil.getConnection();
        int stockUpdated = 0;
        try {
            PreparedStatement selectNoOfStocksStmt = conn.prepareStatement(selectNoOfStocksSql);
            selectNoOfStocksStmt.setInt(1, productId);

            ResultSet resultSet = selectNoOfStocksStmt.executeQuery();
            int noOfStocks = 0;
            if (resultSet.next()) {
                noOfStocks = resultSet.getInt("number_of_stocks");
            }

            PreparedStatement updateStockStmt = conn.prepareStatement(updateStockSql);
            updateStockStmt.setInt(1, noOfStocks - quantity);
            updateStockStmt.setInt(2, productId);
            stockUpdated = updateStockStmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stockUpdated;
    }

    @Override
    public int deleteProduct(int id) {
        int deleted = 0;

        //Delete product sql statement
        String deleteSQL = "DELETE FROM products WHERE product_id = ?";

        //Get connection
        Connection conn = DbUtil.getConnection();
        try {

            //Prepare delete statement
            deleteProduct = conn.prepareStatement(deleteSQL);
            deleteProduct.setInt(1, id);

            //Confirm delete
            int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this product?", "Delete Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirmation == JOptionPane.YES_OPTION) {
                //execute insert
                deleted = deleteProduct.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            //Close db connection
            DbUtil.close(conn, deleteProduct, null);
        }

        return deleted;
    }

}
