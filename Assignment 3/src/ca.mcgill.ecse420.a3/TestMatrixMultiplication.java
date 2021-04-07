package ca.mcgill.ecse420.a3;

import java.util.concurrent.ExecutionException;

public class TestMatrixMultiplication {

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
        double[][] matrix = {{1,2,3,4}, {4,5,6,7}, {7,8,9,10}, {10,11,12,13}};
        double[][] matrix3 = {{1,1,1,1}, {2,2,2,2}, {3,3,3,3}, {4,4,4,4}};
        double[][] matrix2 = {{1,2,3,4}, {1,2,3,4}, {1,2,3,4}, {1,2,3,4}};
        double[] vector = {5,5,5,5};

        Matrix a = new Matrix(4);
        a.data = matrix;

        Matrix b = new Matrix(4);
        b.data = matrix2;

        Matrix c = new Matrix(4);
        c.data = Matrix.covertToMatrix(vector);

        Matrix res = ParallelMultiplication.mul(a, c);
        printVec(Matrix.covertToVec(res.data));
    }


}
