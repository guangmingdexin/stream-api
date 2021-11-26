package com.guang.rxjava.frame;

import com.guang.rxjava.frame.emitter.FrameObservableEmitter;
import io.reactivex.annotations.NonNull;

/**
 * @author guangyong.deng
 * @date 2021-11-25 10:11
 */
public interface FrameObservableOnSubscribe<T> {

    void subscribe(@NonNull FrameObservableEmitter<T> var1) throws Exception;

}
