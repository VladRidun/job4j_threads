package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CasCountTest {

    @Test
    void whenExecute2ThreadThen10() throws InterruptedException {
        var count = new CASCount();
        var first = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                count.increment();
            }
        });
        var second = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                count.increment();
            }
        });
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.get()).isEqualTo(10);
    }
}