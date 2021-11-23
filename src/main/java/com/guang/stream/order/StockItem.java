package com.guang.stream.order;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author guangyong.deng
 * @date 2021-11-23 16:52
 */
public class StockItem {

    private final AtomicLong amountItemStock = new AtomicLong(0);

    public void store(long n) {
        amountItemStock.accumulateAndGet(n, (prev, mount) -> prev + mount);
    }

    /**
     * 1.下单时，库存数量大于所需商品数量，则减去所需商品数量，返回移除的库存数量
     * 2.库存数量小于所需商品数量，则返回 0
     *
     * @param n 移除库存数量
     * @return 移除库存的实际数量
     */
    public long remove(long n) {

        /**
         * 使用内部类的原因-保持计算的无状态性
         */
        class RemoveData {
            long remove;
        }

        RemoveData removeData = new RemoveData();

        amountItemStock.accumulateAndGet(n,
                (prev, mount) -> prev >= n ?
                        prev - (removeData.remove = mount) :
                        prev - (removeData.remove = 0));

        return removeData.remove;
    }
}
