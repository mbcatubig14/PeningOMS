/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author Muhammad
 */
public class Employee extends Person {

    private String jobRole;

    private List<Order> ordersList;

    public Employee(Integer employeeId, String firstName, String lastName, String fullAddress, String postcode, String telephoneNumber, String gender, String jobRole) {
        super(employeeId, firstName, lastName, fullAddress, postcode, telephoneNumber, gender);
        this.jobRole = jobRole;
    }

    public Employee(String firstName, String lastName, String fullAddress, String postcode, String telephoneNumber, String gender, String jobRole) {
        super(firstName, lastName, fullAddress, postcode, telephoneNumber, gender);
        this.jobRole = jobRole;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public List<Order> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Order> ordersList) {
        this.ordersList = ordersList;
    }

}
