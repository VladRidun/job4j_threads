package ru.job4j.concurrent.pool;

import ru.job4j.concurrent.ConsoleProgress;
import ru.job4j.concurrent.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);


    public ThreadPool() {
        for (int i = 0; i <= size; i++) {
            threads.add(new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }));
        }
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        Runnable task = () -> System.out.println("Job is done");
        threadPool.work(task);
        threadPool.work(task);
        threadPool.work(task);
        threadPool.work(task);
        threadPool.work(task);
        threadPool.work(task);
        threadPool.shutdown();
    }
}