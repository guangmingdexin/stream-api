package com.guang.homework.java_base;

import java.util.Arrays;

/**
 * @author guangyong.deng
 * @date 2021-11-24 13:42
 */
public class SortDemo {


    /**
     * 对数组进行排序
     *
     * @param arr 数组
     */
    public static void sort(int[] arr) {
        if(arr == null || arr.length <= 1) {
            return;
        }

        // 冒泡
        for (int i = arr.length - 1; i >= 0 ; i--) {
            for (int j = 0; j < i; j++) {
                if(arr[j] > arr[j + 1]) {
                    // 交换
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 二分查找
     *
     * @param arr 源数组
     * @param target 目标值
     * @return 目标索引，不存在则返回 -1
     */
    public static int find(int[] arr, int target) {

        // 二分法首先确定边界！[left, right]
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if(arr[mid] > target) {
                right = mid + 1;
            }else if(arr[mid] < target) {
                left = mid - 1;
            }else {
                return mid;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{7, 9, 3, 4, 2};

        sort(arr);

        System.out.println(Arrays.toString(arr));

        System.out.println(find(arr, 3));
    }
}
