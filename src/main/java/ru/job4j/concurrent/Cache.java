package ru.job4j.concurrent;

public final class Cache {
    private static Cache cache;

    public Cache instOf() {
        synchronized (this) {
            if (cache == null) {
                cache = new Cache();
            }
            return cache;
        }
    }
}