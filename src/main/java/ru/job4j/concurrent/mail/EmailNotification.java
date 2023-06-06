package ru.job4j.concurrent.mail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private ExecutorService pool;

    public void emailTo(User user) {
        pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors());
        String subject = String.format("Notification {%d} to email {%d}", user.getUsername(), user.getEmail());
        String body = String.format("Add a new event to {%d}", user.getUsername());
        pool.submit(() -> send(subject, body, user.getEmail()));
        close();
    }

    public void close() {
        pool.shutdown();
    }

    public void send(String subject, String body, String email) {

    }
}
