package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

// Comparable is an interface defining a strategy of comparing
// This is called the "natural ordering"
@Value
public class Player implements Comparable<Player> {
    int ranking;
    String name;
    int age;

    @Override
    public int compareTo(Player otherPlayer) {
        // compare(x. y) returns
        // -1: x is less than y
        // 0: they are equal
        // 1: x is bigger then y
        return Integer.compare(this.getRanking(), otherPlayer.getRanking());
    }
}
