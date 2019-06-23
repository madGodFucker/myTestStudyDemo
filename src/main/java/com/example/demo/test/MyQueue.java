package com.example.demo.test;

import java.util.Arrays;
import java.util.List;

/**
 * 简单队列模型
 */
public class MyQueue<E> {
    private int currentSize;
    Object[] array;

    private int front = 1;
    private int back;
    private int len;

    public MyQueue(int size) {
        this.array = new Object[size];
        this.len = size;
    }

    public E enqueue(E o) {

        E p = null;
        if (currentSize == array.length) {
            p = dequeue();
        }

        if (len - 1 == back) {
            back = 0;
        } else {
            back++;
        }
        currentSize++;

        array[back] = o;
        System.out.println(o + ":加入了队列");

        return p;
    }

    public E dequeue() {
        if (currentSize == 0) {
            System.out.println("无人排队");
            return null;
        }

        currentSize--;
        Object o = array[front];
        System.out.println(o + ":退出了队列");
        if (len - 1 == front) {
            front = 0;
        } else {
            front++;
        }
        return (E) o;
    }

    public static void main(String[] args) {

        MyQueue myQueue = new MyQueue<String>(5);

        List<String> list = Arrays.asList("aaaa", "bbbbb", "cccccc", "dddddd", "eeee", "fffffff", "gggggggg");

        list.forEach(o -> {
            myQueue.enqueue(o);
        });

        for (int i = 0; i < 8; i++) {
            myQueue.dequeue();
        }

    }

}
