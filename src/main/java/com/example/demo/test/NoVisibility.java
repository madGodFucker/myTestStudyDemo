package com.example.demo.test;

/**
 * volatile 可见性 测试
 */
public class NoVisibility {
    private static boolean ready;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (true) {
                if (ready) {
                    System.out.println("!=");
                    System.exit(0);
                }
            }
        }).start();
 
        Thread.sleep(100);

        new Thread(){
            @Override
            public void run() {
                ready = true;
            }
        }.start();

    }

}
