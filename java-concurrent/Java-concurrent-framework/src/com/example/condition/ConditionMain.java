package com.example.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class ConditionLockExample {
    private Lock lock = new ReentrantLock();
    private Condition zero = lock.newCondition();
    private Condition max = lock.newCondition();
    private int value = 0;
    private final int MAX = 3;
    private final int MIN = 0;
    
    public void inc() {
        lock.lock();
        try {
            
            while (value >= MAX)
                zero.await();
            
            value++;
            max.signal();
            System.out.println("Value inc to " + value);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    
    public void dec() {
        
        lock.lock();
        
        try {
            
            while (value <= MIN)
                max.await();
            
            value--;
            zero.signal();
            System.out.println("Value dec to " + value);
            
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

class CLEInctor implements Runnable {

    private ConditionLockExample mConditionLock;
    
    public CLEInctor(ConditionLockExample conditionLockExample) {
        this.mConditionLock = conditionLockExample;
    }
    
    
    @Override
    public void run() {
        mConditionLock.inc();
    }
}

class CLEDector implements Runnable {

    private ConditionLockExample mConditionLock;
    
    public CLEDector(ConditionLockExample conditionLockExample) {
        this.mConditionLock = conditionLockExample;
    }
    
    @Override
    public void run() {
        mConditionLock.dec();
    }
}


public class ConditionMain {
    
    

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ConditionLockExample example = new ConditionLockExample();
        
        CLEInctor inctor = new CLEInctor(example);
        CLEDector dector = new CLEDector(example);
        
        for(int i=0; i<5; i++) {
            new Thread(inctor).start();
            new Thread(dector).start();
        }

    }

}
