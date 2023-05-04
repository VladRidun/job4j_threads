package ru.job4j.concurrent;

import org.apache.commons.validator.routines.UrlValidator;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final Long time = 1000L;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    public String getUrl() {
        return url;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(getUrl()).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int countRead = 0;
            long start = System.currentTimeMillis();
            long timeSleep = 0;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                countRead += bytesRead;
                if (countRead > speed * time) {
                    long end = System.currentTimeMillis();
                    long timeElapsed = start - end;
                    if (timeElapsed < time) {
                        timeSleep = time - timeElapsed;
                    }
                    Thread.sleep(timeSleep);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    static boolean isValidURL(String url) {
        UrlValidator validator = new UrlValidator();
        return validator.isValid(url);
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void validate(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Use two arguments: URL of downloading file, downloading speed");
        }
        if (!isValidURL(args[0])) {
            throw new IllegalArgumentException("URL is null or incorrectly specified");
        }
        if (!isNumeric(args[1])) {
            throw new IllegalArgumentException("Speed id incorrectly specified");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}