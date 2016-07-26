/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Muhammad
 */
public class Order {

    private Integer orderId;

    private String courierName;

    private Date orderDate;

    private BigDecimal total;

    private Customer customers;

    private Employee employees;

    private List<DetailedOrder> orderDetailsList;

    public Order() {
    }

    public Order(Integer orderId, String courierName, Date orderDate, BigDecimal total, Customer customers, Employee employees) {
        this.orderId = orderId;
        this.courierName = courierName;
        this.orderDate = orderDate;
        this.total = total;
        this.customers = customers;
        this.employees = employees;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Customer getCustomer() {
        return customers;
    }

    public void setCustomer(Customer customers) {
        this.customers = customers;
    }

    public Employee getEmployee() {
        return employees;
    }

    public void setEmployee(Employee employees) {
        this.employees = employees;
    }

    public List<DetailedOrder> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<DetailedOrder> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Order)) {
            return false;
        }
        Order other = (Order) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Orders{" + "orderId=" + orderId + ", courierName=" + courierName + ", orderDate=" + orderDate + ", total=" + total + ", customers=" + customers + ", employees=" + employees + '}';
    }

}
