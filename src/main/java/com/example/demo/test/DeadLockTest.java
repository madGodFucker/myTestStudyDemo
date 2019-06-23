package com.example.demo.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockTest {
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(() -> {
            lock1.lock();
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName()+":执行完毕 准备获取资源2");
                lock2.lock();
                try {
                    Thread.sleep(2000);
                }finally {
                    lock2.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock1.unlock();
            }
        }).start();

        new Thread(() -> {
            lock2.lock();
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName()+":执行完毕 准备获取资源1");
                lock1.lock();
                try {
                    Thread.sleep(2000);
                }finally {
                    lock1.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock2.unlock();
            }
        }).start();

        System.out.println("main");
    }
}
