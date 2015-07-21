package com.example.future;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class Calculation implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        return new Random().nextInt(1000);
    }
    
}

class FutureExample {
    
    public void test() {
        ExecutorService executor = Executors.newFixedThreadPool(100);
        List<Future<Integer>> values = new LinkedList<Future<Integer>>();
        
        for(int i=0; i<100; i++) {
            Callable<Integer> value = new Calculation();
            Future<Integer> task = executor.submit(value);
            values.add(task);
        }
        
        Float mean = (float)0;
        for(Future<Integer> result : values) {
            
            try {
                mean += (float)result.get() / 100;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("The mean of 100 random integer is " + mean);
    }
}

public class FutureExampleMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new FutureExample().test();
        System.out.println("end");
    }

}
