package ca.mcgill.ecse420.a3;
import java.util.concurrent.ThreadLocalRandom;

public class TestFineGrainedAlgorithm extends Thread{
    public static final int MIN = 1;
    public static final int MAX = 5;
    //Generate a random value
    Integer item;
    Integer check;
    int id;

    FineGrainedAlgorithm<Integer> fineGrainedList;

    TestFineGrainedAlgorithm(int id, FineGrainedAlgorithm<Integer> fineGrainedList){
        this.id = id++;
        this.fineGrainedList = fineGrainedList;
        item = ThreadLocalRandom.current().nextInt(MIN, MAX+1);
        check = ThreadLocalRandom.current().nextInt(MIN, MAX+1);
    }

    @Override
    public void run() {

        if (fineGrainedList.add(item)) {
            System.out.println("Thread " + id + " added " + item);

        } else {
            System.out.println("Thread " + id + " could NOT add " + item);

        }

        // Checks a random item in the list
        if (fineGrainedList.contains(check)) {
            System.out.println("Thread " + id + " found " + check);
        } else {
            System.out.println("Thread " + id + " could NOT find " + check);
        }

        if (fineGrainedList.remove(item)) {
            System.out.println("Thread " + id + " removed " + item);

        } else {
            System.out.println("Thread " + id + " could NOT remove " + item);

        }

        if (fineGrainedList.contains(check)) {
            System.out.println("Thread " + id + " found" + check);
        } else {
            System.out.println("Thread " + id + " could NOT find " + check);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int NUM_THREADS = 10;

        FineGrainedAlgorithm<Integer> FGList = new FineGrainedAlgorithm();

        TestFineGrainedAlgorithm[] FGThreads = new TestFineGrainedAlgorithm[NUM_THREADS];

        System.out.println("Initializing All Threads");

        for (int i = 0; i < NUM_THREADS; i++) {
            FGThreads[i] = new TestFineGrainedAlgorithm(i, FGList);
        }
        System.out.println("All Threads Initialized");

        System.out.println("Populating List with Add()");
        for (int i = 0; i < NUM_THREADS; i++) {
            FGThreads[i].start();
        }
    }
}
