package org.example;

import lombok.Value;

// Comparable is an interface defining a strategy of comparing
// This is called the "natural ordering"
@Value
public class Player implements Comparable<Player> {
    int ranking;
    String name;
    int age;

    // comparable interface override method is good choice to use for defining the default ordering
    @Override
    public int compareTo(Player otherPlayer) {
        // compare(x. y) returns
        // -1: x is less than y
        // 0: they are equal
        // 1: x is bigger then y
        return Integer.compare(this.getRanking(), otherPlayer.getRanking());
    }
}
