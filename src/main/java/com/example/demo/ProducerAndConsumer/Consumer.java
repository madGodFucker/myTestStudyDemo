package com.example.demo.ProducerAndConsumer;

public class Consumer implements Runnable{
    private ProduceOrConsume storage;

    public Consumer(ProduceOrConsume storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(1000);
                storage.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
