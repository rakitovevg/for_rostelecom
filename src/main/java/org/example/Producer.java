package org.example;

import com.google.common.util.concurrent.RateLimiter;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final RateLimiter rateLimiter;

    public Producer(BlockingQueue<Integer> queue, double rate) {
        this.queue = queue;
        this.rateLimiter = RateLimiter.create(rate);
    }

    @Override
    public void run() {
        try {
            int value = 0;
            while (true) {
                rateLimiter.acquire();  // Замедление в соответствии с rateLimiter
                queue.put(value);
                System.out.println("Producer produced: " + value);
                value++;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}