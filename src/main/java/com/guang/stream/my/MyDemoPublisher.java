package com.guang.stream.my;


import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow.*;
import java.util.concurrent.Future;

/**
 * @author guangyong.deng
 * @date 2021-11-23 11:48
 */
public class MyDemoPublisher<T> implements Publisher<T>, AutoCloseable {

    /**
     * 守护线程
     */
    private final ExecutorService executor;


    private CopyOnWriteArrayList<MySubscription> list = new CopyOnWriteArrayList<>();

    public MyDemoPublisher(ExecutorService executor) {
        this.executor = executor;
    }

    public void submit(T item) {
        System.out.println("*******" + " 开始发布元素 item: " + item + " ************");
        list.forEach(e -> {
            e.future = executor.submit(() -> {
                // 启动一个异步任务
                // 消费元素
                e.subscriber.onNext(item);
            });
        });
    }

    /**
     * 只有在将 subscriber 添加到 publisher 的时候，onSubscribe 方法才会被调用
     *
     * @param subscriber
     */
    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        subscriber.onSubscribe(new MySubscription<>(subscriber, executor));
        list.add(new MySubscription(subscriber, executor));
    }


    @Override
    public void close() {
        list.forEach(e -> {
          e.future =  executor.submit(() -> {
                e.subscriber.onComplete();
            });
        });
    }

    static class MySubscription<T> implements Subscription {

        private final Subscriber<T> subscriber;

        private final ExecutorService executor;

        private  Future<?> future;

        private T item;

        private boolean completed;

        public MySubscription(Subscriber<T> subscriber, ExecutorService executor) {
            this.subscriber = subscriber;
            this.executor = executor;
        }

        @Override
        public void request(long n) {
            if(n != 0 && !completed) {
                if(n < 0) {
                    IllegalArgumentException ex = new IllegalArgumentException();
                    executor.execute(() -> subscriber.onError(ex));
                } else {
                    executor.submit(() -> {
                        // 启动异步任务
                        subscriber.onNext(item);
                    });
                }
            }else {
                subscriber.onComplete();
            }
        }

        @Override
        public void cancel() {
            completed = true;
            if(future != null && !future.isCancelled()) {
                future.cancel(true);
            }
        }
    }
}
