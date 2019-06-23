package com.example.demo.test;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁实现简单缓存
 */
@Component
public class MyThreadCache {
    Map<String, Object> cacheMap = new HashMap<String, Object>();
    final ReadWriteLock lock = new ReentrantReadWriteLock();
    final Lock readLock = lock.readLock();
    final Lock writeLock = lock.writeLock();

    public Object get(String key) {
        readLock.lock();

        Object value = null;
        try {
            value = cacheMap.get(key);
        } catch (Exception e) {

        } finally {
            readLock.unlock();
        }

        if (value == null) {
            writeLock.lock();
            try {
                //再次验证 可能多个线程进去上面步骤
                value = cacheMap.get(key);
                if (value == null) {
                    System.out.println("正在取数据");
                    Thread.sleep(1000);
                    value = "this data is from database";
                    cacheMap.put(key, value);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
            }


        }

        return value;
    }

    public void put(String key, Object value) {
        writeLock.lock();
        try {
            cacheMap.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }
}
