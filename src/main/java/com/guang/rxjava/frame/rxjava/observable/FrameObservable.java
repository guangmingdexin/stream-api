package com.guang.rxjava.frame.rxjava.observable;

import com.guang.rxjava.frame.rxjava.FrameObservableOnSubscribe;
import com.guang.rxjava.frame.rxjava.disposable.FrameDisposable;
import com.guang.rxjava.frame.rxjava.observer.FrameLambdaObserver;
import com.guang.rxjava.frame.rxjava.observer.FrameObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 数据源，既能产生数据，又能下发数据
 * 可以根据用户定义的方式产生数据源，
 *
 * @author guangyong.deng
 * @date 2021-11-25 10:06
 */
public abstract class FrameObservable<T> implements FrameObservableSource<T> {

    /**
     * 能够通过用户自定义的方法产生数据源
     *
     * @param source 用户自定义对象实例
     * @param <T>
     * @return
     */
    @NonNull
    public static <T> FrameObservable<T> create(FrameObservableOnSubscribe<T> source) {
        // 这里使用了适配器模式
        // 通过 适配 FrameObservableOnSubscribe 接口，可以让 数据源能够在订阅的时候，下方数据
        // 达到 既能产生数据，又能下发数据
        // FrameObservableCreate - 可以看作一个适配器类
        return new FrameObservableCreate(source);
    }

    /**
     * 数据源将数据推送给订阅者
     * 同时可以执行用户自定义的消费方式
     *
     * @param onNext 订阅数据
     * @return
     */
    public final FrameDisposable subscribe(Consumer<? super T> onNext) {
        // 订阅的时候会执行用户自定义的方式
        FrameLambdaObserver<T> ls = new FrameLambdaObserver(onNext);
        // 调用实际实现 FrameObservableSource 接口的对象方法
        // 具体类型为：
        this.subscribe(ls);
        return ls;
    }

    @Override
    public final void subscribe(FrameObserver<? super T> observer) {
        try {
            // 执行 子类 的 实现方式
            // obsever : FrameLambdaObserver
            this.subscribeActual(observer);
        } catch (NullPointerException var4) {
            throw var4;
        } catch (Throwable var5) {
            NullPointerException npe = new NullPointerException("Actually not, but can't throw other exceptions due to RS");
            npe.initCause(var5);
            throw npe;
        }
    }

    /**
     * 实际控制消费方式的的方法
     *
     * @param var1
     */
    protected abstract void subscribeActual(FrameObserver<? super T> var1);


}
