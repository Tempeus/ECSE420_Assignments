import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
public class Bakery implements Lock {
    boolean flag[];
    Label[] label;

    public Bakery (int numofThreads){
        flag = new boolean[numofThreads];
        label = new Label[numofThreads];
        for (int i = 0; i < numofThreads; i++){
            flag[i] = false;
            label[i] = new Label();
        }
    }

    @Override
    public void lock(){
        int id = ThreadID.get();
        flag[id] = true;
        //find the max number in labels and add 1
        label[id].num = findMaxLabel() + 1;
        int smol = findIndexOfMinLabel();
        /* if their label is smaller than ours, then wait for them to finish */
        while(flag[smol] && label[smol].num < label[id].num);
        System.out.println("ThreadID: "+id+ " with label "+ label[id].num);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {}

    @Override
    public boolean tryLock() {return false;}

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException { return false; }

    @Override
    public void unlock(){
        int id = ThreadID.get();
        flag[id] = false;
        label[id].num = 0;
        System.out.println("ThreadID: "+ id+ " has finished queueing");
    }

    @Override
    public Condition newCondition() { return null; }

    private int findMaxLabel(){
        int max = label[0].num;

        for(int i = 0; i < label.length; i++){
            if(label[i].num > max){
                max = label[i].num;
            }
        }
        return max;
    }

    private int findIndexOfMinLabel(){
        int min = label[0].index;

        for(int i = 0; i < label.length; i++){
            if(label[i].index < min){
                min = label[i].index;
            }
        }
        return min;
    }
}

class Label{
    public int num;
    public int index;
    public void Label(){
        num = 0;
        index = 0;
    }
}
