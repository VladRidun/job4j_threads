package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Integer> filter) {
        String output = "";
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buffer = new byte[256];
            int data;
            while ((data = i.read(buffer, 0, 256)) != -1) {
                if (filter.test(data)) {
                    output += (char) data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}