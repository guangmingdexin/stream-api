package com.guang.rxjava.frame.rxjava;

import com.guang.rxjava.frame.rxjava.emitter.FrameObservableEmitter;
import io.reactivex.annotations.NonNull;

/**
 * @author guangyong.deng
 * @date 2021-11-25 10:11
 */
public interface FrameObservableOnSubscribe<T> {

    void subscribe(@NonNull FrameObservableEmitter<T> var1) throws Exception;

}
