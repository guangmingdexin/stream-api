package com.guang.rxjava.frame.observable;

import com.guang.rxjava.frame.FrameObservableOnSubscribe;
import com.guang.rxjava.frame.disposable.FrameDisposable;
import com.guang.rxjava.frame.emitter.FrameObservableEmitter;
import com.guang.rxjava.frame.observer.FrameObserver;
import io.reactivex.exceptions.Exceptions;


/**
 * @author guangyong.deng
 * @date 2021-11-25 10:16
 */
public final class FrameObservableCreate<T> extends FrameObservable<T> {

    /**
     * 用户定义的数据源产生方式
     */
    final FrameObservableOnSubscribe<T> source;

    public FrameObservableCreate(FrameObservableOnSubscribe<T> source) {
        this.source = source;
    }

    /**
     * @param observer 用户自实现订阅方式
     */
    @Override
    protected void subscribeActual(FrameObserver<? super T> observer) {
        FrameObservableCreate.FrameCreateEmitter<T> parent = new FrameObservableCreate.FrameCreateEmitter(observer);
//        observer.onSubscribe(parent);
        try {
            // 当订阅的时候才会下方数据
            // 执行  FrameCreateEmitter 的 onNext 方法
            this.source.subscribe(parent);
        } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            parent.onError(var4);
        }
    }

    /**
     *
     * 内部类，作用如下
     * 1.作为连接 Observable 和 Observer 的中间联系方式（产生数据的方式！但是使用 onNext 容易产生误解！）
     * 2.增强作用，看作装饰器模式 or 代理模式
     * 3.第三方接口，解耦合
     *
     */
    static final class FrameCreateEmitter<T>  implements FrameObservableEmitter<T>, FrameDisposable {

        final FrameObserver<? super T> observer;

        FrameCreateEmitter(FrameObserver<? super T> observer) {
            this.observer = observer;
        }

        @Override
        public void dispose() {

        }

        @Override
        public boolean isDisposed() {
            return false;
        }

        @Override
        public void onNext(T t) {
            if (t == null) {
                this.onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
            } else {
                if (!this.isDisposed()) {
                    // 执行用户自定义的订阅方式
                    System.out.println("这是第一步！" + t);
                    this.observer.onNext(t);
                }

            }
        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onComplete() {

        }
    }
}
