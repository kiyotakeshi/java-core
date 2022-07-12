package org.example;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

class TreeMapTests {

    @Test
    void orderByNaturalOrdering() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(3, "val");
        map.put(2, "val");
        map.put(5, "val");
        map.put(1, "val");
        map.put(4, "val");

        assertThat(map.keySet().toString()).isEqualTo("[1, 2, 3, 4, 5]");
    }

    @Test
    void orderByNaturalOrdering2() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", "val");
        map.put("c", "val");
        map.put("b", "val");
        map.put("d", "val");
        map.put("e", "val");

        assertThat(map.keySet().toString()).isEqualTo("[a, b, c, d, e]");
    }

    @Test
    void comparator() {
        TreeMap<Integer, String> map = new TreeMap<>(Comparator.reverseOrder());
        map.put(3, "val");
        map.put(2, "val");
        map.put(5, "val");
        map.put(1, "val");
        map.put(4, "val");

        assertThat(map.keySet().toString()).isEqualTo("[5, 4, 3, 2, 1]");
    }

    @Test
    void queries() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(3, "val");
        map.put(2, "val");
        map.put(5, "val");
        map.put(1, "val");
        map.put(4, "val");

        Integer highestKey = map.lastKey();
        Integer lowestKey = map.firstKey();
        assertThat(map.keySet().toString()).isEqualTo("[1, 2, 3, 4, 5]");
        assertThat(highestKey).isEqualTo(5);
        assertThat(lowestKey).isEqualTo(1);
    }
}
