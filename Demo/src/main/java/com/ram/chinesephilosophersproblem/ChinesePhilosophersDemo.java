package com.ram.chinesephilosophersproblem;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChinesePhilosophersDemo {

    public static void main(String[] args) {

        ChinesePhilosophersProblem problem = new ChinesePhilosophersProblem();
        new Thread(problem::eating, "0").start();
        new Thread(problem::eating, "1").start();
        new Thread(problem::eating, "2").start();
        new Thread(problem::eating, "3").start();
        new Thread(problem::eating, "4").start();
    }


}

class ChinesePhilosophersProblem {
    int n = 5;
    Lock[] lockObj = new Lock[n];
    Condition[] conditions = new Condition[n];

    public ChinesePhilosophersProblem() {
        for (int i = 0; i < n; i++) {
            Lock lock = new ReentrantLock(true);
            lockObj[i] = lock;
            conditions[i] = lock.newCondition();
        }
    }

    public void eating() {

        while (true) {
            lockObj[Integer.valueOf(Thread.currentThread().getName()) % n].lock();
            lockObj[(Integer.valueOf(Thread.currentThread().getName()) + 1) % n].lock();
            System.out.println("Philosopher " +(Integer.valueOf(Thread.currentThread().getName())+1) + " is eating now ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            conditions[Integer.valueOf(Thread.currentThread().getName()) % n].signalAll();
            conditions[(Integer.valueOf(Thread.currentThread().getName()) + 1) % n].signalAll();

            lockObj[Integer.valueOf(Thread.currentThread().getName()) % n].unlock();
            lockObj[(Integer.valueOf(Thread.currentThread().getName()) + 1) % n].unlock();
        }
    }
}