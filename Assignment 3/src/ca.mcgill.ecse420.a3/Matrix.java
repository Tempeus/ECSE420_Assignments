package ca.mcgill.ecse420.a3;

public class Matrix {
    int dim;
    double[][] data;
    int rowDisplace, colDisplace;

    public Matrix(int d){
        dim = d;
        rowDisplace = colDisplace = 0;
        data = new double[d][d];
    }

    static double[][] covertToMatrix(double[] vec){
        int l = vec.length;
        double[][] mat = new double[l][l];
        for(int i = 0; i < l ; i++){
            mat[i][0] = vec[i];
        }
        return mat;
    }

    static double[] covertToVec(double[][] mat){
        int l = mat.length;
        double[] vec = new double[l];
        for(int i = 0; i < l ; i++){
            vec[i] = mat[i][0];
        }
        return vec;
    }

    private Matrix(double[][] matrix, int x, int y, int d){
        data = matrix;
        rowDisplace = x;
        colDisplace = y;
        dim = d;
    }

    public double get(int row, int col){
        return data[row + rowDisplace][col + colDisplace];
    }

    public void set(int row, int col, double value){
        data[row + rowDisplace][col + colDisplace] = value;
    }

    public int getDim(){
        return dim;
    }

    Matrix[][] split(){
        Matrix[][] result = new Matrix[2][2];
        int newDim = dim/2;
        result[0][0] = new Matrix(data, rowDisplace, colDisplace, newDim);
        result[0][1] = new Matrix(data, rowDisplace, colDisplace + newDim, newDim);
        result[1][0] = new Matrix(data, rowDisplace + newDim, colDisplace, newDim);
        result[1][1] = new Matrix(data, rowDisplace + newDim, colDisplace + newDim, newDim);
        return result;
    }

}
