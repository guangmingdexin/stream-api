package com.guang.rxjava.frame.reactor.publisher;

import com.guang.rxjava.frame.reactor.subscriber.FrameCoreSubscriber;
import org.reactivestreams.Publisher;

/**
 * 这个接口的作用？
 *
 * @author guangyong.deng
 * @date 2021-12-02 15:05
 */
public interface FrameCorePublisher<T> extends Publisher<T> {

    /**
     * @param subscriber
     */
    void subscribe(FrameCoreSubscriber<? super T> subscriber);
}
