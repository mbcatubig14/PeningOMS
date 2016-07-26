package model;

import java.math.BigDecimal;

/**
 *
 * @author Muhammad
 */
public class DetailedOrder {

    private Integer orderDetailsId;

    private int quantity;

    private BigDecimal subTotal;

    private String orderDescription;

    private Product product;

    private Order orders;

    public DetailedOrder() {
    }

    public DetailedOrder(Integer orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public DetailedOrder(Integer orderDetailsId, int quantity, BigDecimal total, String orderDescription) {
        this.orderDetailsId = orderDetailsId;

    }

    public DetailedOrder(Product product, int quantity, BigDecimal subTotal, String orderDescription) {
        this.product = product;
        this.quantity = quantity;
        this.subTotal = subTotal;
        this.orderDescription = orderDescription;
    }

    public Integer getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(Integer orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return subTotal;
    }

    public void setTotal(BigDecimal total) {
        this.subTotal = total;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public Product getProducts() {
        return product;
    }

    public void setProducts(Product product) {
        this.product = product;
    }

    public Order getOrders() {
        return orders;
    }

    public void setOrders(Order orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Product: " + product.getProductName() + ", Qty x U/P:" + quantity + " " + product.getPackagingType() + " x " + product.getPricePerUnit().toPlainString() + ", Subtotal: £" + subTotal.toPlainString();
    }

}
