import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestBakeryAlgorithm {
    public static int NUM_THREAD = 5;
    public static Bakery bakery = new Bakery(NUM_THREAD);

    public static void main(String[] args) {
        Thread[] bakerythreads = new Thread[NUM_THREAD];

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREAD);

        for(int i= 0; i< NUM_THREAD; i++){
            bakerythreads[i] = new Thread(new BakeryThread());
            executor.execute(bakerythreads[i]);
        }
        executor.shutdown();

        while (!executor.isTerminated()){}

    }

    static class BakeryThread implements Runnable{
        @Override
        public void run() {
            for(int i = 0; i < NUM_THREAD; i++){
                bakery.lock();
                bakery.unlock();
            }
        }
    }
}