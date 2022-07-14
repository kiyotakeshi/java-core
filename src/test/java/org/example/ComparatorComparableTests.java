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

    Employee[] employees;

    Employee[] sortedEmployeesByName;
    private Employee[] employeesContainsNull;
    private Employee[] sortedEmployeesByNameWithNullsFirst;

    private Employee[] sameAgeContainsEmployees;

    private Employee[] sortedSameAgeContainsEmployees;

    @BeforeEach
    void setUp() {
        footballTeam = Arrays.asList(
                new Player(33, "mike", 22),
                new Player(11, "popcorn", 29),
                new Player(28, "kendrick", 31)
        );

        employees = new Employee[] {
                new Employee("mike", 25, 3000, 9922001),
                new Employee("popcorn", 22, 2000, 5924001),
                new Employee("kanye", 35, 4000, 3924401)
        };

        sortedEmployeesByName = new Employee[] {
                new Employee("kanye", 35, 4000, 3924401),
                new Employee("mike", 25, 3000, 9922001),
                new Employee("popcorn", 22, 2000, 5924001)
        };

        employeesContainsNull = new Employee[] {
                new Employee("mike", 25, 3000, 9922001),
                null,
                new Employee("popcorn", 22, 2000, 5924001),
                null,
                new Employee("kanye", 35, 4000, 3924401)
        };

        sortedEmployeesByNameWithNullsFirst = new Employee[] {
                null,
                null,
                new Employee("kanye", 35, 4000, 3924401),
                new Employee("mike", 25, 3000, 9922001),
                new Employee("popcorn", 22, 2000, 5924001)
        };

        sameAgeContainsEmployees = new Employee[] {
                new Employee("mike", 25, 3000, 9922001),
                new Employee("trout", 25, 3000, 9922001),
                new Employee("kendrick", 25, 5000, 9082001),
                new Employee("popcorn", 22, 2000, 5924001),
                new Employee("kanye", 35, 4000, 3924401)
        };

        sortedSameAgeContainsEmployees = new Employee[] {
                new Employee("popcorn", 22, 2000, 5924001),
                new Employee("kendrick", 25, 5000, 9082001),
                new Employee("mike", 25, 3000, 9922001),
                new Employee("trout", 25, 3000, 9922001),
                new Employee("kanye", 35, 4000, 3924401)
        };
    }

    @Test
    void comparable() {
        Collections.sort(footballTeam);
        // System.out.println(footballTeam);
        // [Player(ranking=11, name=popcorn, age=29), Player(ranking=28, name=kendrick, age=31), Player(ranking=33, name=mike, age=22)]
        assertThat(footballTeam.get(0).getName()).isEqualTo("popcorn");
    }

    @Test
    void useComparatorClass() {
        var playerComparator = new PlayerRankingComparator();
        Collections.sort(footballTeam, playerComparator);
        // System.out.println(footballTeam);
        // [Player(ranking=11, name=popcorn, age=29), Player(ranking=28, name=kendrick, age=31), Player(ranking=33, name=mike, age=22)]
        assertThat(footballTeam.get(0).getName()).isEqualTo("popcorn");
    }

    @Test
    void useComparatorClass2() {
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

    @Test
    void comparatorComparing() {
        Comparator<Employee> nameComparator = Comparator.comparing(Employee::getName);
        Arrays.sort(employees, nameComparator);
        assertThat(employees).isEqualTo(sortedEmployeesByName);
    }

    @Test
    void comparatorComparingReverse() {
        Employee[] sortedEmployeesByNameDesc = new Employee[] {
                new Employee("popcorn", 22, 2000, 5924001),
                new Employee("mike", 25, 3000, 9922001),
                new Employee("kanye", 35, 4000, 3924401)
        };

        // Comparator.comparing(Employee::getName, (e1, e2) -> {return e2.compareTo(e1);});
        // Comparator<Employee> nameDescComparator = nameComparator.reversed();
        Comparator<Employee> nameDescComparator = Comparator.comparing(Employee::getName, Comparator.reverseOrder());
        Arrays.sort(employees, nameDescComparator);
        assertThat(employees).isEqualTo(sortedEmployeesByNameDesc);
    }

    @Test
    void comparatorNaturalOrder() {
        Comparator<Employee> naturalOrderComparator = Comparator.naturalOrder();
        Arrays.sort(employees, naturalOrderComparator);
        assertThat(employees).isEqualTo(sortedEmployeesByName);
    }
    @Test
    void comparatorComparingNullsFirst() {
        Comparator<Employee> nameComparator = Comparator.comparing(Employee::getName);
        Comparator<Employee> nameComparatorNullFirst = Comparator.nullsFirst(nameComparator);
        Arrays.sort(employeesContainsNull, nameComparatorNullFirst);
        assertThat(employeesContainsNull).isEqualTo(sortedEmployeesByNameWithNullsFirst);
    }

    @Test
    void comparatorComparingAgeAndName() {
        Comparator<Employee> ageNameComparator = Comparator.comparing(Employee::getAge).thenComparing(Employee::getName);
        Arrays.sort(sameAgeContainsEmployees, ageNameComparator);
        assertThat(sameAgeContainsEmployees).isEqualTo(sortedSameAgeContainsEmployees);
    }
}
