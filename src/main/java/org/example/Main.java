package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        Properties properties = getProperties("config.properties");

        double rateLimit = Double.parseDouble((String) properties.get("rateLimit"));
        long delay = Long.parseLong((String) properties.get("delay"));

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(100);

        Producer producer = new Producer(queue, rateLimit);  // 1 операция в секунду
        Consumer consumer = new Consumer(queue, delay);  // Задержка 2 секунды

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(producer);
        executor.submit(consumer);
    }

    private static Properties getProperties(String fileName) {
        Properties properties = new Properties();

        try (InputStream input = Main.class.getClassLoader().getResourceAsStream(fileName)) {
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return properties;
    }
}