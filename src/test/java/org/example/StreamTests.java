package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;

class StreamTests {

    private int counter;

    private List<Player> footballTeam;

    private Map<String, String> books;

    private void wasCalled() {
        counter++;
    }

    @BeforeEach
    void setUp() {
        footballTeam = Arrays.asList(
                new Player(33, "mike", 22),
                new Player(11, "popcorn", 29),
                new Player(28, "kendrick", 31),
                new Player(30, "kanye", 22)
        );

        books = new HashMap<>();
        books.put("978-0201633610", "Design patterns");
        books.put("978-1617291999", "Java 8 in Action");
        books.put("978-0134685991", "Effective Java");
    }

    @Test
    void generate() {
        Stream<String> generate = Stream.generate(() -> "element").limit(5);
        generate.forEach(System.out::println);
    }

    @Test
    void iterate() {
        List<Integer> collect = Stream.iterate(10, n -> n * 2)
                .limit(5)
                .collect(Collectors.toList());

        assertThat(collect.get(0)).isEqualTo(10);
        assertThat(collect.get(1)).isEqualTo(20);
        assertThat(collect.get(2)).isEqualTo(40);
    }

    @Test
    void stringStream() {
        List<String> collect = Pattern.compile(", ")
                .splitAsStream("a, b, c")
                .collect(Collectors.toList());

        assertThat(collect.get(0)).isEqualTo("a");
        assertThat(collect.get(1)).isEqualTo("b");
    }

    @Test
    void streamsCannotBeReused() {
        Stream<String> stream = Stream.of("a", "b", "c").filter(e -> e.contains("b"));
        Optional<String> any = stream.findAny();

        assertThatThrownBy(stream::findFirst)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("stream has already been operated upon or closed");
    }

    @Test
    void streamPipeline() {
        List<String> list = Arrays.asList("def1", "ghi2", "abc3");
        List<String> collect = list.stream()
                .map(e -> e.substring(0, 3))
                .sorted()
                .skip(1)
                .collect(Collectors.toList());

        assertThat(collect.size()).isEqualTo(2);
        assertThat(collect.get(0)).isEqualTo("def");
        assertThat(collect.get(1)).isEqualTo("ghi");
    }


    @Test
    @DisplayName("terminal operation が呼び出されていないから intermediate operation が実行されていない")
    void lazyInvocation() {
        List<String> list = Arrays.asList("abc1", "abc2", "abc3");
        counter = 0;

        Stream<String> stream = list.stream()
                .skip(2)
                .map(element -> {
                    wasCalled();
                    return element.substring(0, 3);
                });

        // wasCalled() is not called yet(= intermediate operations are lazy)
        assertThat(counter).isEqualTo(0);
    }

    @Test
    void lazyInvocation2() {
        List<String> list = Arrays.asList("abc1", "abc2", "abc3");
        counter = 0;

        long size = list.stream()
                .skip(2)
                .map(element -> {
                    wasCalled();
                    return element.substring(0, 3);
                })
                .count();

        assertThat(counter).isEqualTo(1);
    }

    @Test
    void lazyInvocation3() {
        List<String> list = Arrays.asList("abc1", "abc2", "abc3");
        counter = 0;

        long size = list.stream()
                .map(element -> {
                    wasCalled();
                    return element.substring(0, 3);
                })
                .skip(2)
                .count();

        assertThat(counter).isEqualTo(3);
    }

    @Test
    void reduce() {
        List<Integer> intStreamRange = IntStream.range(1, 3).
                boxed()
                .collect(Collectors.toList());
        assertThat(intStreamRange).isEqualTo(List.of(1, 2)); // [1, 2]

        // OptionalInt reduce = IntStream.range(1, 3).reduce(Integer::sum);
        OptionalInt reduce = IntStream.range(1, 3)
                .reduce((a, b) -> a + b); // 1+2

        if (reduce.isPresent()) {
            assertThat(reduce.getAsInt()).isEqualTo(3);
        }
    }

    @Test
    void reduce2() {
        int reduced = IntStream.range(1, 6)
                .reduce(Integer::sum)// 1+2+3+4+5
                .orElseThrow();

        assertThat(reduced).isEqualTo(15);
    }

    @Test
    void reduce3() {
        List<String> letters = Arrays.asList("a", "b", "c", "d", "e");
        String result = letters.stream()
                // .reduce("", (partialString, element) -> partialString + element);
                .reduce("", String::concat);

        assertThat(result).isEqualTo("abcde");
    }

    @Test
    void reduce4() {
        List<Human> users = Arrays.asList(new Human("John", 30), new Human("Julie", 35));

        int computedAges =
                users.stream()
                        .reduce(0, (partialAgeResult, human)
                                    -> partialAgeResult + human.getAge(), Integer::sum);

        assertThat(computedAges).isEqualTo(65);
    }

    @Test
    void collect() {
        String collect = footballTeam.stream()
                .map(Player::getName)
                .collect(Collectors.joining(", ", "[", "]"));
        assertThat(collect).isEqualTo("[mike, popcorn, kendrick, kanye]");
    }

    @Test
    void summaryStatistics() {
        IntSummaryStatistics summaryStatistics = footballTeam.stream()
                .collect(Collectors.summarizingInt(Player::getAge));

        System.out.println(summaryStatistics); // IntSummaryStatistics{count=4, sum=104, min=22, average=26.000000, max=31}
        long expectedAverage = Math.round(summaryStatistics.getAverage());
        assertThat(expectedAverage).isEqualTo(26L);
    }

    @Test
    void groupingBy() {
        Map<Integer, List<Player>> collect = footballTeam.stream()
                .collect(Collectors.groupingBy(Player::getAge));

        assertThat(collect.get(22).size()).isEqualTo(2);
    }

    @Test
    void partitioningBy() {
        Map<Boolean, List<Player>> collect = footballTeam.stream()
                .collect(Collectors.partitioningBy(e -> e.getAge() >= 29));

        assertThat(collect.get(true).size()).isEqualTo(2);
    }

    @Test
    void mapStream() {
        // key-value pair
        // Set<Map.Entry<String, String>> entries = books.entrySet();
        String optionalIsbn = books.entrySet().stream()
                // .filter(e -> "Effective Java".equals(e.getValue()))
                .filter(e -> e.getValue().startsWith("Effective Java"))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();


        assertThat(optionalIsbn).isEqualTo("978-0134685991");
    }

    @Test
    void mapStream2() {
        books.put("978-0321356680", "Effective Java: Second Edition");

        List<String> isbnCodes = books.entrySet().stream()
                .filter(e -> e.getValue().startsWith("Effective Java"))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        assertThat(isbnCodes.contains("978-0134685991")).isTrue();
        assertThat(isbnCodes.contains("978-0321356680")).isTrue();
    }

    @Test
    void mapStream3() {
        List<String> titles = books.entrySet().stream()
                .filter(e -> e.getKey().startsWith("978-0"))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        assertThat(titles.size()).isEqualTo(2);
        assertThat(titles.contains("Design patterns")).isTrue();
        assertThat(titles.contains("Effective Java")).isTrue();
    }
}
