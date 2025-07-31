package org.example.animals;

import java.util.Objects;

public class Cat implements Comparable<Cat> {

    private String name;
    private Integer age;

    public Cat(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return this.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Cat)) {
            return false;
        }
        Cat oth = (Cat) obj;

        return name.equals(oth.name) && age.intValue() == oth.age.intValue();
    }

    @Override
    public String toString() {
        return String.format("cat name = %s age = %d", name, age);
    }

    @Override
    public int compareTo(Cat o) {
        return Integer.compare(this.age, o.age);
    }
}
