import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestBakeryAlgorithm {
    public static int NUM_THREAD = 5;
    public static Bakery bakery = new Bakery(NUM_THREAD);
    public static boolean lockWorks = true;
    public static int c = 0;

    public static void main(String[] args) {
        ThreadID.reset();
        Thread[] bakerythreads = new Thread[NUM_THREAD];

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREAD);

        for(int i= 0; i< NUM_THREAD; i++){
            bakerythreads[i] = new Thread(new BakeryThread());
            executor.execute(bakerythreads[i]);
        }
        executor.shutdown();
        while (!executor.isTerminated()){}
        if(lockWorks){
            System.out.println("Lock is working");
        }
    }

    static class BakeryThread implements Runnable{
        public ArrayList<Integer> workorder = new ArrayList<>();
        @Override
        public void run() {
            for(int i = 0; i < 9; i++){
                bakery.lock();
                try {
                    Thread.sleep(15);
                    workorder.add(c++);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bakery.unlock();
            }
            if(!check()){
                lockWorks = false;
            }
        }

        public boolean check(){
            for(int i = 0; i < workorder.size(); i++){
                if(workorder.get(i) != i)
                    return false;
            }
            return true;
        }
    }
}