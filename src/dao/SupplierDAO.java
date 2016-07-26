/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dbutil.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Supplier;

/**
 *
 * @author Muhammad
 */
public class SupplierDAO implements SupplierDAOInterface {

    private PreparedStatement aSupplier, allSuppliers, insertSupplier, updateSupplier, deleteSupplier;

    @Override
    public Supplier getSupplierById(int id) {

        //Select a supplier sql statement
        String sql = "SELECT * FROM suppliers WHERE supplier_id=?";

        //Initialise supplier object
        Supplier supplier = null;

        //Get connection to database
        Connection conn = DbUtil.getConnection();

        //Initialise resultset
        ResultSet resultSet = null;

        try {

            //Prepare statement with set parameters and execute
            aSupplier = conn.prepareStatement(sql);
            aSupplier.setInt(1, id);
            resultSet = aSupplier.executeQuery();

            //If found get supplier's data
            if (resultSet.next()) {
                int supplierId = resultSet.getInt("supplier_id");
                String supplierName = resultSet.getString("supplier_name"), contactName = resultSet.getString("supplier_contact_name"), supplierAddress = resultSet.getString("supplier_address"), contactNo = resultSet.getString("supplier_contact_number");
                supplier = new Supplier(supplierId, supplierName, contactName, supplierAddress, contactNo);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SupplierDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //Close connection
            DbUtil.close(conn, aSupplier, resultSet);
        }

        return supplier;
    }

    @Override
    public List<Supplier> getSuppliers() {
        //Select a supplier sql statement
        String sql = "SELECT * FROM suppliers ORDER BY supplier_name";

        //Initialise list
        List<Supplier> suppliers = new ArrayList<>();

        //Get connection to database
        Connection conn = DbUtil.getConnection();

        //Initialise resultset
        ResultSet resultSet = null;

        try {

            //Prepare statement with set parameters and execute
            allSuppliers = conn.prepareStatement(sql);
            resultSet = allSuppliers.executeQuery();

            //Loop through and get supplier's data
            while (resultSet.next()) {
                int supplierId = resultSet.getInt("supplier_id");
                String supplierName = resultSet.getString("supplier_name"), contactName = resultSet.getString("supplier_contact_name"), supplierAddress = resultSet.getString("supplier_address"), contactNo = resultSet.getString("supplier_contact_number");
                Supplier supplier = new Supplier(supplierId, supplierName, contactName, supplierAddress, contactNo);
                suppliers.add(supplier);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SupplierDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //Close connection
            DbUtil.close(conn, aSupplier, resultSet);
        }

        return suppliers;
    }

    @Override
    public int insertSupplier(Supplier supplier) {

        //find a supplier
        String checkAllSuppliers = "SELECT * FROM suppliers WHERE supplier_name = ?";

        //Insert supplier and supplier id where supplier exist in suppliers table sql statement
        String insertSql = "INSERT INTO suppliers (supplier_name, supplier_contact_name, supplier_address, supplier_contact_number) VALUES (?,?,?,?)";

        //Get connection
        Connection conn = DbUtil.getConnection();

        //Resultset for checking existing suppliers
        ResultSet resultSet = null;

        int inserted = 0;

        try {

            //Prepare check all suppliers statement
            allSuppliers = conn.prepareStatement(checkAllSuppliers);
            allSuppliers.setString(1, supplier.getSupplierName());
            resultSet = allSuppliers.executeQuery();

            //If doesn't exist in suppliers table
            if (!resultSet.next()) {
                //Prepare insert statement
                insertSupplier = conn.prepareStatement(insertSql);
                //Get supplier parameter's data
                insertSupplier.setString(1, supplier.getSupplierName());
                insertSupplier.setString(2, supplier.getSupplierContactName());
                insertSupplier.setString(3, supplier.getSupplierAddress());
                insertSupplier.setString(4, supplier.getSupplierContactNumber());

                //Confirm insert
                int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to insert this supplier?", "Insert Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirmation == JOptionPane.YES_OPTION) {
                    //execute insert
                    inserted = insertSupplier.executeUpdate();
                }

            }//Else don't insert and show error messages.
            else {
                JOptionPane.showMessageDialog(null, "Supplier already exists.", "Invalid insert.", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(conn, allSuppliers, resultSet);
            DbUtil.close(conn, insertSupplier, resultSet);
        }

        return inserted;
    }

    @Override
    public int updateSupplier(int id, Supplier supplier) {
        int updated = 0;

        //find a supplier
        String checkAllSuppliers = "SELECT * FROM suppliers WHERE supplier_name = ?";

        //Insert supplier and supplier id where supplier exist in suppliers table sql statement
        String updateSql = "UPDATE suppliers SET supplier_name = ?, supplier_contact_name = ?, supplier_address = ?, supplier_contact_number = ? WHERE supplier_id = ?";

        //Get connection
        Connection conn = DbUtil.getConnection();

        //Resultset for checking existing suppliers
        ResultSet resultSet = null;

        try {

            //Prepare check all suppliers statement
            allSuppliers = conn.prepareStatement(checkAllSuppliers);
            allSuppliers.setString(1, supplier.getSupplierName());
            resultSet = allSuppliers.executeQuery();

            //If doesn't exist in suppliers table
            if (!resultSet.next()) {
                //Prepare insert statement
                updateSupplier = conn.prepareStatement(updateSql);
                //Get supplier parameter's data
                updateSupplier.setString(1, supplier.getSupplierName());
                updateSupplier.setString(2, supplier.getSupplierContactName());
                updateSupplier.setString(3, supplier.getSupplierAddress());
                updateSupplier.setString(4, supplier.getSupplierContactNumber());
                updateSupplier.setInt(5, id);

                //Confirm update
                int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this supplier?", "Update Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirmation == JOptionPane.YES_OPTION) {
                    //execute insert
                    updated = updateSupplier.executeUpdate();
                }

            }//Else don't insert and show error messages.
            else {
                JOptionPane.showMessageDialog(null, "Supplier already exists.", "Invalid update.", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(conn, allSuppliers, resultSet);
            DbUtil.close(conn, updateSupplier, resultSet);
        }
        return updated;
    }

    @Override
    public int deleteSupplier(int id) {
        int deleted = 0;

        //Delete supplier sql statement
        String deleteSQL = "DELETE FROM suppliers WHERE supplier_id = ?";

        //Get connection
        Connection conn = DbUtil.getConnection();
        try {

            //Prepare delete statement
            deleteSupplier = conn.prepareStatement(deleteSQL);
            deleteSupplier.setInt(1, id);

            //Confirm delete
            int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this supplier?", "Delete Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirmation == JOptionPane.YES_OPTION) {
                //execute insert
                deleted = deleteSupplier.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            //Close db connection
            DbUtil.close(conn, deleteSupplier, null);
        }

        return deleted;
    }

    @Override
    public Supplier getSupplierByName(String productSupplierName) {
        //Select a supplier sql statement
        String sql = "SELECT * FROM suppliers WHERE supplier_name=?";

        //Initialise supplier object
        Supplier supplier = null;

        //Get connection to database
        Connection conn = DbUtil.getConnection();

        //Initialise resultset
        ResultSet resultSet = null;

        try {

            //Prepare statement with set parameters and execute
            aSupplier = conn.prepareStatement(sql);
            aSupplier.setString(1, productSupplierName);
            resultSet = aSupplier.executeQuery();

            //If found get supplier's data
            if (resultSet.next()) {
                int supplierId = resultSet.getInt("supplier_id");
                String supplierName = resultSet.getString("supplier_name"), contactName = resultSet.getString("supplier_contact_name"), supplierAddress = resultSet.getString("supplier_address"), contactNo = resultSet.getString("supplier_contact_number");
                supplier = new Supplier(supplierId, supplierName, contactName, supplierAddress, contactNo);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SupplierDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //Close connection
            DbUtil.close(conn, aSupplier, resultSet);
        }

        return supplier;
    }

}
