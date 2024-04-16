package com.ram.oddeven;

import java.util.concurrent.TimeUnit;

public class OddEvenDemo {
    public static void main(String[] args) {

        PrintNum printNum=new PrintNum(1);

        Thread odd=new Thread(new OddThread(printNum), "odd thread");
        Thread even=new Thread(new EvenThread(printNum), "even thread");

        odd.start();
        even.start();

    }
}

class OddThread implements Runnable{

    PrintNum printNum;

    public OddThread(PrintNum printNum) {
        this.printNum = printNum;
    }

    @Override
    public void run() {
        while (true){
            synchronized (this.printNum){
                if (this.printNum.curNum>6){
                    return;
                }

                //if(this.printNum.curNum>10){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    if (this.printNum.curNum%2!=0){
                        this.printNum.printNum();
                        this.printNum.notify();
                    }else {
                        try {
                            this.printNum.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

            }


        }
    }
}

class EvenThread implements Runnable{

    PrintNum printNum;

    public EvenThread(PrintNum printNum) {
        this.printNum = printNum;
    }

    @Override
    public void run() {
        while (true){
            synchronized (this.printNum){

                if (this.printNum.curNum>6){
                    return;
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (this.printNum.curNum%2==0){
                    this.printNum.printNum();
                    this.printNum.notify();
                }else {
                    try {
                        this.printNum.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

    }
}

class PrintNum{
    public volatile int curNum;

    public PrintNum(int curNum) {
        this.curNum = curNum;
    }

    public void printNum(){
        System.out.println(Thread.currentThread().getName() +" : "+this.curNum);
        this.curNum++;
    }
}
