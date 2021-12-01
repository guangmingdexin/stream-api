package com.guang.rxjava.frame.rxjava.observable;

import com.guang.rxjava.frame.rxjava.observer.FrameObserver;
import io.reactivex.annotations.NonNull;

/**
 *
 * 仿照 RxJava 框架初步学习
 *
 * @author guangyong.deng
 * @date 2021-11-25 10:04
 */
public interface FrameObservableSource<T> {

    void subscribe(@NonNull FrameObserver<? super T> var1);
}
