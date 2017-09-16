package com.company;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by dafadong on 9/16/17.
 */
public class ForkJoinDemo extends RecursiveTask<Long> {
    static ConcurrentHashMap<Long, String> threads = new ConcurrentHashMap();
    volatile int[] arr;
    int start;
    int end;

    public ForkJoinDemo(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
        threads.putIfAbsent(Thread.currentThread().getId(),
                Thread.currentThread().getName());
    }

    public static void main(String args[]) {
        try {
            int[] arrays = new int[]
                    {1, 12, 2, 13, 3, 14, 4, 15, 5, 16, 17, 17, 177, 18, 8, 8, 19,
                            21, 212, 22, 213, 23, 214, 24, 215, 25, 216, 217, 217, 2177,
                            218, 28, 28, 219, 1, 213, 22, 42, 5, 52, 99, 120, 177, 18, 8, 8, 19,
                            61, 612, 62, 613, 63, 614, 64, 615, 65, 616, 617, 617, 6177,
                            618, 68, 68, 6191, 12, 2, 13, 3, 14, 4, 15, 5, 16, 17, 17, 177,
                            21, 212, 22, 213, 23, 214, 24, 215, 25, 216, 217, 217, 2177,
                            218, 28, 28, 219, 1, 213, 22, 42, 5, 52, 99, 120, 177, 18, 8, 8, 19,
                            61, 612, 62, 613, 63, 614, 64, 615, 65, 616, 617, 617, 6177,
                            618, 68, 68, 6191, 12, 2, 13, 3, 14, 4, 15, 5, 16, 17, 17, 177,
                            21, 212, 22, 213, 23, 214, 24, 215, 25, 216, 217, 217, 2177,
                            218, 28, 28, 219, 1, 213, 22, 42, 5, 52, 99, 120, 177, 18, 8, 8, 19,
                            61, 612, 62, 613, 63, 614, 64, 615, 65, 616, 617, 617, 6177,
                            618, 68, 68, 6191, 12, 2, 13, 3, 14, 4, 15, 5, 16, 17, 17, 177,
                            21, 212, 22, 213, 23, 214, 24, 215, 25, 216, 217, 217, 2177,
                            218, 28, 28, 219, 1, 213, 22, 42, 5, 52, 99, 120, 18, 8, 8, 19,
                            61, 612, 62, 613, 63, 614, 64, 615, 65, 616, 617, 1, 12, 2,
                            13, 3, 14, 4, 15, 5, 16, 17, 17, 177, 18, 8, 8, 19, 18, 8, 8, 19,
                            21, 212, 22, 213, 23, 214, 24, 215, 25, 216, 217, 217, 2177,
                            218, 28, 28, 219, 1, 213, 22, 42, 5, 52, 99, 120, 18, 8, 8, 19,
                            61, 612, 62, 613, 63, 614, 64, 615, 65, 616, 617, 617, 6177,
                            618, 68, 68, 6191, 12, 2, 13, 3, 14, 4, 15, 5, 16, 17, 17, 177,
                            21, 212, 22, 213, 23, 214, 24, 215, 25, 216, 217, 217, 2177,
                            218, 28, 28, 219, 1, 213, 22, 42, 5, 52, 99, 120, 18, 8, 8, 19,
                            61, 612, 62, 613, 63, 614, 64, 615, 65, 616, 617, 617, 6177,
                            618, 68, 68, 6191, 12, 2, 13, 3, 14, 4, 15, 5, 16, 17, 17, 177,
                            21, 212, 22, 213, 23, 214, 24, 215, 25, 216, 217, 217, 2177,
                            218, 28, 28, 219, 1, 213, 22, 42, 5, 52, 99, 120, 18, 8, 8, 19,
                            61, 612, 62, 613, 63, 614, 64, 615, 65, 616, 617, 617, 6177,
                            718, 78, 78, 7191, 19, 9, 13, 3, 14, 4, 15, 5, 17, 17, 17, 177,
                            91, 919, 99, 913, 93, 914, 94, 915, 95, 917, 917, 917, 9177,
                            918, 98, 98, 919, 1, 913, 99, 49, 5, 59, 99, 190, 18, 8, 8, 19,
                            71, 719, 79, 713, 73, 714, 74, 715, 75, 717, 717, 1, 19, 9,
                            13, 3, 14, 4, 15, 5, 17, 17, 17, 177, 18, 8, 8, 19, 18, 8, 8, 19,
                            91, 919, 99, 913, 93, 914, 94, 915, 95, 917, 917, 917, 9177,
                            918, 98, 98, 919, 1, 913, 99, 49, 5, 59, 99, 190, 18, 8, 8, 19,
                            71, 719, 79, 713, 73, 714, 74, 715, 75, 717, 717, 717, 7177,
                            718, 78, 78, 7191, 19, 9, 13, 3, 14, 4, 15, 5, 17, 17, 17, 177,
                            91, 919, 99, 913, 93, 914, 94, 915, 95, 917, 917, 917, 9177,
                            918, 98, 98, 919, 1, 913, 99, 49, 5, 59, 99, 190, 18, 8, 8, 19,
                            61, 612, 62, 613, 63, 614, 64, 615, 65, 616, 617, 617, 6177, 618, 68, 68, 619};
            ForkJoinDemo quickSort = new ForkJoinDemo(arrays, 0, arrays.length - 1);
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            Future<Long> future = forkJoinPool.submit(quickSort);

            System.out.printf("Thread Count: %d\n", forkJoinPool.getActiveThreadCount());

            Long res = future.get();

            System.out.printf("Thread Steal: %d\n", forkJoinPool.getStealCount());
            System.out.printf("Parallelism: %d\n", forkJoinPool.getParallelism());
            System.out.println(Arrays.toString(arrays));
            System.out.println("Total called times: " + res);
            threads.forEach((k, v) -> System.out.printf("\tID: %d, Value: %s.\n", k, v));

            forkJoinPool.shutdown();
        } catch (Exception e) {
            System.out.println("Get Exception: " + e.getMessage());
        }
    }

    @Override
    protected Long compute() {
        if ((end - start) < 5) {
            subQuickSort(arr, start, end);
            return 1L;

        } else {
            //System.out.println("Fork");

            int middleIndex = subQuickSortCore(arr, start, end);
            ForkJoinDemo quickSort = new ForkJoinDemo(arr, start, middleIndex - 1);
            ForkJoinDemo quickSort2 = new ForkJoinDemo(arr, middleIndex + 1, end);

            quickSort.fork();
            quickSort2.fork();
            return quickSort2.join() + quickSort.join();
        }
    }

    private void quickSort(int[] arrays) {
        subQuickSort(arrays, 0, arrays.length - 1);
    }

    private void subQuickSort(int[] arrays, int start, int end) {
        if (start >= end) {
            return;
        }
        int middleIndex = subQuickSortCore(arrays, start, end);
        subQuickSort(arrays, start, middleIndex - 1);
        subQuickSort(arrays, middleIndex + 1, end);
    }

    private int subQuickSortCore(int[] arrays, int start, int end) {
        int middleValue = arrays[start];
        while (start < end) {
            while (arrays[end] >= middleValue && start < end) {
                end--;
            }
            arrays[start] = arrays[end];
            while (arrays[start] <= middleValue && start < end) {
                start++;
            }
            arrays[end] = arrays[start];
        }
        arrays[start] = middleValue;
        return start;
    }
}