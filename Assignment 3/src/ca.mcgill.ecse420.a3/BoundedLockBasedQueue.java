package ca.mcgill.ecse420.a3;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedLockFreeQueue<T> {
    private T[] items;

    //Locks
    private Lock headLock;
    private Lock tailLock;

    //Lock Condition
    private Condition notEmpty;
    private Condition notFull;

    //indexes
    private int head;
    private int tail;

    //queue size
    private AtomicInteger size;

    public BoundedLockFreeQueue(int capacity){
        //initialization
        items = (T[]) new Object[capacity];

        headLock = new ReentrantLock();
        tailLock = new ReentrantLock();
        notEmpty = headLock.newCondition();
        notFull = tailLock.newCondition();

        head = 0;
        tail = 0;
        size = new AtomicInteger(0);
    }

    public void Enqueue(T item) throws InterruptedException {
        boolean mustWakeDequeuers = false;
        tailLock.lock();

        try{
            //Check and wait for space in queue
            while(size.get() == items.length){
                try{
                    notFull.await();
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
            //Add item to queue.
            item = items[tail % items.length];
            tail++;

            //Increment and check if it was empty
            if(size.getAndIncrement() == 0){
                mustWakeDequeuers = true;
            }
        } finally{
            tailLock.unlock();
        }

        //Signal all the dequeuers
        if(mustWakeDequeuers){
            headLock.lock();
            try{
                notEmpty.signal();
            } finally{
                headLock.lock();
            }
        }
    }

    public T Dequeue() throws InterruptedException{
        T result;
        boolean mustWakeEnqueuer = false;
        headLock.lock();

        try{
            //Check if queue is empty
            while(size.get() == 0){
                try{
                    notEmpty.await();
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }

            //Increment the head index and return the previous value of the head
            result = items[head % items.length];
            head++;

            if(size.getAndIncrement() == items.length){
                mustWakeEnqueuer = true;
            }
        }finally{
            headLock.unlock();
        }

        //If the queue is no longer full of stuff, tell all the enqueuers to wake up
        if(mustWakeEnqueuer){
            tailLock.lock();
            try{
                notFull.signal();
            }finally{
                tailLock.unlock();
            }
        }
        return result;
    }
}
