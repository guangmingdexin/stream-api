package com.guang.stream.order;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.ForkJoinPool;

/**
 * @author guangyong.deng
 * @date 2021-11-23 17:30
 */
public class StockMaintain implements Subscriber<Order> {


    private Stock stock;

    private Subscription subscription;

    private ExecutorService service = ForkJoinPool.commonPool();

    public StockMaintain(@Autowired Stock stock) {
        this.stock = stock;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("******* onSubscribe *********");
        this.subscription = subscription;
        subscription.request(3);
    }

    @Override
    public void onNext(Order order) {
        System.out.println("thread-local-name: " + Thread.currentThread().getName());
        service.submit(() -> {
            order.getItems().forEach(item -> {
                stock.remove(item.getProduct(), item.getAmount());
                System.out.println("");
            });
        });
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
