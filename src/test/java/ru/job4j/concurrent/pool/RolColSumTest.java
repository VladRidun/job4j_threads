package ru.job4j.concurrent.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;
import static ru.job4j.concurrent.pool.RolColSum.asyncSum;
import static ru.job4j.concurrent.pool.RolColSum.sum;

class RolColSumTest {

    @Test
    void WhenSumLineEqualsTrue() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums sum1 = new RolColSum.Sums();
        sum1.setColSum(12);
        sum1.setRowSum(6);
        RolColSum.Sums[] sum2 = sum(matrix);
        assertThat(sum2[0].getColSum()).isEqualTo(sum1.getColSum());
        assertThat(sum2[0].getRowSum()).isEqualTo(sum1.getRowSum());
    }

    @Test
    void WhenAsyncSumLineEqualsTrue() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums sum1 = new RolColSum.Sums();
        sum1.setColSum(12);
        sum1.setRowSum(6);
        RolColSum.Sums[] sum2 = asyncSum(matrix);
        assertThat(sum2[0].getColSum()).isEqualTo(sum1.getColSum());
        assertThat(sum2[0].getRowSum()).isEqualTo(sum1.getRowSum());
    }
}