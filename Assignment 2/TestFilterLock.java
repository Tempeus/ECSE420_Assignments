import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestFilterLock {
    private final static int NTHREADS = 8;
    private final static int COUNT = 100;
    private static int PER_THREAD = COUNT / NTHREADS;

    public static FilterLock lock = new FilterLock(NTHREADS);

    public static void main(String[] args){

        Thread[] filterLockThreads = new Thread[NTHREADS];

        ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);

        for(int i= 0; i< NTHREADS; i++){
            filterLockThreads[i] = new Thread(new FilterLockThread());
            executor.execute(filterLockThreads[i]);
        }
        executor.shutdown();

        while (!executor.isTerminated()){}
    }

    public static class FilterLockThread implements Runnable {

        @Override
        public void run() {

            for( int i = 0; i < PER_THREAD; i++){
//            while (true) {
                lock.lock();
                lock.unlock(); // Release the lock
            }

        }
    }
}