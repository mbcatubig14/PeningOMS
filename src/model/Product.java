/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Muhammad
 */
public class Product {

    private int productId;

    private String productName;

    private String productType;

    private Integer numberOfStocks;

    private BigDecimal pricePerUnit;

    private String packagingType;

    private List<DetailedOrder> orderDetailsList;

    private Supplier suppliers;

    public Product() {
    }

    public Product(int productId) {
        this.productId = productId;
    }

    public Product(int productId, String productName, String productType) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
    }

    public Product(int productId, String productName, String productType, Integer numberOfStocks, BigDecimal pricePerUnit, String packagingType, Supplier suppliers) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.numberOfStocks = numberOfStocks;
        this.pricePerUnit = pricePerUnit;
        this.packagingType = packagingType;
        this.suppliers = suppliers;
    }

    public Product(String productName, String productType, Integer numberOfStocks, BigDecimal pricePerUnit, String packagingType) {
        this.productName = productName;
        this.productType = productType;
        this.numberOfStocks = numberOfStocks;
        this.pricePerUnit = pricePerUnit;
        this.packagingType = packagingType;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getNumberOfStocks() {
        return numberOfStocks;
    }

    public void setNumberOfStocks(Integer numberOfStocks) {
        this.numberOfStocks = numberOfStocks;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getPackagingType() {
        return packagingType;
    }

    public void setPackagingType(String packagingType) {
        this.packagingType = packagingType;
    }

    public List<DetailedOrder> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<DetailedOrder> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }

    public Supplier getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Supplier suppliers) {
        this.suppliers = suppliers;
    }

    @Override
    public String toString() {
        return "Products{" + "productName=" + productName + ", productType=" + productType + ", numberOfStocks=" + numberOfStocks + ", pricePerUnit=" + pricePerUnit + ", packagingType=" + packagingType + '}';
    }

}
