package school21.AP1JvT03.domain.model;

import java.util.Arrays;

public class Matrix {
    public static final int SIZE = 3;
    private int[][] matrix;

    public Matrix() {
        matrix = new int[SIZE][SIZE];
    }

    public Matrix(int[][] matrix) {
        this.matrix = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            this.matrix[i] = Arrays.copyOf(matrix[i], SIZE);
        }
    }

    public int[][] getMatrix() {
        int[][] copy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            copy[i] = Arrays.copyOf(this.matrix[i], SIZE);
        }
        return copy;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            this.matrix[i] = Arrays.copyOf(matrix[i], SIZE);
        }
    }
}
