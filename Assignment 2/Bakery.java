import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
public class Bakery implements Lock {
    volatile boolean flag[];
    volatile Label[] label;
    volatile int n;

    public Bakery(int numofThreads) {
        this.n = numofThreads;
        flag = new boolean[n];
        label = new Label[n];
        for (int i = 0; i < n; i++) {
            flag[i] = false;
            label[i] = new Label();
        }
    }

    @Override
    public void lock() {
        int id = ThreadID.get();
        flag[id] = true;
        int max = Label.max(label);
        label[id] = new Label(max + 1);
        /* if their label is smaller than ours, then wait for them to finish */
        while (conflict(id)) ;
        System.out.println("ThreadID: " + id + " with label " + label[id].counter);
    }

    private boolean conflict(int id) {
        for (int i = 0; i < label.length; i++) {
            if (i != id && flag[i] && label[id].compareTo(label[i]) > 0 && label[i].counter != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        int id = ThreadID.get();
        flag[id] = false;
        System.out.println("ThreadID: " + id + " has finished queueing");
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}

class Label implements Comparable<Label> {
    volatile int counter;
    volatile int id;

    Label() {
        counter = 0;
        id = ThreadID.get();
    }

    Label(int c) {
        counter = c;
        id = ThreadID.get();
    }

    static int max(Label[] labels) {
        int c = 0;
        for (Label label : labels) {
            c = Math.max(c, label.counter);
        }
        return c;
    }

    @Override
    public int compareTo(Label o) {
        if (this.counter < o.counter || (this.counter == o.counter && this.id < o.id)) {
            return -1;
        }
        else if (this.counter > o.counter) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
