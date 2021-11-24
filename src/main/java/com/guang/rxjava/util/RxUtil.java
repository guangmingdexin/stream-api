package com.guang.rxjava.util;

/**
 * @author guangyong.deng
 * @date 2021-11-24 15:21
 */
public class RxUtil {


    public static void log(Object msg) {
        System.out.println(Thread.currentThread().getName() + "：" + msg.toString() );
    }
}
