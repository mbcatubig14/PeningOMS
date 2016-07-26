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
public class Customer extends Person {

    private List<Order> ordersList;

    public Customer(Integer customerId, String firstName, String lastName, String firstLineAddress, String postcode, String contactNumber, String gender) {
        super(customerId, firstName, lastName, firstLineAddress, postcode, contactNumber, gender);
    }

    public Customer(String firstName, String lastName, String firstLineAddress, String postcode, String contactNumber, String gender) {
        super(firstName, lastName, firstLineAddress, postcode, contactNumber, gender);
    }

    public List<Order> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Order> ordersList) {
        this.ordersList = ordersList;
    }

}
