package com.example.demo.ProducerAndConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 利用阻塞队列
 */
public class Storage3 implements ProduceOrConsume {
    private final int MAX_SIZE = 5;
    BlockingQueue queue = new LinkedBlockingQueue(5);
    public static boolean flag = true;

    public BlockingQueue getQueue() {
        return queue;
    }

    public void setQueue(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void produce() {
        try {
            queue.put(new Object());
            System.out.println(Thread.currentThread().getName() + ":生产了一个产品");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void consume() {
        try {
            queue.take();
            System.out.println(Thread.currentThread().getName() + ":消费了一个产品");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Storage3 storage = new Storage3();

        new Thread(new Producer(storage)).start();
        new Thread(new Producer(storage)).start();

        new Thread(() -> {
            List list = new ArrayList();
            while (Storage3.flag) {
                if (storage.getQueue().size() == 5) {
                    storage.getQueue().drainTo(list);
                    list.forEach(o -> {
                        System.out.println(o.hashCode());
                    });
                    list = new ArrayList();
                }
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(3000);
                Storage3.flag = false;
                System.out.println("停止处理数据");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();


//        new Thread( new Producer(storage)).start();
//        new Thread( new Producer(storage)).start();


//        new Thread( new Consumer(storage)).start();
//        new Thread( new Consumer(storage)).start();
//        new Thread( new Consumer(storage)).start();
//        new Thread( new Consumer(storage)).start();
    }
}
