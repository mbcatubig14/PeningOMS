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
import model.Employee;
import model.Person;

/**
 *
 * @author Muhammad
 */
public class EmployeeDAO extends PersonDAO {

    private PreparedStatement anEmployee, allEmployees, addEmployee, removeEmployee, updateEmployee;

    @Override
    public Person getPersonById(int id) {
        String sql = "SELECT * FROM employees WHERE employee_id = ?";

        Connection conn = DbUtil.getConnection();
        ResultSet resultSet = null;
        Employee employee = null;
        try {
            anEmployee = conn.prepareStatement(sql);
            anEmployee.setInt(1, id);
            resultSet = anEmployee.executeQuery();

            while (resultSet.next()) {
                Integer employeeId = resultSet.getInt("employee_id");

                String firstName = resultSet.getString("first_name"), lastName = resultSet.getString("last_name"), firstLineAddress = resultSet.getString("full_address"),
                        postcode = resultSet.getString("postcode"), contactNumber = resultSet.getString("telephone_number"), gender = resultSet.getString("gender"), jobRole = resultSet.getString("job_role");

                employee = new Employee(employeeId, firstName, lastName, firstLineAddress, postcode, contactNumber, gender, jobRole);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(conn, anEmployee, resultSet);
        }

        return employee;
    }

    @Override
    public Person getPersonByName(String employeeName) {
        String sql = "SELECT * FROM employees WHERE first_name = ? AND last_name = ?";

        Connection conn = DbUtil.getConnection();
        ResultSet resultSet = null;
        Employee employee = null;

        //Extract firstname and lastname
        String firstNameString = employeeName.substring(employeeName.indexOf(",") + 1).trim(), lastNameString = employeeName.substring(0, employeeName.indexOf(",")).trim();

        try {
            anEmployee = conn.prepareStatement(sql);
            anEmployee.setString(1, firstNameString);
            anEmployee.setString(2, lastNameString);
            resultSet = anEmployee.executeQuery();

            while (resultSet.next()) {
                Integer employeeId = resultSet.getInt("employee_id");

                String firstName = resultSet.getString("first_name"), lastName = resultSet.getString("last_name"), firstLineAddress = resultSet.getString("full_address"),
                        postcode = resultSet.getString("postcode"), contactNumber = resultSet.getString("telephone_number"), gender = resultSet.getString("gender"), jobRole = resultSet.getString("job_role");

                employee = new Employee(employeeId, firstName, lastName, firstLineAddress, postcode, contactNumber, gender, jobRole);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtil.close(conn, anEmployee, resultSet);
        }

        return employee;
    }

    @Override
    public List<Person> getPersons() {
        //Select all rows in employees table sql statement
        String sql = "SELECT * FROM employees";

        //Connect to the database
        Connection conn = DbUtil.getConnection();

        //Initialise result set
        ResultSet resultSet = null;

        //Create a list of employees
        List<Person> employeesList = new ArrayList<>();
        try {

            allEmployees = conn.prepareStatement(sql); // prepare sql statement
            resultSet = allEmployees.executeQuery(); // execute query

            //loop through the result set
            while (resultSet.next()) {

                //Get Employee Data from resultset
                Integer employeeId = resultSet.getInt("employee_id");

                String firstName = resultSet.getString("first_name"), lastName = resultSet.getString("last_name"), firstLineAddress = resultSet.getString("full_address"),
                        postcode = resultSet.getString("postcode"), contactNumber = resultSet.getString("telephone_number"), gender = resultSet.getString("gender"), jobRole = resultSet.getString("job_role");

                ///create new Employee objet with employees data
                Employee employee = new Employee(employeeId, firstName, lastName, firstLineAddress, postcode, contactNumber, gender, jobRole);

                //add to list
                employeesList.add(employee);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            //close database connection
            DbUtil.close(conn, allEmployees, resultSet);
        }

        return employeesList;
    }

    public List<Employee> getCouriers() {
        //Select all rows in employees table sql statement
        String sql = "SELECT * FROM employees WHERE job_role=?";

        //Connect to the database
        Connection conn = DbUtil.getConnection();

        //Initialise result set
        ResultSet resultSet = null;

        //Create a list of employees
        List<Employee> employeesList = new ArrayList<>();
        try {

            allEmployees = conn.prepareStatement(sql); // prepare sql statement
            allEmployees.setString(1, "Courier");
            resultSet = allEmployees.executeQuery(); // execute query

            //loop through the result set
            while (resultSet.next()) {

                //Get Employee Data from resultset
                Integer employeeId = resultSet.getInt("employee_id");

                String firstName = resultSet.getString("first_name"), lastName = resultSet.getString("last_name"), firstLineAddress = resultSet.getString("full_address"),
                        postcode = resultSet.getString("postcode"), contactNumber = resultSet.getString("telephone_number"), gender = resultSet.getString("gender"), jobRole = resultSet.getString("job_role");

                ///create new Employee objet with employees data
                Employee employee = new Employee(employeeId, firstName, lastName, firstLineAddress, postcode, contactNumber, gender, jobRole);

                //add to list
                employeesList.add(employee);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            //close database connection
            DbUtil.close(conn, allEmployees, resultSet);
        }

        return employeesList;
    }

    @Override
    public int insertPerson(Person employee) {
        //Insert employee sql statement
        String insertSql = "INSERT INTO employees (first_name,last_name,full_address,postcode,telephone_number,gender, job_role) VALUES (?, ?, ?, ?, ?, ?, ?) ";

        //Select all rows in employees table sql statement
        String checkSql = "SELECT * FROM employees WHERE first_name = ? AND last_name = ? AND full_address = ? AND postcode = ?";

        //Connect to the database
        Connection conn = DbUtil.getConnection();
        ResultSet resultSet = null;

        int inserted = 0;

        Employee theEmployee = (Employee) employee;

        try {

            //Select all employee rows
            allEmployees = conn.prepareStatement(checkSql);
            allEmployees.setString(1, theEmployee.getFirstName());
            allEmployees.setString(2, theEmployee.getLastName());
            allEmployees.setString(3, theEmployee.getFirstLineAddress());
            allEmployees.setString(4, theEmployee.getPostcode());
            resultSet = allEmployees.executeQuery();

            //If doesn't exist in employees table
            if (!resultSet.next()) {
                addEmployee = conn.prepareStatement(insertSql);
                addEmployee.setString(1, theEmployee.getFirstName());
                addEmployee.setString(2, theEmployee.getLastName());
                addEmployee.setString(3, theEmployee.getFirstLineAddress());
                addEmployee.setString(4, theEmployee.getPostcode());
                addEmployee.setString(5, theEmployee.getContactNumber());
                addEmployee.setString(6, theEmployee.getGender());
                addEmployee.setString(7, theEmployee.getJobRole());

                int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to insert this Employee?", "Update Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirmation == JOptionPane.YES_OPTION) {
                    //execute insert
                    inserted = addEmployee.executeUpdate();
                }

            } //Else don't insert and show error messages.
            else {
                JOptionPane.showMessageDialog(null, "Employee already exists.", "Invalid insert.", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            //close database connection
            DbUtil.close(conn, allEmployees, resultSet);
        }

        return inserted;
    }

    @Override
    public int updatePersonById(int id, Person updatedEmployee) {
        //Update employee sql statement
        String updateSql = "UPDATE employees SET first_name = ?,last_name = ?,full_address = ?,postcode = ?,telephone_number = ?,gender = ?, job_role = ? WHERE employee_id = ?";

        //Select all employee sql statement
        String checkSql = "SELECT * FROM employees WHERE first_name = ? AND last_name = ? AND full_address = ? AND postcode = ?";

        //Connect to database and initialise result set
        Connection conn = DbUtil.getConnection();
        ResultSet resultSet = null;

        int updated = 0;

        Employee theEmployee = (Employee) updatedEmployee;
        try {

            //Select all employee rows
            allEmployees = conn.prepareStatement(checkSql);
            allEmployees.setString(1, theEmployee.getFirstName());
            allEmployees.setString(2, theEmployee.getLastName());
            allEmployees.setString(3, theEmployee.getFirstLineAddress());
            allEmployees.setString(4, theEmployee.getPostcode());
            resultSet = allEmployees.executeQuery();

            //If doesn't exist in employees table update it
            if (!resultSet.next()) {
                updateEmployee = conn.prepareStatement(updateSql);
                updateEmployee.setString(1, theEmployee.getFirstName());
                updateEmployee.setString(2, theEmployee.getLastName());
                updateEmployee.setString(3, theEmployee.getFirstLineAddress());
                updateEmployee.setString(4, theEmployee.getPostcode());
                updateEmployee.setString(5, theEmployee.getContactNumber());
                updateEmployee.setString(6, theEmployee.getGender());
                updateEmployee.setString(7, theEmployee.getJobRole());
                updateEmployee.setInt(8, id);

                int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this Employee?", "Update Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirmation == JOptionPane.YES_OPTION) {
                    //execute update
                    updated = updateEmployee.executeUpdate();
                }

            } //Else don't update and show error messages.
            else {
                JOptionPane.showMessageDialog(null, "Employee already exists.", "Invalid insert.", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            //close database connection
            DbUtil.close(conn, updateEmployee, resultSet);
        }
        return updated;
    }

    @Override
    public int deletePersonById(int id) {
        //delete a employee sql statement
        String deleteSql = "DELETE FROM employees WHERE employee_id = ?";

        //Connect to database and initialise result set
        Connection conn = DbUtil.getConnection();

        int deleted = 0;
        try {

            //Prepare delete sql statement and set parameter id
            removeEmployee = conn.prepareStatement(deleteSql);
            removeEmployee.setInt(1, id);

            int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this Employee?", "Delete Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmation == JOptionPane.YES_OPTION) {
                //execute delete
                deleted = removeEmployee.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Employee is deleted successfully.", "Delete Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            //close database connection
            DbUtil.close(conn, removeEmployee, null);
        }
        return deleted;
    }

}
