package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ComparatorComparableTests {

    @Test
    void comparable() {
        List<Player> footballTeam = Arrays.asList(
                new Player(33, "mike", 22),
                new Player(11, "popcorn", 29),
                new Player(28, "kendrick", 31)
        );

        Collections.sort(footballTeam);
        assertThat(footballTeam.get(0).getName()).isEqualTo("popcorn");
    }

    // TODO: https://www.baeldung.com/java-comparator-comparable#comparator
    @Test
    void comparator() {
    }
}
