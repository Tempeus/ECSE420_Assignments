//public class TestFilterLock {
//    private final static int NTHREADS = 2;
//    private final static int COUNT = 100;
//    private  static int PER_THREAD = COUNT / NTHREADS;
//
//    public static void main(String args[]) {
//        int wrong = 0;
//        long startTime = System.nanoTime();
//
//        for (int i = 0; i < noOfExperiments; i++) {
//            count = 0;
//
//            spawnThreads();
//
//            // Check to see that count is correct value
//            if (count != threadCount*countToThis) {
//                System.out.println("Wrong : " + count);
//                wrong++;
//            }
//        }
//
//        long endTime = System.nanoTime();
//        System.out.println("That took " + (endTime - startTime)/1000000 + " milliseconds");
//        System.out.println("Mistakes:  " + wrong + "/" + noOfExperiments);
//    }
//
//}
//
//    public static class FilterLockThread implements Runnable {
//
//        public int me;
//        public static final int countToThis = 10000;
//        public static final int noOfExperiments = 3;
//        public static volatile int count = 0;
//        public static int threadCount = 8;
//        public static FilterLock lock = new FilterLock(threadCount);
//
//        public FilterLockThread(int newMe) {
//            me = newMe;
//        }
//
//        @Override
//        public void run() {
//            int i = 0;
//            while (i < countToThis) {
//                lock.lock();
//                count = count + 1;
//                i = i + 1;
//                lock.unlock();
//            }
//        }
//    }