package ru.job4j.concurrent.pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums getSum(int[][] matrix, int index) {
        Sums sum = new Sums(0, 0);
        int rowSum = 0;
        int colSum = 0;
        for (int i = 0; i < matrix.length; i++) {
            rowSum += matrix[index][i];
            colSum += matrix[i][index];
        }
        sum.setColSum(colSum);
        sum.setRowSum(rowSum);
        return sum;
    }

    public static Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        for (int i = 0; i < size; i++) {
            sums[i] = new Sums(0, 0);
            Sums sum = getSum(matrix, i);
            sums[i] = sum;
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        for (int i = 0; i < size; i++) {
            int finalI = i;
            sums[i] = CompletableFuture.supplyAsync(() -> getSum(matrix, finalI)).get();
        }
        return sums;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] sumTest = {new Sums(6, 12), new Sums(15, 15), new Sums(24, 18)};
        Sums[] sums1 = sum(matrix);
        Sums[] sums2 = asyncSum(matrix);
        for (Sums s : sums1) {
            System.out.println(s);
        }
        System.out.println("multi:");
        for (Sums s : sums2) {
            System.out.println(s);
        }
        System.out.println("Test:");
        for (Sums s : sumTest) {
            System.out.println(s);
        }
    }
}