package ca.mcgill.ecse420.a3;

import java.util.concurrent.*;

public class ParallelMultiplication {

    private static final int NUMBER_THREADS = 2;
    private static final int MATRIX_SIZE = 100;

    static ExecutorService exec = Executors.newCachedThreadPool();

    static  Matrix mul(Matrix a, Matrix b) throws ExecutionException, InterruptedException {
        int n = a.getDim();
        Matrix c = new Matrix(n);
        Future<?> future = exec.submit(new MulTask(a,b,c));
        future.get();
        return  c;
    }

    static  Matrix add(Matrix a, Matrix b) throws ExecutionException, InterruptedException {
        int n = a.getDim();
        Matrix c = new Matrix(n);
        Future<?> future = exec.submit(new AddTask(a,b,c));
        future.get();
        return  c;
    }

    static class AddTask implements Runnable {

        Matrix a, b, c;

        public  AddTask(Matrix myA, Matrix myB, Matrix myC){
            a = myA; b = myB; c = myC;
        }

        public void run() {

            try {
                int n = a.getDim();

                if(n == 1) {
                    c.set(0,0, a.get(0,0) + b.get(0,0));
                } else {

                    Matrix[][] aa = a.split(), bb = b.split(), cc = c.split();

                    Future<?>[][] future = (Future<?>[][]) new Future[2][2];

                    for(int i = 0; i  < 2 ; i++){
                        for(int j = 0; j < 2 ; j++){
                            future[i][j] = exec.submit(new AddTask(aa[i][j], bb[i][j], cc[i][j]));
                        }
                    }
                    for (int i = 0; i < 2; i++){
                        for(int j = 0; j < 2; j++){
                            future[i][j].get();
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    static class MulTask implements Runnable{

        Matrix a, b, c, lhs, rhs;

        public MulTask(Matrix myA, Matrix myB, Matrix myC) {

            a = myA; b = myB; c = myC;
            lhs = new Matrix(a.getDim());
            rhs = new Matrix(a.getDim());
        }

        public void run() {

            try {

                if (a.getDim() == 1) {

                    c.set(0, 0, a.get(0,0) * b.get(0,0));
                } else {

                    Matrix[][] aa = a.split(), bb = b.split(), cc = c.split();
                    Matrix[][] ll = lhs.split(), rr = rhs.split();
                    Future<?>[][][] future = (Future<?>[][][]) new Future[2][2][2];
                    for (int i = 0; i < 2; i++)
                        for (int j = 0; j < 2; j++) {
                            future[i][j][0] =
                                    exec.submit(new MulTask(aa[i][0], bb[0][i], ll[i][j]));
                            future[i][j][1] =
                                    exec.submit(new MulTask(aa[1][i], bb[i][1], rr[i][j]));
                        }

                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            for (int k = 0; k < 2; k++) {
                                future[i][j][k].get();
                            }
                        }
                    }
                    Future<?> done = exec.submit(new ParallelMultiplication.AddTask(lhs, rhs, c));
                    done.get();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


}
