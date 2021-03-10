import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
public class BakeryAlgorithm {
    public static int NUMBER_THREADS = 1;

    public static void main(String[] args) {
        Bakery bakery = new Bakery(NUMBER_THREADS);


    }
}

class Label{
    public int num;
    public int index;
    public void Label(){
        int num = 0;
        int index = 0;
    }
}


class BakeryThread implements Runnable{

    public BakeryThread(){

    }

    @Override
    public void run() {
        //check which thread is being used
    }
}

class Bakery implements Lock {
    boolean flag[];
    Label[] label;
    public Bakery (int numofThreads){
        flag = new boolean[numofThreads];
        label = new Label[numofThreads]();
        for (int i = 0; i < numofThreads; i++){
            flag[i] = false;
        }
    }

    @Override
    public void lock(){
        int id = 1; //placeholder
        flag[id] = true;
        //find the max number in labels and add 1
        label[id].num = findMaxLabel() + 1;
        int smol = findIndexOfMinLabel();
        /* if their label is smaller than ours, then wait for them to finish */
        while(flag[smol] && label[smol].num < label[id].num);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {}

    @Override
    public boolean tryLock() {return false;}

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException { return false; }

    @Override
    public void unlock(){
        int id = 1; //placeholder
        flag[id] = false;
        label[id].num = 0;
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
