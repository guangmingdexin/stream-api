package com.guang.stream.submission;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.LongStream;

/**
 * @author guangyong.deng
 * @date 2021-11-23 16:26
 */
public class ConsumeSubmissionPublisher {

    public static void main(String[] args) {
        try {
            publish();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void publish() throws ExecutionException, InterruptedException {

        CompletableFuture future = null;

        try (SubmissionPublisher<Long> publisher = new SubmissionPublisher<>()){
            System.out.println("Subscriber Buffer Size: " + publisher.getMaxBufferCapacity());
            future = publisher.consume(System.out::println);
            LongStream.range(1, 1000).forEach(publisher::submit);
        }finally {
            future.get();
        }

    }
}
