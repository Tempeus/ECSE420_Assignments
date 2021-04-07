package ca.mcgill.ecse420.a3;

public class SequentialMultiplication {
    public static double[] sequentialMultiply(double[][] matrix, double[] vector) {
        int m = matrix.length;
        int n = matrix[0].length;
        if (vector.length != n) throw new RuntimeException("Illegal matrix dimensions.");

        double[] result = new double[m];
        for (int i = 0; i < m; i++){
            result[i] = 0;
            for (int j = 0; j < m; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }
}
