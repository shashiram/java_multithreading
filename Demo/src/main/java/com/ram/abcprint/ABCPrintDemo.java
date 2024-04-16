package com.ram.abcprint;

import java.util.concurrent.TimeUnit;

public class ABCPrintDemo {

    public static void main(String[] args) {

        NumCounter numCounter=new NumCounter(1);

        Thread a_th=new Thread(new AThread(numCounter),"thread_A");
        Thread b_th=new Thread(new BThread(numCounter),"thread_B");
        Thread c_th=new Thread(new CThread(numCounter),"thread_C");

        a_th.start();
        b_th.start();
        c_th.start();


    }
}

class NumCounter{
    public volatile int cnt;

    public NumCounter(int cnt) {
        this.cnt = cnt;
    }

    public void print(Character character){

        System.out.println(Thread.currentThread().getName()+" : "+character);

        this.cnt++;
    }
}


class AThread implements Runnable{
    NumCounter numCounter;
    ThreadLocal<Integer> threadLocal=new ThreadLocal<>();

    public AThread(NumCounter numCounter) {
        this.numCounter = numCounter;

    }

    @Override
    public void run() {

        while (true) {
            synchronized (this.numCounter){

                if(threadLocal.get()==null){
                    threadLocal.set(1);
                }


                if( threadLocal.get()>5){
                    return;
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (this.numCounter.cnt % 3 == 1){
                    this.numCounter.print('A');
                    this.threadLocal.set(this.threadLocal.get()+1);
                    this.numCounter.notifyAll();
                }
                else {
                    try {
                        this.numCounter.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
         }
        }
    }
}

class BThread implements Runnable{
    NumCounter numCounter;
    ThreadLocal<Integer> threadLocal=new ThreadLocal<>();

    public BThread(NumCounter numCounter) {
        this.numCounter = numCounter;
        threadLocal.set(1);
    }

    @Override
    public void run() {

        while (true) {
            synchronized (this.numCounter){

                if(threadLocal.get()==null){
                    threadLocal.set(1);
                }

                if( threadLocal.get()>5){
                    return;
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (this.numCounter.cnt % 3 == 2){
                    this.numCounter.print('B');
                    this.threadLocal.set(this.threadLocal.get()+1);
                    this.numCounter.notifyAll();
                }
                else {
                    try {
                        this.numCounter.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}

class CThread implements Runnable{
    NumCounter numCounter;
    ThreadLocal<Integer> threadLocal=new ThreadLocal<>();

    public CThread(NumCounter numCounter) {
        this.numCounter = numCounter;
        threadLocal.set(1);
    }

    @Override
    public void run() {

        while (true) {
            synchronized (this.numCounter){

                if(threadLocal.get()==null){
                    threadLocal.set(1);
                }

                if( threadLocal.get()>5){
                    return;
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (this.numCounter.cnt % 3 == 0){
                    this.numCounter.print('C');
                    this.threadLocal.set(this.threadLocal.get()+1);
                    this.numCounter.notifyAll();
                }
                else {
                    try {
                        this.numCounter.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}



