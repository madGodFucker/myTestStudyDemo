package com.example.demo.controller;

import com.example.demo.test.MyThreadCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @Autowired
    private MyThreadCache myThreadCache;

    /**
     * 如何让 3个 线程 依次执行  ：使用join
     *
     * @return
     */
    @RequestMapping("/test")
    public String test() {

        Thread t1 = new Thread(() -> {
            System.out.println("111111111111111");
        });

        Thread t2 = new Thread(() -> {
            try {
                t1.start();
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("222222222222");
        });

        Thread t3 = new Thread(() -> {
            try {
                t2.start();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("3333333333333");
        });

        t3.start();

        return "hello idea spring boot project";
    }

    /**
     * lock() 获取锁 未获取到则一直等待
     * tryLock() 尝试获取锁 获取到则返回true  可添加时间参数 尝试获取一段时间
     * lockInterruptibly() 若处于等待状态 则可 通过interrupt 打断
     * <p>
     * ReentrantLock
     * ReentrantReadWriteLock
     */
    @RequestMapping("/lock")
    public void lockTest() {
        System.out.println(myThreadCache.hashCode());
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                System.out.println(myThreadCache.get("data"));
            }).start();
        }

    }

}
