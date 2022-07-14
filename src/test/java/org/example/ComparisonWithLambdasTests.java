package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    // TODO: https://www.baeldung.com/java-8-sort-lambda#sort-extracted-comparators
    @Test
    void name() {

    }
}
