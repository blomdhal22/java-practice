package com.example.barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class BarrierExample {
    
    public void test() {
        int id = 1;
        
        // NOTE : parties
        final CyclicBarrier cb = new CyclicBarrier(6, new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                System.out.println("Barrier released.");
                
            }
        });
        
        for (int i=0; i<6; i++)
            new Thread(new BarrierTask(cb, id++)).start();
    }
}

class BarrierTask implements Runnable {
    final CyclicBarrier barrier;
    final int id;
    
    public BarrierTask(CyclicBarrier b, int _id) {
        // TODO Auto-generated constructor stub
        barrier = b;
        this.id = _id;
    }
    
    public void run() {
        try {
            System.out.println("Task " + id + " waiting at barrier.");
            barrier.await();
            System.out.println("Task " + id + " released by barrier.");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

public class BarrierExampleMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        BarrierExample example = new BarrierExample();
        example.test();
    }

}
