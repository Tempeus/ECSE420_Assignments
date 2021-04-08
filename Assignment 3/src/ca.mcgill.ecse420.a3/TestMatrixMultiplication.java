package ca.mcgill.ecse420.a3;

import java.util.concurrent.ExecutionException;

public class TestMatrixMultiplication {

    private static double[][] generateRandomMatrix(int numRows, int numCols) {
        double matrix[][] = new double[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                matrix[row][col] = (double) ((int) (Math.random() * 10.0));
            }
        }
        return matrix;
    }

    private static double[] generateRandomVector(int numRows) {
        double vec[] = new double[numRows];
        for (int row = 0; row < numRows; row++) {
                vec[row] = (Math.random() * 10.0);
        }
        return vec;
    }

    public static void printVec(double[] vec){
        for(int i = 0; i<vec.length; i++){
            System.out.println(vec[i]);
        }
    }
    public static void printMatix(double[][] m){
        for(int i = 0; i< m.length; i++){
            for(int j = 0; j < m[0].length; j++){
                System.out.print(" "+m[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        double[][] matrix = {{1,2,3,4}, {4,5,6,7}, {7,8,9,10}, {10,11,12,13}};
//        double[][] matrix3 = {{1,1,1,1}, {2,2,2,2}, {3,3,3,3}, {4,4,4,4}};
//        double[][] matrix2 = {{1,2,3,4}, {1,2,3,4}, {1,2,3,4}, {1,2,3,4}};
//        double[] vector = {5,5,5,5};
//
//        Matrix a = new Matrix(4);
//        a.data = matrix;
//
//        Matrix b = new Matrix(4);
//        b.data = matrix2;
//
//        Matrix c = new Matrix(4);
//        c.data = Matrix.covertToMatrix(vector);

        double[][] matrix = generateRandomMatrix(2000,2000);
        double[] vec = generateRandomVector(2000);
        long startTime = System.currentTimeMillis();
        double[] d = SequentialMultiplication.sequentialMultiply(matrix, vec);
        long endTime = System.currentTimeMillis();

        Matrix a = new Matrix(4);
        a.data = matrix;

        Matrix c = new Matrix(4);
        c.data = Matrix.covertToMatrix(vec);

        long SEQ_TIME = endTime - startTime;
        System.out.println("\nSequential time is " + SEQ_TIME + " milliseconds");

        startTime = System.currentTimeMillis();
        Matrix e = ParallelMultiplication.mul(a, c);
        endTime = System.currentTimeMillis();

        long PAR_TIME = endTime - startTime;
        System.out.println("\nParallel time is " + PAR_TIME + " milliseconds");


//




//        Matrix res = ParallelMultiplication.mul(a, c);
//        printVec(Matrix.covertToVec(res.data));
    }


}
