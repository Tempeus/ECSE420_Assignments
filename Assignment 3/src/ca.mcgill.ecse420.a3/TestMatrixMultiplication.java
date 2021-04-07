package ca.mcgill.ecse420.a3;

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

    public static void main(String[] args) {
        double[][] matrix = {{1,2,3}, {4,5,6}, {7,8,9}};
        double[] vector = {5,5,5};

        double[] res = SequentialMultiplication.sequentialMultiply(matrix, vector);
        printMatix(matrix);
        System.out.println();
        printVec(res);

    }


}
