package com.ram.produceconsumerusingwaitandnotify;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class ProdConWaitNotify {
    public static void main(String[] args) {
        LinkedList<Integer> buffer=new LinkedList<>();

       new Thread(new Produce(buffer)).start();
       new Thread(new Consumer(buffer)).start();

    }
}

class Produce implements Runnable{

    public LinkedList<Integer> buffer;

    public Random random;



    public Produce(LinkedList<Integer> buffer) {
        this.buffer = buffer;
        this.random=new Random();
    }

    @Override
    public void run() {

        while (true){
            synchronized (this.buffer){

                if(this.buffer.size()==0){
                    int i=random.nextInt(10)+1;
                    this.buffer.add(i);
                    System.out.println("produced data: "+i);
                    this.buffer.notifyAll();
                }else {
                    try {
                        this.buffer.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}

class Consumer implements Runnable{

    public LinkedList<Integer> buffer;

    public Consumer(LinkedList<Integer> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        while (true){
            synchronized (this.buffer){

                if(this.buffer.size()>0){
                    int data=this.buffer.remove();
                    System.out.println("consumed data: "+data);
                    this.buffer.notifyAll();
                }else {
                    try {
                        this.buffer.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
