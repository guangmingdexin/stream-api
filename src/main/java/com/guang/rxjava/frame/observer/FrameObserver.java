package com.guang.rxjava.frame.observer;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * @author guangyong.deng
 * @date 2021-11-25 10:18
 */
public interface FrameObserver<T> {

    void onSubscribe(@NonNull Disposable var1);

    void onNext(@NonNull T var1);

    void onError(@NonNull Throwable var1);

    void onComplete();
}
