package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

import org.example.animals.Cat;
import org.example.animals.Dog;

public class App {

    static final Consumer<String> print = System.out::println;
    static final Random random = new Random();

    public static void main(String[] args) {
        example1();
        example2();
    }

    static void example1() {
        // cat: a.equals(b) -> a.hashCode() == b.hashCode()
        Map<Cat, String> cats = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            cats.put(new Cat(random.nextInt() + "", random.nextInt()), "cat" + random.nextInt());
        }
        cats.entrySet()
                .stream()
                .map(entry -> entry.getKey().toString() + entry.getValue())
                .forEach(print);
    }

    static void example2() {
        // dog: a.equals(b) -> a.hashCode() != b.hashCode();
        Map<Dog, String> dogs = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            dogs.put(new Dog(random.nextInt() + "", random.nextInt()), random.nextInt() + "");
        }

        Dog r = new Dog("Rex", 10);
        dogs.put(r, " <-");

        Dog rr = new Dog("Rex", 10);

        print.accept(r.equals(rr) + "");

        print.accept(dogs.getOrDefault(rr, "rr such element does not exist in map ") + rr.toString());
        print.accept(r.toString() + " value: " + dogs.get(r));
    }

    static void example3() {
        // Дженерики в Java инвариантны.
        List<Number> elems = new ArrayList<>();
        elems.add(10);
        elems.add(11);
        long sum = sum(elems);

        // Мы не можем передать в sum список типа List<Integer>, так как дженерики инвариантны.
        List<Integer> elemsInteger = new ArrayList<>();
        elemsInteger.add(10);
        elemsInteger.add(11);
        // long summ = sum(elemsInteger); // compile error

        // То есть мы не можем делать такое присваивание:
        List<Integer> ints = new ArrayList<>();
        // List<Number> numbers = ints; // compile error

        // так как между производными типами отсутствует наследование,
        // хотя оно присутствует в исходных типах: Integer - это подтип Number
        // Итак, проблема: инвариантность дженериков сильно ограничивает возможности ПОЛИМОРФИЗМА и нам нужно какое-то решение....
        // Bounded Wildcards
        // 1. Ковариантность. Upper Bounded Wildcards
        // <? extends T
        // Пусть у нас есть следующая иерархия типов:
        // Object
        // |
        // Number (верхняя граница)
        // |
        // Integer
        // Тогда множество <? extends Number> имеет верхнюю границу Number
        // Итак, зная верхнюю границу типа, мы гарантированно знаем, что, тип элемента - это сама граница или тип, унаследованный от неё
        // Мы не знаем точно, какой это тип, однако гарантированно знаем, что мы можем работать с этим элементом как с родителем (Number)
        // 
        // Итак, <? extends T> ковариантен. Это означает, что если Integer - подтип Number,
        // то и List<Integer> - подтип List<? extends Number>. Тогда валидно такое присваивание:
        List<Integer> ins = new ArrayList<>();
        List<? extends Number> nums1 = ins;
        // Мы видим, что иерархия наследования исходных типов сохранилась. Нам гарантируется, что в списке лежат элементы,
        // чей тип - это Number или унаследованный от Number, то есть мы точно знаем их верхнюю границу.

        // Имея эти знания, мы можем переписать нашу функцию подсчета суммы sumUpperBounded
        // Теперь в нашей функции мы можем работать с любыми типами, которые наследуют Number
        long sum1 = sumUpperBounded(elems);
        long sum2 = sumUpperBounded(elemsInteger);
        // Однако, мы можем только читать из такого списка, но не добавлять элементы в него, причём запись запрещена на стадии компиляции.
        // Так работает потому, что мы не знаем точного типа элементов в списке,
        // ведь данный список может хранить элементы как типа Integer, так и Dobule
        // Если бы запись была возможна, то мы могли бы получить Heap Pollution.
        // Heap Pollution - это ситуация, когда какая-то переменная определённого типа ссылается на объект совсем иного типа.
        // При такой попытке присвоения мы получаем ClassCastException.

        // 2. Контравариантность. Lower Bounded Wildcards
        // <? super T>
        // Для примера вернемся к иерархии типов из прошлого примера:
        // Object
        // |
        // Number (верхняя граница)
        // |
        // Integer
        // Тогда множество <? super Integer> имеет нижнюю границу Integer 
        // и включает в себя типы выше или равно этой границы: Integer, Number, Object.
        // Итак, зная нижнюю границу исходного типа, мы гарантированно знаем,
        // что исходный тип по иерархии стоит выше или равно этой границы, но не наследует границу.
        List<Number> nums123 = new ArrayList<>();
        List<? super Integer> ints123 = nums123;

        // в общем 
        // ? extends T (читай как T)
        // ? super T (пиши T)
        // источник: https://github.com/gravity182/Algorithms/blob/master/doc/java/JavaTypeVariance.MD
    }

    static long sum(List<Number> numbers) {
        long sum = 0L;
        for (Number num : numbers) {
            sum += num.longValue();
        }

        return sum;
    }

    static long sumUpperBounded(List<? extends Number> numbers) {
        long sum = 0L;
        for (Number num : numbers) {
            sum += num.longValue();
        }
        return sum;
    }
}
