package com.guang.rxjava.frame.rxjava.emitter;

import io.reactivex.annotations.NonNull;

/**
 * @author guangyong.deng
 * @date 2021-11-25 10:12
 */
public interface FrameEmitter<T> {

    void onNext(@NonNull T var1);

    void onError(@NonNull Throwable var1);

    void onComplete();
}
