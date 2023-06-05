package ru.job4j.concurrent.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class CacheTest {

    @Test
    void whenAdd() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 100);
        Base base2 = new Base(2, 200);
        assertThat(cache.add(base1)).isTrue();
        assertThat(cache.add(base1)).isFalse();
        assertThat(cache.add(base2)).isTrue();
        assertThat(cache.getMemory()).isEqualTo(Map.of(1, base1, 2, base2));
    }

    @Test
    void whenUpdate() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 100);
        Base base2 = new Base(2, 200);
        Base base3 = new Base(1, 100);
        cache.add(base1);
        cache.add(base2);
        assertThat(cache.update(base3)).isTrue();
        assertThat(cache.update(new Base(5, 400))).isFalse();
        assertThat(cache.getMemory().get(base1.getId()).getVersion()).isEqualTo(101);
    }

    @Test
    void whenUpdateCheckName() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 100);
        Base base3 = new Base(1, 100);
        base1.setName("qwerty");
        cache.add(base1);
        cache.update(base3);
        assertThat(cache.getMemory().get(base1.getId()).getName()).isEqualTo("qwerty");
    }

    @Test
    void whenDelete() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 100);
        Base base2 = new Base(2, 200);
        Base base3 = base2;
        cache.add(base1);
        cache.add(base2);
        base2 = new Base(2, 205);
        cache.delete(base1);
        cache.delete(base2);
        assertThat(cache.getMemory()).containsEntry(2, base3);
    }

    @Test
    void whenUpdateThenException() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 100);
        cache.add(base1);
        assertThatThrownBy(() -> cache.update(new Base(1, 110)))
                .isInstanceOf(OptimisticException.class)
                .message()
                .isNotEmpty();
    }
}