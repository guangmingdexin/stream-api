package com.guang.stream.order;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author guangyong.deng
 * @date 2021-11-23 16:51
 */
// @Component
public class Stock {

    private final Map<Product, StockItem> stockItemMap = new ConcurrentHashMap<>();

    public void store(Product product, long amount) {
        stockItemMap.getOrDefault(product, new StockItem()).store(amount);
    }

    public void remove(Product product, long amount) {
        if(stockItemMap.getOrDefault(product, new StockItem()).remove(amount) != amount) {
            System.out.println("删除订单错误！");
        }
    }
}
