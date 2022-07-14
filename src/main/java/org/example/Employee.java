package org.example;

import lombok.Data;
import lombok.Value;

@Value
public class Employee implements Comparable<Employee> {

    String name;
    int age;
    double salary;
    long mobile;

    @Override
    public int compareTo(Employee otherEmployee) {
        return name.compareTo(otherEmployee.getName());
    }
}
