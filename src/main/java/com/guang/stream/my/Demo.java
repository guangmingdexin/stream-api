package com.guang.stream.my;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

/**
 * @author guangyong.deng
 * @date 2021-11-23 14:23
 */
public class Demo {

    /**
     *  通过为每一个subscriber分别指定subscription来连接同一个publisher
     *
     * @param publisher
     * @param subscriberName
     */
    private static void demoSubscribe(MyDemoPublisher<Integer> publisher, String subscriberName) {

        MyDemoSubscriber<Integer> subscriber = new MyDemoSubscriber<>(subscriberName, 4L);

        publisher.subscribe(subscriber);
    }


    public static void main(String[] args) {

        ExecutorService executor = ForkJoinPool.commonPool();

        try (MyDemoPublisher<Integer> publisher = new MyDemoPublisher<>(executor)){

            demoSubscribe(publisher, "One");
            demoSubscribe(publisher, "Two");
            demoSubscribe(publisher, "Three");

            IntStream.range(1, 5).forEach(publisher::submit);
        }finally {

        }

    }
}
