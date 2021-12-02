package com.guang.rxjava.frame.reactor.subscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @author guangyong.deng
 * @date 2021-12-02 15:07
 */
public interface FrameCoreSubscriber<T> extends Subscriber<T> {

    /**
     * 在给定的Subscription想要使用Subscriber其他方法的前提下，必须先调用这个方法
     *
     * @param s
     */
    @Override
    void onSubscribe(Subscription s);
}
