import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class FilterLock implements Lock {

    volatile int[] level;
    volatile int[] victim;
    volatile int n;

    public FilterLock(int n) {
        this.n = n;
        level  = new int[n];
        victim = new int[n];
        for(int i = 0; i < n; i++){
            level[i] = 0;
        }
    }

    // Check if there are other threads that are occupying the same or higher level
    private boolean CheckLevel(int currentId, int currentLevel) {
        for (int id = 0; id < n; id++){
            if (id != currentId && level[id] >= currentLevel) {
                return true;
            }
        }
        return false;
    }

    public void lock() {
        int me = ThreadID.get();
        for (int L = 1; L < n; L++) {
            level[me]  = L;
            victim[L] = me;
            while (CheckLevel(me, L) && victim[L] == me) {};
            System.out.println("ThreadID: "+me+ " is at level "+L);
        }

    }

    public void unlock() {
        int me = ThreadID.get();
        level[me] = 0;
        System.out.println("ThreadID: "+ me+ "has finished queueing");
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {}

    @Override
    public boolean tryLock() {return false;}

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException { return false; }

    @Override
    public Condition newCondition() { return null; }

}