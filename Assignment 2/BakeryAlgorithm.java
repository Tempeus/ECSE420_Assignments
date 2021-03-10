
public class BakeryAlgorithm {

    private static int NUMBER_THREADS = 1;

    public static void main(String[] args) {
        Bakery threads = new Bakery(NUMBER_THREADS);


    }
}

class Bakery implements Lock, Runnable {
    boolean flag[];
    Label[] label;
    public Bakery (){
        flag = new boolean[NUMBER_THREADS];
        label = new Label[NUMBER_THREADS];
        for (int i = 0; i < n; i++){
            flag[i] = false;
            label[i] = 0;
        }
    }

    public void lock(id){
        flag[id] = true;
        //find the max number in labels and add 1
        label[id] = findMaxLabel() + 1;
        while(1 /*someone else's has a flag && their label is smaller than ours, then wait for them to finish */ );
    }

    public void unlock(id){
        flag[id] = false;
        label[id] = 0;
    }

    private int findMaxLabel(){
        int max = label[0];

        for(int i = 0; i < label.length; i++){
            if(label[i] > max){
                m = label[i];
            }
        }
        return max;
    }

    public void run(){

    }
}