# Ссылки на методы в Java (Method References)

Ссылки на методы (method references) — это **укороченный синтаксис** для **лямбд**, которые просто вызывают существующий метод. Они появились в **Java 8** вместе с лямбда-выражениями.

---

## 🔹 Общий синтаксис

```java
ClassName::methodName
objectRef::methodName
ClassName::new
```

Используется, когда:
- Лямбда **просто вызывает метод** без добавочной логики
- Метод уже существует и соответствует функциональному интерфейсу

---

## 🔹 Виды ссылок на методы

| Вид                                | Синтаксис                | Пример                                |
|------------------------------------|--------------------------|----------------------------------------|
| Ссылка на **статический метод**     | `ClassName::staticMethod`| `Integer::parseInt`                    |
| Ссылка на **экземплярный метод** объекта | `objectRef::instanceMethod`| `System.out::println`              |
| Ссылка на **экземплярный метод по типу** | `ClassName::instanceMethod`| `String::toLowerCase`              |
| Ссылка на **конструктор**           | `ClassName::new`         | `ArrayList::new`                       |

---

## 🔹 Примеры

### 📌 Статический метод

```java
Function<String, Integer> parser = Integer::parseInt;
// эквивалентно: s -> Integer.parseInt(s)
```

### 📌 Метод экземпляра (через объект)

```java
PrintStream out = System.out;
Consumer<String> printer = out::println;
// эквивалентно: s -> out.println(s)
```

### 📌 Метод экземпляра по типу

```java
Function<String, String> lower = String::toLowerCase;
// эквивалентно: s -> s.toLowerCase()
```

### 📌 Конструктор

```java
Supplier<List<String>> listSupplier = ArrayList::new;
// эквивалентно: () -> new ArrayList<>()
```

---

## 🔹 Method reference и функциональные интерфейсы

Method reference — это всего лишь **короткий способ передать метод как реализацию** абстрактного метода интерфейса. Работает только с **функциональными интерфейсами** (один абстрактный метод).

```java
@FunctionalInterface
interface StringTransformer {
    String transform(String s);
}

public class Main {
    public static void main(String[] args) {
        StringTransformer toUpper = String::toUpperCase;
        System.out.println(toUpper.transform("hello")); // HELLO
    }
}
```

---

## 🔹 Примеры в Stream API

```java
List<String> names = List.of("Alice", "Bob", "Charlie");

// вместо .forEach(s -> System.out.println(s))
names.forEach(System.out::println);

// вместо .map(s -> s.toUpperCase())
names.stream()
     .map(String::toUpperCase)
     .forEach(System.out::println);
```

---

## 🔹 Метод экземпляра по типу — как работает?

```java
Function<String, String> f = String::trim;
```

Это лямбда, которая принимает строку и вызывает `trim()`:
```java
s -> s.trim()
```

То есть:
- Аргумент становится **получателем метода**
- Метод вызывается **на этом аргументе**

---

## 🔹 Метод с аргументами

```java
BiFunction<String, String, Boolean> equals = String::equals;
// эквивалентно: (a, b) -> a.equals(b)
```

---

## 🔹 Конструкторы с аргументами

```java
Function<String, Integer> toInt = Integer::new;
Integer x = toInt.apply("123"); // 123
```

Работает, если у класса есть подходящий конструктор.

---

## 🔹 Преимущества method reference

✅ Код компактнее и читабельнее  
✅ Избегается дублирование  
✅ Хорошо сочетается с Stream API  
✅ Идеально подходит, когда лямбда просто делегирует вызов

---

## 🔹 Ограничения

- Работает только с **существующими методами/конструкторами**
- Метод должен **совпадать по сигнатуре** с интерфейсом
- Нельзя использовать, если требуется дополнительная логика

---

## 📌 Вывод

> Ссылки на методы (`::`) — это синтаксический сахар для лямбд, которые просто вызывают методы. Они повышают читаемость, особенно в Stream API и коллекциях, и делают код лаконичным.

---
