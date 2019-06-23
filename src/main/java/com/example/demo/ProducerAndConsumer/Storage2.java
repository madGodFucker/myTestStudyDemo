package com.example.demo.ProducerAndConsumer;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 利用lock + condition 解决 消费者 生产者问题
 */
public class Storage2 implements ProduceOrConsume {
    private final int MAX_SIZE = 5;
    LinkedList<Object> list = new LinkedList<>();
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    @Override
    public void produce(){
        lock.lock();
        try {
            while (list.size() == MAX_SIZE){
                try {
                    System.out.println(Thread.currentThread().getName()+":正在等待消费");
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            list.add(new Object());
            System.out.println(Thread.currentThread().getName()+":生产了一个产品");
            condition.signalAll();

        }finally {
            lock.unlock();
        }

    }

    @Override
    public void consume(){
        lock.lock();
        try {
            while (list.size() == 0){
                try {
                    System.out.println(Thread.currentThread().getName()+":正在等待生产");
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            list.remove();
            System.out.println(Thread.currentThread().getName()+":已经消费一个产品");
            condition.signalAll();

        }finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        Storage2 storage2 = new Storage2();

        new Thread(new Producer(storage2)).start();
        new Thread(new Producer(storage2)).start();
        new Thread(new Producer(storage2)).start();
        new Thread(new Producer(storage2)).start();

        new Thread(new Consumer(storage2)).start();
        new Thread(new Consumer(storage2)).start();
        new Thread(new Consumer(storage2)).start();
        new Thread(new Consumer(storage2)).start();

    }

}
