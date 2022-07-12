package org.example;

import org.junit.jupiter.api.Test;

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

    // TODO: https://www.baeldung.com/java-treemap#custom-sorting-in-treemap
    @Test
    void name() {

    }
}
