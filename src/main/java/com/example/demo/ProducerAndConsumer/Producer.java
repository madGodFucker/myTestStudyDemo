package com.example.demo.ProducerAndConsumer;

public class Producer implements Runnable{

    private ProduceOrConsume storage;

    public Producer(ProduceOrConsume storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(100);
                storage.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
