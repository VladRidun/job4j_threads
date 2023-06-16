package ru.job4j.concurrent.pool;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        for (int i = 0; i < size; i++) {
            sums[i] = new Sums();
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < size; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums[i].setColSum(colSum);
            sums[i].setRowSum(rowSum);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        for (int i = 0; i < size; i++) {
            sums[i] = new Sums();
            sums[i].setColSum(getColTask(matrix, i, size).get());
            sums[i].setRowSum(getRowTask(matrix, i, size).get());
        }
        return sums;
    }

    public static CompletableFuture<Integer> getColTask(int[][] data, int col, int size) {
        return CompletableFuture.supplyAsync(() -> {
            int colSum = 0;
            for (int i = 0; i < size; i++) {
                colSum += data[i][col];
            }
            return colSum;
        });
    }

    public static CompletableFuture<Integer> getRowTask(int[][] data, int row, int size) {
        return CompletableFuture.supplyAsync(() -> {
            int rowSum = 0;
            for (int j = 0; j < size; j++) {
                rowSum += data[row][j];
            }
            return rowSum;
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] sums1 = sum(matrix);
        Sums[] sums2 = asyncSum(matrix);
        for (Sums s: sums1) {
            System.out.print(s.getColSum());
            System.out.print(s.getRowSum());
        }
        System.out.println("////////////////////////////////// multi");
        for (Sums s: sums2) {
            System.out.print(s.getColSum());
            System.out.print(s.getRowSum());
        }
    }

}