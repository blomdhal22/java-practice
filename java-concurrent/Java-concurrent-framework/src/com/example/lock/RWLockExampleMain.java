package com.example.lock;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class RWLockExample {
    
    private int value = 0;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    
    public int read() {
        
        try {
            lock.readLock().lock();
            System.out.println("Read Lock: " + lock.getReadLockCount());
            return value;
        } finally {
            System.out.println("Read lock released.");
            lock.readLock().unlock();
        }
    }
    
    public int writeNewValue(int newVal) {
        
        try {
            lock.writeLock().lock();
            System.out.println("Write Lock: " + lock.getWriteHoldCount());
            value = newVal;
            return value;
        } finally {
            System.out.println("Write lock leased");
            lock.writeLock().unlock();
            
        }
    }
}

class RWLockWriter implements Runnable {
    
    private RWLockExample ex;
    
    public RWLockWriter(RWLockExample _ex) {
        
        this.ex = _ex;
        
    }
    
    public void run() {
        System.out.println("Value wrote is "
                + ex.writeNewValue(new Random().nextInt(100)));
    }
}

class RWLockReader implements Runnable {
    
    private RWLockExample ex;
    
    public RWLockReader(RWLockExample _ex) {
        this.ex = _ex;
    }
    
    public void run() {
        System.out.println("Value read is " + ex.read());
    }
}

public class RWLockExampleMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        RWLockExample ex = new RWLockExample();
        
        RWLockWriter writer = new RWLockWriter(ex);
        RWLockReader reader = new RWLockReader(ex);
        
        for (int i=0; i<5; i++) {
            new Thread(writer).start();
            new Thread(reader).start();
        }
    }

}
