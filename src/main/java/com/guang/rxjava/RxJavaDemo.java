package com.guang.rxjava;

import com.guang.rxjava.util.RxUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

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

    public static void main(String[] args) {
        observable();
      //  rangeTest();
    }
}
