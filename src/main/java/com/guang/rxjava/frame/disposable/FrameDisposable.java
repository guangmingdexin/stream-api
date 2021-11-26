package com.guang.rxjava.frame.disposable;

/**
 * @author guangyong.deng
 * @date 2021-11-25 10:26
 */
public interface FrameDisposable {

    void dispose();

    boolean isDisposed();
}
