package com.example.que;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;


class Taker implements Runnable {
    SynchronousQueue<Integer> sq;
    
    public Taker (SynchronousQueue<Integer> _sq) {
        super();
        this.sq = _sq;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        
        try {
            System.out.println(sq.take().toString() + " removed from queue");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}

class Putter implements Runnable {

    SynchronousQueue<Integer> sq;
    
    private static AtomicInteger value = new AtomicInteger(1);
    
    public Putter(SynchronousQueue<Integer> _sq) {
        super();
        this.sq = _sq;
    }
    
    @Override
    public void run() {
        
        // TODO Auto-generated method stub
        try {
            Integer i = value.getAndIncrement();
            sq.put(i);
            System.out.println(i.toString() + " placed in queue");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
}

public class Synchronousque {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        SynchronousQueue<Integer> sq = new SynchronousQueue<Integer>();
        
        Taker t = new Taker(sq);
        Putter p = new Putter(sq);
        
        for(int i=0; i<5; i++) {
            new Thread(t).start();
            new Thread(p).start();
        }

    }

}
