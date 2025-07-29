package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.example.animals.Cat;
import org.example.animals.Dog;
import org.example.thread.MyThread;

public class App {

    static final Consumer<String> print = System.out::println;
    static final Consumer<Number> printNumber = i -> System.out.println(i.intValue() + "");
    static final Predicate<? extends Number> checkGreat = i -> i.intValue() > 100;
    static final Random random = new Random();
    static long counter = 0;
    static boolean flag = true;

    public static void main(String[] args) {
        // example1();
        // example2();
        // example3();
        // example4();
        // example5();
        example6();
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

    static void example4() {
        // Stream API
        // package java.util.stream;
        // public interface Stream<T extends Object> extends BaseStream<T, Stream<T>>
        // Builder) public static interface Builder<T extends Object> extends Consumer<T>
        Stream.Builder<Integer> strB = Stream.builder();
        strB.add(100);
        strB.add(1000);
        Stream<Integer> strBB = strB.build();
        strBB.forEach(printNumber);
        Stream<Integer> generate = Stream.generate(random::nextInt);
        generate.limit(100).forEach(printNumber);
        // Stateless и Stateful операции
        // 1. Операции без состояния, такие как map() и filter(), обрабатывают каждый элемент потока независимо от других.
        // Они идеально подходит для паралельной обработки
        // 2.
    }

    static void example5() {
        // multithreding
        // part 1
        // java.lang.Thread
        // 1
        MyThread myThread = new MyThread();
        myThread.start();
        System.out.println("Основной поток продолжает выполнение.");

        // 2
        Thread thread = new Thread(new MyThread());
        thread.start();

        // 3
        Thread thread2 = new Thread(() -> System.out.println("Поток: " + Thread.currentThread().getName() + " startt"));
        thread2.start();

        // start() — метод класса Thread, который создает новый поток и вызывает
        // метод run() в этом новом потоке. Вызов start() инициирует параллельное выполнение потока.
        // run() — это метод, содержащий код, который будет выполнен в потоке.
        // Если вызвать run() напрямую, без вызова start(), код будет выполнен в текущем потоке,
        // как обычный метод, и новый поток не будет создан.
        // join() - Синхронизация потоков
        String s1 = "thread3";
        Thread thread3 = new Thread(() -> {
            System.out.println("поток: " + Thread.currentThread().getName() + " start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.getMessage();
            }
            System.out.println("finish");
        }, s1);
        String s2 = "thread4";
        Thread thread4 = new Thread(() -> {
            System.out.println("поток: " + Thread.currentThread().getName() + " start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.getMessage();
            }
            System.out.println("finish");
        }, s2);
        thread3.start();
        thread4.start();
        try {
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.getMessage();
        }
        System.out.println("main continue");

        // Жизненный цикл потока
        Thread thread5 = new Thread(() -> {
            System.out.println("находимся в метде run");
        });
        System.out.println("Состояние потока после создания: " + thread5.getState()); // new
        thread5.start();
        System.out.println("Состояние потока после вызова start: " + thread5.getState()); // runable
        // время для завершения
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.getMessage();
        }
        System.out.println("Состояние потока после завершения: " + thread5.getState());

        // Daemon threads
        Thread deamonThread = new Thread(() -> {
            while (true) {
                try {
                    System.out.println("Демон поток работает ....");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.getMessage();
                }
            }
        });
        deamonThread.setDaemon(true);
        deamonThread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.getMessage();
        }
        System.out.println("Основной поток завершен .");

        // Приоритеты потоков
        // Приоритет потока можно установить с помощью метода setPriority(int priority)
        // Приоритеты лишь помогают JVM принимать решение, но не являются обязательными для исполнения.
        Thread minPriority = new Thread(() -> System.out.println("priority min"));
        Thread maxPriority = new Thread(() -> System.out.println("priority max"));
        minPriority.setPriority(Thread.MIN_PRIORITY);
        maxPriority.setPriority(Thread.MAX_PRIORITY);
        minPriority.start();
        maxPriority.start();

