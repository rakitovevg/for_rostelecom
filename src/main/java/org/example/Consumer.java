package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final long delayMillis;

    public Consumer(BlockingQueue<Integer> queue, long delayMillis) {
        this.queue = queue;
        this.delayMillis = delayMillis;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Integer value = queue.take();
                System.out.println("Consumer consumed: " + value);
                TimeUnit.MILLISECONDS.sleep(delayMillis);  // Задержка потребления
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}