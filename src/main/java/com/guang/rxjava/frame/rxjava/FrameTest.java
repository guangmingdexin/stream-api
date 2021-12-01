package com.guang.rxjava.frame.rxjava;

import com.guang.rxjava.frame.rxjava.observable.FrameObservable;

/**
 * @author guangyong.deng
 * @date 2021-11-25 11:26
 */
public class FrameTest {

    public static void main(String[] args) {

        FrameObservable<Object> observable = FrameObservable.create((observer) -> {
            observer.onNext("hello " + Math.random());
        });

        observable.subscribe((consumer) -> {
            System.out.println(Thread.currentThread().getName() + " " + consumer);
        });


    }
}
