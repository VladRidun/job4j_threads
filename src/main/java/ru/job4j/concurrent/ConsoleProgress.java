package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        var process = new char[]{'-', '\\',  '|',  '/'};
        while (!Thread.currentThread().isInterrupted()) {
            for (int i = 0; i <= process.length - 1; i++) {
                try {
                    Thread.sleep(500);
                    System.out.print("\r Loading: " + process[i]);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}
