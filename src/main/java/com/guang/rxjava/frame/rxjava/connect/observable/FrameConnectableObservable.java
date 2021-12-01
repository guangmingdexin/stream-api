package com.guang.rxjava.frame.rxjava.connect.observable;

import com.guang.rxjava.frame.rxjava.disposable.FrameDisposable;
import com.guang.rxjava.frame.rxjava.observable.FrameObservable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @author guangyong.deng
 * @date 2021-11-29 10:31
 */
public abstract class FrameConnectableObservable<T> extends FrameObservable<T> {

    public abstract void connect(@NonNull Consumer<? super FrameDisposable> connection);
}
