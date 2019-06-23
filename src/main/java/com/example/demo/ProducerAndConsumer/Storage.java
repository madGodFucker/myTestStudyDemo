package com.example.demo.ProducerAndConsumer;

import java.util.LinkedList;

/**
 * wait + notify 实现
 */
public class Storage implements ProduceOrConsume{

    private final int MAX_SIZE = 5;
    private LinkedList<Object> list = new LinkedList<>();

    @Override
    public void produce() {
        synchronized (list) {
            while (list.size() == MAX_SIZE) {
                System.out.println(Thread.currentThread().getName() + ":仓库满了");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            list.add(new Object());
            System.out.println(Thread.currentThread().getName() + "生产了商品" + ",目前库存" + list.size());

            list.notifyAll();
        }
    }

    @Override
    public void consume() {
        synchronized (list) {
            while (list.size() == 0) {
                System.out.println(Thread.currentThread().getName() + "仓库没有货物了");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            list.remove();
            System.out.println(Thread.currentThread().getName() + "消费了商品" + ",目前库存" + list.size());

            list.notifyAll();
        }
    }

    public static void main(String[] args) {
        Storage storage = new Storage();

        new Thread( new Producer(storage)).start();
        new Thread( new Producer(storage)).start();
        new Thread( new Producer(storage)).start();
        new Thread( new Producer(storage)).start();


        new Thread( new Consumer(storage)).start();
        new Thread( new Consumer(storage)).start();
        new Thread( new Consumer(storage)).start();
        new Thread( new Consumer(storage)).start();


    }
}
