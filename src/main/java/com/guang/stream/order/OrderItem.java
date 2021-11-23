package com.guang.stream.order;

/**
 * @author guangyong.deng
 * @date 2021-11-23 17:46
 */
public class OrderItem {

    private Product product;

    private long amount;

    public Product getProduct() {
        return product;
    }

    public OrderItem setProduct(Product product) {
        this.product = product;
        return this;
    }

    public long getAmount() {
        return amount;
    }

    public OrderItem setAmount(long amount) {
        this.amount = amount;
        return this;
    }
}
