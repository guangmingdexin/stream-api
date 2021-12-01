package com.guang.rxjava.frame.rxjava.observer;

import com.guang.rxjava.frame.rxjava.disposable.FrameDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 事实上的订阅者，及下发数据后实际的接受对象
 *
 * @author guangyong.deng
 * @date 2021-11-25 10:31
 */
public final class FrameLambdaObserver<T> implements FrameObserver<T>, FrameDisposable {

    final Consumer<? super T> onNext;

    public FrameLambdaObserver(Consumer<? super T> onNext) {
        this.onNext = onNext;
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean isDisposed() {
        return false;
    }

    @Override
    public void onSubscribe(Disposable var1) {

    }

    @Override
    public void onNext(T t) {
        try {
            this.onNext.accept(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable var1) {

    }

    @Override
    public void onComplete() {

    }
}
