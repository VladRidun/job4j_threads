package ru.job4j.concurrent;

import org.apache.commons.validator.routines.UrlValidator;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private static final Long TIME_POINT = 1000L;
    private final String url;
    private final int speed;
    private final String out;

    public Wget(String url, int speed, String out) {
        this.url = url;
        this.speed = speed;
        this.out = out;
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
             FileOutputStream fileOutputStream = new FileOutputStream(out)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int countRead = 0;
            long start = System.currentTimeMillis();
            long timeSleep = 0;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                countRead += bytesRead;
                if (countRead >= speed) {
                    var end = System.currentTimeMillis();
                    var timeElapsed = start - end;
                    if (timeElapsed < TIME_POINT) {
                        timeSleep = TIME_POINT - timeElapsed;
                    }
                    Thread.sleep(timeSleep);
                    countRead = 0;
                    start = System.currentTimeMillis();
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
        if (args.length != 3) {
            throw new IllegalArgumentException("Use 3 arguments: URL of downloading file, downloading speed, output file name");
        }
        if (!isValidURL(args[0])) {
            throw new IllegalArgumentException("URL is null or incorrectly specified");
        }
        if (!isNumeric(args[1])) {
            throw new IllegalArgumentException("Speed id incorrectly specified");
        }
        if ((args[2]).isEmpty()) {
            throw new IllegalArgumentException("output file name is null or incorrectly specified");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String out = args[2];
        Thread wget = new Thread(new Wget(url, speed, out));
        wget.start();
        wget.join();
    }
}