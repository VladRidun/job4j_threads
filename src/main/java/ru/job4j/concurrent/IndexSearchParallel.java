package ru.job4j.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class IndexSearchParallel<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T elementToSearch;
    private final int from;
    private final int to;

    public IndexSearchParallel(T[] array, T elementToSearch, int from, int to) {
        this.array = array;
        this.elementToSearch = elementToSearch;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        int result;
        if (array.length > 10) {
            int mid = (from + to) / 2;
            IndexSearchParallel left = new IndexSearchParallel(array, elementToSearch, from, mid);
            IndexSearchParallel right = new IndexSearchParallel(array, elementToSearch, mid + 1, to);
            left.fork();
            right.fork();
            int resultLeft = (int) left.join();
            int resultRight = (int) right.join();
           result = Math.max(resultLeft, resultRight);
        } else {
            result = search(array, elementToSearch, from, to);
        }
        return result;
    }

    public int search(T[] array, T elementToSearch, int from, int to) {
        for (int i = from; i <= to; i++) {
            if (array[i].equals(elementToSearch)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Integer[] array = new Integer[]{5, 6, 7, 7676, 78, 8, 9, 9, 0, 10, 11, 34};
        int from = 0;
        int to = array.length-1;
        int elSearch = 7676;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        System.out.println(forkJoinPool.invoke(new IndexSearchParallel<>(array, elSearch, from, to)));
    }
}
