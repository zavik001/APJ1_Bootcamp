package org.example.animals;

public class Dog {

    private String name;
    private Integer age;

    public Dog(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public Integer getAge() {
        return this.age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Dog)) {
            return false;
        }
        Dog d = (Dog) o;
        return name.equals(d.getName()) && age.intValue() == d.getAge().intValue();
    }

    @Override
    public String toString() {
        return String.format("dog name = %s, age = %d", name, age);
    }
}
