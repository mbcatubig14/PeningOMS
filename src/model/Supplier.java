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
public class Supplier {

    private Integer supplierId;

    private String supplierName;

    private String supplierContactName;

    private String supplierAddress;

    private String supplierContactNumber;

    private List<Product> productsList;

    public Supplier() {
    }

    public Supplier(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Supplier(Integer supplierId, String supplierName, String supplierContactName, String supplierAddress, String supplierContactNumber) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierContactName = supplierContactName;
        this.supplierAddress = supplierAddress;
        this.supplierContactNumber = supplierContactNumber;
    }

    public Supplier(String supplierName, String supplierContactName, String supplierAddress, String supplierContactNumber) {
        this.supplierName = supplierName;
        this.supplierContactName = supplierContactName;
        this.supplierAddress = supplierAddress;
        this.supplierContactNumber = supplierContactNumber;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierContactName() {
        return supplierContactName;
    }

    public void setSupplierContactName(String supplierContactName) {
        this.supplierContactName = supplierContactName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierContactNumber() {
        return supplierContactNumber;
    }

    public void setSupplierContactNumber(String supplierContactNumber) {
        this.supplierContactNumber = supplierContactNumber;
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Product> productsList) {
        this.productsList = productsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (supplierId != null ? supplierId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Supplier)) {
            return false;
        }
        Supplier other = (Supplier) object;
        if ((this.supplierId == null && other.supplierId != null) || (this.supplierId != null && !this.supplierId.equals(other.supplierId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "peningoms.model.Suppliers[ supplierId=" + supplierId + " ]";
    }

}
