package com.example.executor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

class ThreadFactoryExample implements ThreadFactory {

    private static int id = 0;
    
    public Thread newThread(Runnable r, String name, int priority) {
        
        Thread thread = new Thread(r);
        
        if (priority > Thread.MAX_PRIORITY)
            priority = Thread.MAX_PRIORITY;
        else if (priority < Thread.MIN_PRIORITY)
            priority = Thread.MIN_PRIORITY;
        
        thread.setPriority(priority);
        thread.setName(name);
        
        return thread;
    }
    
    @Override
    public Thread newThread(Runnable r) {
        return this.newThread(r, "Task " + id++, Thread.NORM_PRIORITY);
    }
    
}

class ExecutorExample implements Executor {
    
    private static ArrayBlockingQueue<Thread> threads = new ArrayBlockingQueue<Thread>(2);
    private static int id = 0;

    public void execute(Runnable r, String name, int priority) {
        
        ThreadFactoryExample tFac = new ThreadFactoryExample();
        Thread t = tFac.newThread(r, name, priority);
        
        if(threads.offer(t))
            System.out.println("Thread " + t.getName() + " added to execution que");
        
        try {
            t = threads.take();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("Thread " + t.getName() + " started with priority " + t.getPriority());
        t.start();
        
    }
    
    @Override
    public void execute(Runnable command) {
        // TODO Auto-generated method stub
        this.execute(command, "Task " + id++, Thread.NORM_PRIORITY);
    }
}

class Etor implements Runnable {
    
    private ExecutorExample ex;
    
    public Etor(ExecutorExample _ex) {
        this.ex = _ex;
    }

    @Override
    public void run() {
        ex.execute(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                System.out.println(Thread.currentThread().getName() + " run");
                
            }
        });
    }
    
}

public class ExecutorExampleMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ExecutorExample ex = new ExecutorExample();
        Etor tor = new Etor(ex);
        
//        for (int i=0; i<5; i++) {
//            new Thread(tor).start();
//        }
        
        ex.execute(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                System.out.println(Thread.currentThread().getName() + " run");
                
            }
        });
        
        ex.execute(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                System.out.println(Thread.currentThread().getName() + " run");
                
            }
        });
        
        ex.execute(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                System.out.println(Thread.currentThread().getName() + " run");
                
            }
        });
    }

}
