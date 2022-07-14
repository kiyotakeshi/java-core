package org.example;

import lombok.Value;

@Value
public class Human {
    String name;
    int age;

    public static int compareByNameThenAge(final Human h1, final Human h2) {
        if (h1.name.equals(h2.name)) {
            return Integer.compare(h1.age, h2.age);
        } else {
            return h1.name.compareTo(h2.name);
        }
    }
}
