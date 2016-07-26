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
import model.Customer;

import model.Person;

/**
 * Represents the data access object for customer
 * @author Muhammad
 */
public class CustomerDAO extends PersonDAO {

    private PreparedStatement aCustomer;
    private PreparedStatement allCustomers;
    private PreparedStatement addCustomer;
    private PreparedStatement updateCustomer;
    private PreparedStatement removeCustomer;

    @Override
    public Person getPersonById(int id) {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";

        Connection conn = DbUtil.getConnection();
        ResultSet resultSet = null;
        Customer person = null;
        try {
            aCustomer = conn.prepareStatement(sql);
            aCustomer.setInt(1, id);
            resultSet = aCustomer.executeQuery();

            while (resultSet.next()) {
                Integer customerId = resultSet.getInt("customer_id");

                String firstName = resultSet.getString("first_name"), lastName = resultSet.getString("last_name"), firstLineAddress = resultSet.getString("first_line_address"),
                        postcode = resultSet.getString("postcode"), contactNumber = resultSet.getString("contact_number"), gender = resultSet.getString("gender");

                person = new Customer(customerId, firstName, lastName, firstLineAddress, postcode, contactNumber, gender);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(conn, aCustomer, resultSet);
        }

        return person;
    }

    @Override
    public List<Person> getPersons() {
        //Select all rows in customers table sql statement
        String sql = "SELECT * FROM customers";

        //Connect to the database
        Connection conn = DbUtil.getConnection();

        //Initialise result set
        ResultSet resultSet = null;

        //Create a list of customers
        List<Person> customersList = new ArrayList<>();
        try {

            allCustomers = conn.prepareStatement(sql); // prepare sql statement
            resultSet = allCustomers.executeQuery(); // execute query

            //loop through the result set
            while (resultSet.next()) {

                //Get Customer Data from resultset
                Integer customerId = resultSet.getInt("customer_id");

                String firstName = resultSet.getString("first_name"), lastName = resultSet.getString("last_name"), firstLineAddress = resultSet.getString("first_line_address"),
                        postcode = resultSet.getString("postcode"), contactNumber = resultSet.getString("contact_number"), gender = resultSet.getString("gender");

                ///create new Customer objet with customers data
                Customer person = new Customer(customerId, firstName, lastName, firstLineAddress, postcode, contactNumber, gender);

                //add to list
                customersList.add(person);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            //close database connection
            DbUtil.close(conn, allCustomers, resultSet);
        }

        return customersList;
    }

    @Override
    public int insertPerson(Person person) {
        //Insert person sql statement
        String insertSql = "INSERT INTO customers (first_name,last_name,first_line_address,postcode,contact_number,gender) VALUES (?,?,?,?,?,?) ";

        //Select all rows in customers table sql statement
        String checkSql = "SELECT * FROM customers WHERE first_name = ? AND last_name = ? AND first_line_address = ? AND postcode = ?";

        //Connect to the database
        Connection conn = DbUtil.getConnection();
        ResultSet resultSet = null;

        int inserted = 0;
        try {

            //Select all person rows
            allCustomers = conn.prepareStatement(checkSql);
            allCustomers.setString(1, person.getFirstName());
            allCustomers.setString(2, person.getLastName());
            allCustomers.setString(3, person.getFirstLineAddress());
            allCustomers.setString(4, person.getPostcode());
            resultSet = allCustomers.executeQuery();

            //If doesn't exist in customers table
            if (!resultSet.next()) {
                addCustomer = conn.prepareStatement(insertSql);
                addCustomer.setString(1, person.getFirstName());
                addCustomer.setString(2, person.getLastName());
                addCustomer.setString(3, person.getFirstLineAddress());
                addCustomer.setString(4, person.getPostcode());
                addCustomer.setString(5, person.getContactNumber());
                addCustomer.setString(6, person.getGender());

                int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to insert this Customer?", "Update Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirmation == JOptionPane.YES_OPTION) {
                    //execute insert
                    inserted = addCustomer.executeUpdate();
                }

            } //Else don't insert and show error messages.
            else {
                JOptionPane.showMessageDialog(null, "Customer already exists.", "Invalid insert.", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            //close database connection
            DbUtil.close(conn, allCustomers, resultSet);
        }

        return inserted;
    }

    @Override
    public int updatePersonById(int id, Person updatedPerson) {
        //Update customer sql statement
        String updateSql = "UPDATE customers SET first_name = ?,last_name = ?,first_line_address = ?,postcode = ?,contact_number = ?,gender = ? WHERE customer_id = ?";

        //Select all customer sql statement
        String checkSql = "SELECT * FROM customers WHERE first_name = ? AND last_name = ? AND first_line_address = ? AND postcode = ?";

        //Connect to database and initialise result set
        Connection conn = DbUtil.getConnection();
        ResultSet resultSet = null;

        int updated = 0;
        try {

            //Select all customer rows
            allCustomers = conn.prepareStatement(checkSql);
            allCustomers.setString(1, updatedPerson.getFirstName());
            allCustomers.setString(2, updatedPerson.getLastName());
            allCustomers.setString(3, updatedPerson.getFirstLineAddress());
            allCustomers.setString(4, updatedPerson.getPostcode());
            resultSet = allCustomers.executeQuery();

            //If doesn't exist in customers table update it
            if (!resultSet.next()) {
                updateCustomer = conn.prepareStatement(updateSql);
                updateCustomer.setString(1, updatedPerson.getFirstName());
                updateCustomer.setString(2, updatedPerson.getLastName());
                updateCustomer.setString(3, updatedPerson.getFirstLineAddress());
                updateCustomer.setString(4, updatedPerson.getPostcode());
                updateCustomer.setString(5, updatedPerson.getContactNumber());
                updateCustomer.setString(6, updatedPerson.getGender());
                updateCustomer.setInt(7, id);

                int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this Customer?", "Update Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirmation == JOptionPane.YES_OPTION) {
                    //execute update
                    updated = updateCustomer.executeUpdate();
                }

            } //Else don't update and show error messages.
            else {
                JOptionPane.showMessageDialog(null, "Customer already exists.", "Invalid insert.", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            //close database connection
            DbUtil.close(conn, updateCustomer, resultSet);
        }
        return updated;
    }

    @Override
    public int deletePersonById(int id) {
        //delete a customer sql statement
        String deleteSql = "DELETE FROM customers WHERE customer_id = ?";

        //Connect to database and initialise result set
        Connection conn = DbUtil.getConnection();

        int deleted = 0;
        try {

            //Prepare delete sql statement and set parameter id
            removeCustomer = conn.prepareStatement(deleteSql);
            removeCustomer.setInt(1, id);

            int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this Customer?", "Delete Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmation == JOptionPane.YES_OPTION) {
                //execute delete
                deleted = removeCustomer.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Customer is deleted successfully.", "Delete Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            //close database connection
            DbUtil.close(conn, removeCustomer, null);
        }
        return deleted;
    }

    @Override
    public Person getPersonByName(String personName) {
        String sql = "SELECT * FROM customers WHERE first_name = ? AND last_name = ?";

        Connection conn = DbUtil.getConnection();
        ResultSet resultSet = null;
        Customer customer = null;

        //Extract firstname and lastname
        String firstNameString = personName.substring(personName.indexOf(",") + 1).trim(), lastNameString = personName.substring(0, personName.indexOf(",")).trim();

        try {
            aCustomer = conn.prepareStatement(sql);
            aCustomer.setString(1, firstNameString);
            aCustomer.setString(2, lastNameString);
            resultSet = aCustomer.executeQuery();

            while (resultSet.next()) {
                Integer customerId = resultSet.getInt("customer_id");

                String firstName = resultSet.getString("first_name"), lastName = resultSet.getString("last_name"), firstLineAddress = resultSet.getString("first_line_address"),
                        postcode = resultSet.getString("postcode"), contactNumber = resultSet.getString("contact_number"), gender = resultSet.getString("gender");

                customer = new Customer(customerId, firstName, lastName, firstLineAddress, postcode, contactNumber, gender);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(conn, aCustomer, resultSet);
        }

        return customer;
    }

}
