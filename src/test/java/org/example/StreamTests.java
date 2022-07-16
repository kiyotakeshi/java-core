package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StreamTests {

    private int counter;

    private void wasCalled() {
        counter++;
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

    // TODO: https://www.baeldung.com/java-8-streams#2-the-collect-method
    @Test
    void name() {

    }
}
