package com.guang.stream.order;

import com.guang.stream.my.MyDemoSubscriber;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow.*;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author guangyong.deng
 * @date 2021-11-24 9:33
 */
public class DemoTest {

    public static void main(String[] args) throws InterruptedException {
        Stock stock = new Stock();

        SubmissionPublisher<Order> publisher = new SubmissionPublisher<>();


        StockMaintain subscriber = new StockMaintain(stock);


        publisher.subscribe(subscriber);

        Product product = new Product("1", "test");
        stock.store(product, 40);

        OrderItem orderItem = new OrderItem();

        orderItem.setProduct(product);
        orderItem.setAmount(10);

        Order order = new Order();
        List<OrderItem> items = new LinkedList<>();

        order.setItems(items);

        for (int i = 0; i < 10; i++) {
            publisher.submit(order);
        }

        System.out.println("所有订单提交完毕！");

        Thread.sleep(100);

        publisher.close();

        System.out.println("publisher 已关闭！");
    }
}
