package com.example.demo.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理模式  可在执行方法前后 添加自己的东西
 */
public class MyProxyTest {

    interface Produce {
        void producePanci();

        void produceCloth();

        void produceNetBook();
    }

    static class MyFactory implements Produce {

        @Override
        public void producePanci() {
            System.out.println("内裤");
        }

        @Override
        public void produceCloth() {
            System.out.println("衣服");
        }

        @Override
        public void produceNetBook() {
            System.out.println("笔记本");
        }
    }

    static class ProxyForFactory implements InvocationHandler {

        private Produce produce;

        public ProxyForFactory(Produce produce) {
            this.produce = produce;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.print("销售");
            return method.invoke(produce, args);
        }
    }

    public static void main(String[] args) {
        Produce produce = new MyFactory();

        Produce proxyProduce = (Produce) Proxy.newProxyInstance(produce.getClass().getClassLoader(), produce.getClass().getInterfaces(), new ProxyForFactory(produce));

        proxyProduce.produceCloth();
        proxyProduce.producePanci();
        proxyProduce.produceNetBook();
    }

}
