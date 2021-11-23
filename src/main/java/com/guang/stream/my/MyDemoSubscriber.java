package com.guang.stream.my;

import java.util.concurrent.Flow.*;

/**
 * @author guangyong.deng
 * @date 2021-11-23 11:22
 */
public class MyDemoSubscriber<T> implements Subscriber<T> {

    private String name;

    private Subscription subscription;

    final long bufferSize;

    long count;

    public String getName() {
        return name;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public  MyDemoSubscriber(String name, long bufferSize) {
        this.name = name;
        this.bufferSize = bufferSize;
    }

    @Override
    public void onSubscribe(Subscription subscription) {

        // 订阅
        (this.subscription = subscription).request(bufferSize);
        System.out.println("开始 onSubscribe 订阅");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNext(Object item) {

        System.out.println(
                "##############################" +
                Thread.currentThread().getName() +
                        " name: " + name +
                        " item: " + item +
                        "#####################"
        );

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("Completed!");
    }
}
