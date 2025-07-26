# Функциональные интерфейсы в Java

**Функциональный интерфейс** — это интерфейс с **одним абстрактным методом**, предназначенный для использования с **лямбда-выражениями** и **method references**. Он лежит в основе функционального программирования в Java с версии **Java 8**.

---

## 🔹 Основное определение

> Функциональный интерфейс — это интерфейс, который содержит **ровно один абстрактный метод**. Такой интерфейс может быть **представлен лямбдой** или **ссылкой на метод**.

```java
@FunctionalInterface
interface Calculator {
    int compute(int x, int y);
}
```

🔍 Аннотация `@FunctionalInterface` **не обязательна**, но помогает компилятору обнаружить ошибки — например, если ты случайно добавишь второй абстрактный метод.

---

## 🔹 Что разрешено в функциональном интерфейсе

✅ Один абстрактный метод  
✅ Любое количество `default` и `static` методов  
✅ Переопределения `Object`-методов (`equals()`, `toString()` и др.)

```java
@FunctionalInterface
interface Action {
    void execute();

    default void log() {
        System.out.println("Executing");
    }

    static void helper() {
        System.out.println("Helper");
    }
}
```

---

## ❌ Что запрещено

- ❌ Более одного абстрактного метода → ошибка при использовании `@FunctionalInterface`
- ❌ Нельзя применять `@FunctionalInterface` к интерфейсу без методов

---

## 🔹 Примеры встроенных функциональных интерфейсов (`java.util.function`)

| Интерфейс         | Назначение                                 | Метод         |
|-------------------|---------------------------------------------|---------------|
| `Function<T,R>`   | принимает `T`, возвращает `R`              | `apply(T)`    |
| `Consumer<T>`     | принимает `T`, ничего не возвращает        | `accept(T)`   |
| `Supplier<T>`     | ничего не принимает, возвращает `T`        | `get()`       |
| `Predicate<T>`    | принимает `T`, возвращает `boolean`        | `test(T)`     |
| `UnaryOperator<T>`| T → T (один аргумент, один результат)      | `apply(T)`    |
| `BiFunction<T,U,R>`| принимает `T`, `U`, возвращает `R`        | `apply(T,U)`  |
| `BinaryOperator<T>`| T, T → T (два одинаковых аргумента)       | `apply(T,T)`  |

---

## 🔹 Использование с лямбдой

```java
Function<String, Integer> length = s -> s.length();
System.out.println(length.apply("hello")); // 5
```

```java
Predicate<Integer> isEven = n -> n % 2 == 0;
System.out.println(isEven.test(4)); // true
```

---

## 🔹 Пользовательские функциональные интерфейсы

```java
@FunctionalInterface
interface StringChecker {
    boolean check(String s);
}
```

Использование:
```java
StringChecker isLong = s -> s.length() > 10;
System.out.println(isLong.check("hello world")); // true
```

---

## 🔹 Взаимодействие с лямбдами и method reference

```java
Consumer<String> printer = System.out::println;
printer.accept("Привет");
```

```java
Runnable runner = () -> System.out.println("Running");
runner.run();
```

---

## 🔹 Особенности и тонкости

- Интерфейс считается функциональным, даже если не помечен `@FunctionalInterface`, **если он удовлетворяет требованиям**.
- Методы, унаследованные от `Object`, **не считаются** абстрактными (например, `equals()` или `hashCode()`).
- `default` и `static` методы не влияют на функциональность интерфейса.
- Анонимные классы работают с много-методными интерфейсами, **а лямбды — только с функциональными**.

---

## 📌 Вывод

> Функциональный интерфейс — это основа лямбд и method reference в Java. Он позволяет передавать поведение как объект, что делает возможным декларативный стиль программирования. Встроенные интерфейсы в `java.util.function` покрывают все базовые сценарии, а пользовательские можно создавать по мере необходимости.

---
