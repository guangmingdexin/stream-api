package com.guang.rxjava.frame.reactor.publisher;

import com.guang.rxjava.frame.reactor.subscriber.FrameCoreSubscriber;
import io.reactivex.functions.Consumer;
import org.reactivestreams.Subscriber;

/**
 * @author guangyong.deng
 * @date 2021-12-02 15:23
 */
final class FrameFluxCreate<T> extends FrameFlux<T> {

    /**
     * 这里 ？ 是否可以用 Object 代替
     * 表示用户自定义的数据产生方式
     *
     */
    final Consumer<? super FrameFluxSink<T>> source;

    final FrameFluxSink.FrameOverflowStrategy backpressure;

    final FrameCreateMode createMode;

    public FrameFluxCreate(Consumer<? super FrameFluxSink<T>> source, FrameFluxSink.FrameOverflowStrategy backpressure,
                           FrameCreateMode createMode) {
        this.source = source;
        this.backpressure = backpressure;
        this.createMode = createMode;
    }

    /**
     * 实现自 FrameCorePublisher 中的方法
     *
     * @param subscriber
     */
    @Override
    public void subscribe(FrameCoreSubscriber<? super T> subscriber) {

    }

    /**
     *  继承自 Publisher 中的 subscriber 方法，每次订阅都会下方数据
     *
     * @param s
     */
    @Override
    public void subscribe(Subscriber<? super T> s) {

    }

    enum FrameCreateMode {
        PUSH_ONLY, PUSH_PULL
    }
}
