package com.guang.stream.order;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author guangyong.deng
 * @date 2021-11-23 17:31
 */
public class Order {

    private List<OrderItem> items;

    public List<OrderItem> getItems() {
        return items;
    }

    public Order setItems(List<OrderItem> items) {
        this.items = items;
        return this;
    }
}
