package com.guang.rxjava;

import com.guang.rxjava.util.RxUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

import java.math.BigInteger;

/**
 * @author guangyong.deng
 * @date 2021-11-24 11:09
 */
public class RxJavaDemo {

    public static void observable() {

//        Observable.create(new ObservableOnSubscribe<Object>() {
//            @Override
//            public void subscribe(ObservableEmitter<Object> observableEmitter) throws Exception {
//                observableEmitter.onNext("处理的数字： "  + Math.random() * 100);
//                observableEmitter.onComplete();
//            }
//        });

        /**
         *
         * 1.创建数据源
         *  1.1 通过 lambda 表达式 创建一个 ObservableOnSubscribe 对象实例，重写 subscribe(ObservableEmitter) 方法
         *  1.2 Observable.create(source), source 为  ObservableOnSubscribe 对象实例
         *    即创建一个ObservableCreate 对象实例，同时初始化 source:ObservableOnSubscribe 属性
         *
         * 2.订阅者消费数据
         *      2.1 通过 lambda 表达式定义消费方式，创建 Consumer 对象实例
         *      2.2 调用 Observable.subscribe(Consumer ) 方法
         *      2.3 创建 LambdaObserver LS 对象实例，调用 subscribe(LS), 调用 抽象类方法 subscribeActual(LS)
         *          返回 LS
         *      2.4 调用 ObservableCreate 对象实例的 subscribeActual
         *      2.5 创建 ObservableEmitter CreateEmitter 对象的实例，初始化属性observer : LS
         *      2.6 执行 ObservableOnSubscribe OOS 对象实例的 subscribe(ObservableEmitter) 重写方法，即执行用户自定义实现的产生
         *          数据源方法，即完成了当订阅时才会下发数据的功能
         *          执行 OOS:subscribe:CreateEmitter:onNext 代码段
         *      2.7 执行 CreateEmitter：LS：onNext 方法
         *      2.8 最终执行 Consumer 的lambda 代码
         */
        Observable<Object> observable = Observable.create(observer -> {
            observer.onNext("处理的数字： "  + Math.random() * 100);
            observer.onComplete();
        });


        Disposable s1 = observable.subscribe(consumer -> {
            System.out.println(Thread.currentThread().getName() + " 创建的数字 " + consumer);
        });

        Disposable s2 = observable.subscribe(consumer -> {
            System.out.println(Thread.currentThread().getName() + " 创建的数字 " + consumer);
        });
    }


    public static void rangeTest() {
        RxUtil.log("before ");
        Observable.range(3, 5).subscribe(RxUtil::log);
        RxUtil.log("after");
    }

    public static void obs_cache() {
        /**
         *
         *  1.创建数据源
         *   1.1 前面几步流程同上
         *   1.2 创建 ObservableCache 对象实例，初始化属性  ObservableCreate：source
         *      初始化 ObservableCache，调用父类构造函数 AbstractObservableWithUpstream，初始化属性
         *      初始化 CacheDisposable[] ：observers数组
         *
         *  2.订阅者消费数据
         *     2.1 前面几步流程同上
         *     2.2 调用 ObservableCache：subscribeActual(Observer:LS)
         *         将 Consumer 封装成为 CacheDisposable：CDs对象
         *         CDs.observer = LS
         *         CDs.parent = CDa
         *         CDs.node = CDa.head
         *
         *         将 CDs 对象加入 observers 数组（observers:所有订阅者集合）
         *         判断是否有订阅者订阅数据
         *         如果第一次订阅数据：
         *              ObservableSource：ObservableCreate.subscribe(ObservableCache:OCa)
         *              执行 ObservableCreate.subscribeActual(ObservableCache)
         *              创建 ObservableEmitter CreateEmitter 对象的实例，初始化属性observer : OCa
         *              执行 ObservableOnSubscribe OOS 对象实例的 subscribe(ObservableEmitter) 重写方法，即执行用户自定义实现的产生
         *              数据源方法，即完成了当订阅时才会下发数据的功能
         *              执行 OOS.subscribe(CreateEmitter):onNext 代码段
         *              执行 CreateEmitter：OCa：onNext 方法,在 OCa:onNext 中将数据源保存到
         *              链表中 n.values[0] = t
         *              遍历 observers 执行 OCa.replay(CDs)
         *              // 数据是如何保存到 node.values[offset]
         *              // downstream : LS
         *              LS:onNext 订阅数据
         *           后续订阅数据：直接执行 replay 方法
         *
         */
        // 1. 实际类型为 Cache
        Observable<Object> observable = Observable.create(observer -> {
            observer.onNext("处理的数字： "  + Math.random() * 100);
            observer.onComplete();
        }).cache();

        System.out.println(observable.toString());

        // 实际类型：LS
        Disposable s1 = observable.subscribe(consumer -> {
            System.out.println(Thread.currentThread().getName() + " 创建的数字 " + consumer);
        });

        Disposable s2 = observable.subscribe(consumer -> {
            System.out.println(Thread.currentThread().getName() + " 创建的数字 " + consumer);
        });

        s1.dispose();

    }

   static void infiniteUndispose() {

        Observable<Object> cache = Observable.create(observer -> {
            Thread t = new Thread(() -> {
                BigInteger i = BigInteger.ZERO;
                while (!observer.isDisposed()) {
                    observer.onNext(i);
                    i = i.add(BigInteger.ONE);
                    System.out.println(Thread.currentThread().getName() + "-消费的数字-" + i.toString());
                }
            });
            t.start();
        }).cache();

        Disposable s1 = cache.subscribe(RxUtil::log);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        s1.dispose();
        System.out.println("取消订阅！");
        try {
            Thread.sleep(5000);
            System.out.println("主线程结束！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
       // observable();
      //  rangeTest();
        // 1.确定 所参与的对象
        // 2.确定关键步骤
        // 3.画出流程
       // obs_cache();
        infiniteUndispose();

    }
}
