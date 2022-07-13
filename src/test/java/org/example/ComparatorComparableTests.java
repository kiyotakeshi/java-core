package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ComparatorComparableTests {

    List<Player> footballTeam;

    @BeforeEach
    void setUp() {
        footballTeam = Arrays.asList(
                new Player(33, "mike", 22),
                new Player(11, "popcorn", 29),
                new Player(28, "kendrick", 31)
        );
    }

    @Test
    void comparable() {
        Collections.sort(footballTeam);
        // System.out.println(footballTeam);
        // [Player(ranking=11, name=popcorn, age=29), Player(ranking=28, name=kendrick, age=31), Player(ranking=33, name=mike, age=22)]
        assertThat(footballTeam.get(0).getName()).isEqualTo("popcorn");
    }

    @Test
    void comparator() {
        var playerComparator = new PlayerRankingComparator();
        Collections.sort(footballTeam, playerComparator);
        // System.out.println(footballTeam);
        // [Player(ranking=11, name=popcorn, age=29), Player(ranking=28, name=kendrick, age=31), Player(ranking=33, name=mike, age=22)]
        assertThat(footballTeam.get(0).getName()).isEqualTo("popcorn");
    }

    @Test
    void comparator2() {
        var playerComparator = new PlayerAgeComparator();
        Collections.sort(footballTeam, playerComparator);
        // System.out.println(footballTeam);
        // [Player(ranking=33, name=mike, age=22), Player(ranking=11, name=popcorn, age=29), Player(ranking=28, name=kendrick, age=31)]
        assertThat(footballTeam.get(0).getName()).isEqualTo("mike");
    }

    @Test
    void comparatorLambda() {
        Comparator<Player> byRanking = (Player player1, Player player2) -> Integer.compare(player1.getRanking(), player2.getRanking());
        // Comparator<Player> byRanking = Comparator.comparingInt(Player::getRanking);
        // Comparator<Player> byRanking = Comparator.comparing(Player::getRanking);

        // Collections.sort(footballTeam, byRanking);
        footballTeam.sort(byRanking);
        assertThat(footballTeam.get(0).getName()).isEqualTo("popcorn");
    }
}
