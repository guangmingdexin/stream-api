package com.guang.homework.question;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author guangyong.deng
 * @date 2021-12-02 9:05
 */
public class Question {

    public static void demo() {

        List<String> list = Arrays.asList("3:4,1", "6:7,5", "8:9,4", "14:1,13",
                "15:10,7", "3?1");

        // 不用流的方式
        List<String> result = new ArrayList<>();

        Stream<String> stream = list.stream().map(s -> {
//            char[] chars = s.toCharArray();
//            for (char c : chars) {
//                if((c == ':' || c == ','|| c == '?') && (!result.contains(String.valueOf(c)))) {
//                    result.add(String.valueOf(c));
//                }
//            }
            // 这里可以去重
            return s + "hello world";

        });

        System.out.println(list.stream().flatMap(s -> {
            return Stream.of(s.replaceAll("\\d+",""));
        }).distinct().collect(Collectors.joining()));



      list.stream().flatMap(s -> {
            return Stream.of(s.replaceAll("[^\\d]", ""));
        }).forEach(s -> System.out.print(s + " "));

        System.out.println();

        Map<String, Long> collect = list.stream().flatMap(s -> {
            return Stream.of(s.replaceAll("\\d+", ""));
        }).collect(Collectors.groupingBy(String::toString, Collectors.counting()));

        collect.forEach((key, value) -> {
            System.out.println("key- " + key + " " + "value- " + value);
        });

        System.out.println(1.5 % 1);

//        IntStream.range(1, 100).flatMap(value -> {
//
//            IntStream.range(value, 100).flatMap(value1 -> {
//
//                List<int[]> collect1 = isPositive(value, value1)
//                        .filter(ints -> ints.length > 0)
//                        .collect(Collectors.toList());
//
//                return collect1;
//            });
//
//            return collect;
//        });

//        stream.forEach(s -> {
//            System.out.println(s);
//        });

        // 不用流的方式
//        List<String> result = new ArrayList<>();
//
//        list.forEach(s -> {
//            char[] chars = s.toCharArray();
//            for (char c : chars) {
//                if((c == ':' || c == ','|| c == '?') && (!result.contains(String.valueOf(c)))) {
//                    result.add(String.valueOf(c));
//                }
//            }
//        });

     //   System.out.println(result);
    }

    /**
     * 判断 a 的平方和 b 的平方的和的平方根是否为正数
     *
     * @param a
     * @param b
     * @return
     */
    private static Stream<int[]> isPositive(int a, int b) {
        if(a >= b) {
            return Stream.of();
        }
        int sum = a * a + b * b;
        // 从一个开始到 sum / 2
        for (int i = 2; i <= sum / 2 ; i++) {
            if(i * i == sum) {
                return Stream.of(new int[]{a, b, sum});
            }
        }
        return Stream.of();
    }

    public static void main(String[] args) {
        demo();
    }
}
