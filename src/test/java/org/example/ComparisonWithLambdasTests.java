package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ComparisonWithLambdasTests {

    List<Human> humans;


    @BeforeEach
    void setUp() {
        humans = Arrays.asList(
                new Human("mike", 20),
                new Human("popcorn", 30),
                new Human("amber", 30),
                new Human("amber", 20)
        );
    }

    @Test
    void anonymousInnerClass() {
        Collections.sort(humans, new Comparator<Human>() {
            @Override
            public int compare(Human h1, Human h2) {
                return h1.getName().compareTo(h2.getName());
            }
        });

        assertThat(humans.get(0).getName()).isEqualTo("amber");
    }

    @Test
    void lambda() {
        // humans.sort(Comparator.comparing(Human::getName));
        humans.sort((Human h1, Human h2) -> h1.getName().compareTo(h2.getName()));
        assertThat(humans.get(0).getName()).isEqualTo("amber");
    }

    @Test
    void staticMethodAsComparator() {
        humans.sort(Human::compareByNameThenAge);
        assertThat(humans.get(0).getName()).isEqualTo("amber");
        assertThat(humans.get(0).getAge()).isEqualTo(20);
    }

    @Test
    void lambadaMultipleConditions() {
        humans.sort((h1, h2) -> {
            if (h1.getName().equals(h2.getName())) {
                return Integer.compare(h1.getAge(), h2.getAge());
            } else {
                return h1.getName().compareTo(h2.getName());
            }
        });
        assertThat(humans.get(0).getName()).isEqualTo("amber");
        assertThat(humans.get(0).getAge()).isEqualTo(20);
    }

    @Test
    void multipleComparator() {
        humans.sort(Comparator.comparing(Human::getName).thenComparing(Human::getAge));
        assertThat(humans.get(0).getName()).isEqualTo("amber");
        assertThat(humans.get(0).getAge()).isEqualTo(20);
    }

    @Test
    void sortedMethodNeedsElementClassImplementComparableInterface() {
        assertThatThrownBy(() -> humans.stream().sorted().collect(Collectors.toList()))
                .isInstanceOf(ClassCastException.class)
                .hasMessageContaining("class org.example.Human cannot be cast to class java.lang.Comparable (org.example.Human is in unnamed module of loader 'app'; java.lang.Comparable is in module java.base of loader 'bootstrap')");
    }

    @Test
    void sortWithCustomComparator() {
        // Comparator<Human> nameComparator = Comparator.comparing(Human::getName);
        Comparator<Human> nameComparator = (h1, h2) -> h1.getName().compareTo(h2.getName());
        List<Human> sortedHumans = humans.stream().sorted(nameComparator).collect(Collectors.toList());
        assertThat(sortedHumans.get(0).getName()).isEqualTo("amber");
        assertThat(sortedHumans.get(0).getAge()).isEqualTo(30);
    }
}
