import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestFilterLock {
    private final static int NTHREADS = 8;
    private final static int COUNT = 100;
    private static int PER_THREAD = COUNT / NTHREADS;
    public static boolean lockWorks = true;
    public static int c = 0;
    public static ArrayList<Integer> workorder = new ArrayList<>();

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
        if(lockWorks){
            System.out.println("Lock is working");
        }
        else{
            System.out.println("Not Working");
        }
    }

    public static class FilterLockThread implements Runnable {
        @Override
        public void run() {

            for( int i = 0; i < PER_THREAD; i++){
                lock.lock();
                try{
                    Thread.sleep(15);
                    workorder.add(c++);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
                if(!check()){
                    lockWorks = false;
                }
            }

        }
        public boolean check(){
            for(int i = 0; i < workorder.size(); i++){
                if(workorder.get(i) != i){
                    return false;
                }
            }
            return true;
        }
    }
}

