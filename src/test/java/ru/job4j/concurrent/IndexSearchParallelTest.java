package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.assertj.core.api.Assertions.*;

class IndexSearchParallelTest {

    @Test
    void whenTypeIsIntegerAndSizeLess10() {
        Integer[] array = new Integer[]{5, 6, 7, 7676, 78, 8, 9, 9, 0, 10};
        int from = 0;
        int to = array.length - 1;
        int elSearch = 7676;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        assertThat(3).isEqualTo(forkJoinPool.invoke(new IndexSearchParallel<>(array, elSearch, from, to)));
    }

    @Test
    void whenTypeIsStringAndSizeOver10() {
        String[] array = new String[]{"Artem", " Vlad", "Maksim", "Marina", "Masha", "Fedor", "Valera", "Galina", "Andrei", "Ivan", "Lena", "Sveta", "Natasha"};
        int from = 0;
        int to = array.length - 1;
        String elSearch = "Marina";
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        assertThat(3).isEqualTo(forkJoinPool.invoke(new IndexSearchParallel<>(array, elSearch, from, to)));
    }


    @Test
    void whenElementNotFound() {
        String[] array = new String[]{"Artem", " Vlad", "Maksim", "Masha", "Fedor", "Valera", "Galina", "Andrei", "Ivan", "Lena", "Sveta", "Natasha"};
        int from = 0;
        int to = array.length - 1;
        String elSearch = "Marina";
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        assertThat(-1).isEqualTo(forkJoinPool.invoke(new IndexSearchParallel<>(array, elSearch, from, to)));
    }
}