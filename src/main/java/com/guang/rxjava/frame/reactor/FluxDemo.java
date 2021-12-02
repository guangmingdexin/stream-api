package com.guang.rxjava.frame.reactor;

import com.guang.rxjava.frame.reactor.util.FluxUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.function.Consumer;

/**
 * @author guangyong.deng
 * @date 2021-11-30 10:51
 */
public class FluxDemo {


    public static void fluxCreateDemo() {

//        Flux.create(new Consumer<FrameFluxSink<? extends Object>>() {
//            @Override
//            public void accept(FrameFluxSink<?> fluxSink) {
//
//            }
//        });

        Flux<Object> flux = Flux.create(obserser -> {
            // FrameFluxSink 的匿名实现类

            obserser.next("处理的数字： " + Math.random() * 100);
        });
        flux.subscribe(consumer -> {
            System.out.println(Thread.currentThread().getName() + " 创建的数字 " + consumer);
        });
        flux.subscribe(consumer -> {
            System.out.println(Thread.currentThread().getName() + " 创建的数字 " + consumer);
        });

    }

    public static void fluxBufferTest() {

        Flux.create(emitter -> {

            for (int i = 0; i < 1000; i++) {
                if(emitter.isCancelled()) {
                    return;
                }
                System.out.println("soource create " + i);
                emitter.next(i);
            }
        }).doOnNext(s -> System.out.println("source push " + s))
                .publishOn(Schedulers.single())
                .subscribe(customer -> {
                    FluxUtil.sleep(10);
                    System.out.println("获取到的 Customer 的 Id 是 " + customer);
                });

        FluxUtil.sleep(1000);

    }

    public static void main(String[] args) {
       fluxCreateDemo();
      //  fluxBufferTest();

//        String regex = "20\\d\\d";
//        System.out.println("2019".matches(regex));
//        System.out.println("2119".matches(regex));
    }
}
