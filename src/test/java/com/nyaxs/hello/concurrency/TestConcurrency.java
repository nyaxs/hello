package com.nyaxs.hello.concurrency;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestConcurrency {
    @Test
    void t1() {

    }


    class MyThreadPoolExecutor{
       private static final int CORE_POOL_SIZE = 5;
       private static final int MAX_POOL_SIZE = 10;
       private static final int  QUEUE_CAPACITY = 100;
       private  final Long KEEP_ALIVE_TIME = 1l;

        public  void main(String[] args) {
            ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
                    TimeUnit.SECONDS, new ArrayBlockingQueue<>(QUEUE_CAPACITY), new ThreadPoolExecutor.CallerRunsPolicy());
            for (int i = 0; i < 10; i++) {
                Runnable work = new MyRunnale("command" + i);
                executor.execute(work);
            }

            executor.shutdown();
            while (!executor.isTerminated()){

            }
            System.out.println("shut down all threads");
        }

    }

    static class MyRunnale implements Runnable{

        private String commamd;
        public MyRunnale(String commamd){
            this.commamd = commamd;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"start..time = " + LocalDateTime.now());
            processCommand();
            System.out.println(Thread.currentThread().getName()+"end..time = " + LocalDateTime.now());

        }

        private void processCommand(){
            try{
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String toString() {
            return this.commamd;
        }
    }

}
