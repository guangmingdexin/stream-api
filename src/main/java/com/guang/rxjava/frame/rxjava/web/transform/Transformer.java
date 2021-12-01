package com.guang.rxjava.frame.rxjava.web.transform;

/**
 * @author guangyong.deng
 * @date 2021-11-29 10:15
 */
public interface Transformer<R, T> {

    /**
     * 转换数据接口
     *
     * @param source 目标源数据
     * @return 转换类型数据
     */
    R transformer(T source);
}
