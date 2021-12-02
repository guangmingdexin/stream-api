package com.guang.rxjava.frame.reactor.publisher;

import com.guang.rxjava.frame.reactor.publisher.FrameFluxSink.*;
import io.reactivex.functions.Consumer;

/**
 * @author guangyong.deng
 * @date 2021-12-02 14:58
 */
public abstract class FrameFlux<T> implements FrameCorePublisher<T> {

    public static <T> FrameFlux<T> create(Consumer<? super FrameFluxSink<T>> emitter) {
       // return create(emitter, FrameOverflowStrategy.BUFFER);
        return new FrameFluxCreate<>(emitter, FrameOverflowStrategy.BUFFER,
                FrameFluxCreate.FrameCreateMode.PUSH_PULL);
    }
}
