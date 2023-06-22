package ru.job4j.concurrent.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;
import static ru.job4j.concurrent.pool.RolColSum.asyncSum;
import static ru.job4j.concurrent.pool.RolColSum.sum;

class RolColSumTest {

    @Test
    void whenSumLineEqualsTrue() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] sumTest = {new Sums(6, 12), new Sums(15, 15),new Sums(24, 18)};
        Sums[] sumGet = sum(matrix);
        assertThat(sumGet).isEqualTo(sumTest);
    }

    @Test
    void whenAsyncSumLineEqualsTrue() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] sumTest = {new Sums(6, 12), new Sums(15, 15),new Sums(24, 18)};
        Sums[] sumsGet = asyncSum(matrix);
        assertThat(sumsGet).containsExactly(sumTest);
    }
}