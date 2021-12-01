package com.guang.rxjava.frame.reactor.util;

/**
 * @author guangyong.deng
 * @date 2021-11-30 15:51
 */
public class FluxUtil {

    public static void sleep(long n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
