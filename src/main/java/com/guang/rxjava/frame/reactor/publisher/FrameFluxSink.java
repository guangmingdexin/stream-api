package com.guang.rxjava.frame.reactor.publisher;

import org.reactivestreams.Subscriber;

/**
 *
 * 承上启下的接口
 *
 * @author guangyong.deng
 * @date 2021-12-02 14:52
 */
public interface FrameFluxSink<T> {

    void complete();

    void error(Throwable e);

    /**
     * Try emitting, might throw an unchecked exception.
     * @see Subscriber#onNext(Object)
     * @param t the value to emit, not null
     */
    FrameFluxSink<T> next(T t);

    long requestedFromDownstream();

    boolean isCancelled();

    /**
     * Enumeration for backpressure handling.
     */
    enum FrameOverflowStrategy {
        /**
         * Completely ignore downstream backpressure requests.
         * <p>
         * This may yield {@link IllegalStateException} when queues get full downstream.
         */
        IGNORE,
        /**
         * Signal an {@link IllegalStateException} when the downstream can't keep up
         */
        ERROR,
        /**
         * Drop the incoming signal if the downstream is not ready to receive it.
         */
        DROP,
        /**
         * Downstream will get only the latest signals from upstream.
         */
        LATEST,
        /**
         * Buffer all signals if the downstream can't keep up.
         * <p>
         * Warning! This does unbounded buffering and may lead to {@link OutOfMemoryError}.
         */
        BUFFER
    }
}
