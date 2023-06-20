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
        if (from - to <= 10) {
            return searchLine();
        }
        int mid = (from + to) / 2;
        IndexSearchParallel<T> left = new IndexSearchParallel(array, elementToSearch, from, mid);
        IndexSearchParallel<T> right = new IndexSearchParallel(array, elementToSearch, mid + 1, to);
        left.fork();
        right.fork();
        Integer resultLeft = left.join();
        Integer resultRight = right.join();
        return Math.max(resultLeft, resultRight);
    }

    public int searchLine() {
        for (int i = from; i <= to; i++) {
            if (elementToSearch.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    public static <T> int search(T[] array, T elSearch) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new IndexSearchParallel<>(array, elSearch, 0, array.length - 1));
    }

    public static void main(String[] args) {
        Integer[] array = new Integer[]{5, 6, 7, 7676, 78, 8, 9, 9, 0, 10, 11, 34};
        int from = 0;
        int to = array.length - 1;
        int elSearch = 7676;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        System.out.println(forkJoinPool.invoke(new IndexSearchParallel<>(array, elSearch, from, to)));
    }
}