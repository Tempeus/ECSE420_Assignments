package ca.mcgill.ecse420.a3;

import java.util.concurrent.atomic.AtomicInteger;

public class BoundedLockFreeQueue<T> {
    private T[] items;

    private AtomicInteger head;
    private AtomicInteger tail;
    private AtomicInteger capacity;
    private AtomicInteger tailcommit;

    public BoundedLockFreeQueue(int capacity){
        //initialization
        items = (T[]) new Object[capacity];
        this.capacity = new AtomicInteger(capacity);
    }

    public void Enqueue(T item){

    }

    public T Dequeue(){
        int i = this.head.getAndIncrement();
        while(i >= this.tailcommit.get()) {

        };

        T item = items[i % this.items.length];
        capacity.incrementAndGet();

        return item;
    }
}