        // Thread Interruption
        // Прерывание потоков — это механизм, с помощью которого один поток может сигнализировать
        // другому о необходимости остановить выполнение.
        // Однако важно помнить, что прерывание не останавливает поток немедленно.
        // Это лишь сигнал, который поток может обработать
        Thread blockingThread = new Thread(() -> {
            try {
                while (true) {
                    System.out.println("Блокирующий поток работает ...");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                System.out.println("Блокирующий поток был прерван ...");
            }
            System.out.println("Блокирующий поток завершил работу");
        });
        blockingThread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.getMessage();
        }
        blockingThread.interrupt();
        // etc....
    }

    static void example6() {
        // multithreding
        // part 2
        // Race Condition
        // Когда несколько потоков одновременно пытаются изменить общие данные, и результат зависит от порядка выполнения.
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                counter++;
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                counter++;
            }
        });

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.getMessage();
        }
        System.out.println("counter : " + counter + " // ожиадемый результат: 2_000_000");

        // volatile не решает проблему так как, инкремент не атомарная операция
        // попробуем через synchronized
        counter = 0;
        final Object lock = new Object(); // нужен монитор этого объекта для synchronized;

        Thread threadS1 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                synchronized (lock) {
                    counter++;
                }
            }
        });

        Thread threadS2 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                synchronized (lock) {
                    counter++;
                }
            }
        });
        threadS1.start();
        threadS2.start();
        try {
            threadS1.join();
            threadS2.join();
        } catch (InterruptedException e) {
            e.getMessage();
        }
        System.out.println("counter : " + counter + " // ожиадемый результат: 2_000_000");
        // Решение через Lock
        Lock lockk = new ReentrantLock();
        counter = 0;
        Thread threadL1 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                lockk.lock();
                try {
                    counter++;
                } finally {
                    lockk.unlock();
                }
            }
        });

        Thread threadL2 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                lockk.lock();
                try {
                    counter++;
                } finally {
                    lockk.unlock();
                }
            }
        });
        threadL1.start();
        threadL2.start();
        try {
            threadL1.join();
            threadL2.join();
        } catch (InterruptedException e) {
            e.getMessage();
        }
        System.out.println("Counter (Lock): " + counter + " // ожидаемый: 2_000_000");

        // Deadlock
        // Два или более потоков блокируют друг друга, каждый ждёт ресурс, который удерживает другой
        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                synchronized (lock2) {  // Ждёт, пока t2 отпустит lock2
                    System.out.println("Thread 1");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                synchronized (lock1) {  // Ждёт, пока t1 отпустит lock1
                    System.out.println("Thread 2");
                }
            }
        });
        // synchronized не поддерживает таймауты — в отличие от ReentrantLock.tryLock(), он блокируется навсегда.
        // тут нам ничего не поможет кроме перезапуска программы  
        // t1.start();
        // t2.start();
        // 
        Thread worker = new Thread(() -> {
            while (flag) {
                System.out.println("Поток worker воркает");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        worker.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.getMessage();
        }
        // Проблема:
        flag = false;
        // более подробно : https://habr.com/ru/articles/685518/
        /*
        * Здесь возникает проблема видимости изменений между потоками.
        * 
        * Подробное объяснение:
        * 1. Каждый поток имеет свой стек и может кэшировать значения переменных
        * 2. Изменение flag в главном потоке может НЕ стать сразу видимым для worker потока
        * 3. Причины:
        *    - Оптимизации процессора (кэширование в регистрах CPU)
        *    - Отсутствие happens-before связи между потоками
        *    - JIT-компилятор может оптимизировать чтение flag в цикле while
        * 
        * Последствия:
        * - Worker поток может продолжать работать бесконечно
        * - Программа ведет себя непредсказуемо
        * - Ошибка трудно воспроизводима (зависит от железа/JVM)
        * 
        * Решение:
        * Использовать volatile для переменной flag:
        *   volatile boolean flag = true;
        * 
        * Почему volatile работает:
        * 1. Гарантирует запись в основную память (не только в кэш CPU)
        * 2. Создает happens-before связь между потоками
        * 3. Запрещает переупорядочивание операций вокруг volatile-переменной
        * 
        * Альтернативы (менее подходящие для этого случая):
        * - synchronized (избыточен для простого флага)
        * - AtomicBoolean (работает, но сложнее чем нужно)
        * - Lock (слишком тяжеловесный)
        * 
        * Когда НЕ использовать volatile:
        * - Для составных операций (i++)
        * - Когда несколько потоков могут менять переменную
         */

        // Livelock
        // Starvation
        // etc....
    }
}
