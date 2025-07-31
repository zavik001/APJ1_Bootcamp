package org.example;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSequentialList;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.SplittableRandom;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.example.animals.Cat;
import org.example.animals.Dog;
import org.example.executor.SerialExecutor;
import org.example.forkjoinpoll.CountNodesTask;
import org.example.forkjoinpoll.TreeNode;
import org.example.thread.MyThread;
import org.w3c.dom.Node;

public class App {

    static final Consumer<String> print = System.out::println;
    static final Consumer<Number> printNumber = i -> System.out.println(i.intValue() + "");
    static final Predicate<? extends Number> checkGreat = i -> i.intValue() > 100;
    static final Random random = new Random();
    static final SplittableRandom randomm = new SplittableRandom();
    static long counter = 0;
    static boolean flag = true;

    public static void main(String[] args) {
        // example1();
        // example2();
        // example3();
        // example4();
        // example5();
        // example6();
        // example7();
        // example8();
        // example9();
        // example10();
        example11();
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
        // Thread Runable
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

    static void example7() {
        // multithreding
        // part 3
        // java 5
        // ExecutorService, Callable, Future
        // проблема с thread
        // 1. Нельзя управлять ими централизованно
        // 2. Нельзя получить результаты работы программы
        // РЕШНИЕ 
        // ExcutorService
        // 1. Управляет пулом потоков
        // 2. Ставит задачи в очередь
        // 3. сама решает когда и каким потоком выпольнить задачу
        // ЗАМЕНА ручному управлению потоками
        // public interface ExecutorService extends Executor, AutoCloseable {}

        // Начала
        // Excutor
        // public interface Executor {
        // void execute(Runnable command);
        // }
        // раньше: - Runnable - Что делать — описание задачи (код, который должен выполниться)
        // java 5 : - Executor - Как и когда делать — принимает Runnable и решает, как его выполнить (в каком потоке, когда и т.д.)
        // раньше
        Runnable task = () -> System.out.println("Hello");
        new Thread(task).start(); // создаем поток вручную
        // А теперь можно:
        Runnable taskE = () -> System.out.println("Привет");
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(taskE); // передаем задачу Executor'у

        // Executor не требует, чтобы выполнение задачи было обязательно асинхронным.
        // В самом простом случае - задача может быть выполнена немедленно в том же потоке, который её
        Executor taskD = r -> r.run();

        // Более часто задачи выполняются в другом потоке, не в потоке вызывающего кода.
        Executor tadkC = r -> new Thread(r).start(); // каждый вызов execute() создаёт новый поток:

        // Некоторые реализации Executor ограничивают, как и когда задачи выполняются.
        // Пример SerialExecutor, который гарантирует последовательное выполнение задач, одну за другой:
        SerialExecutor executor1 = new SerialExecutor(Executors.newSingleThreadExecutor());

        // ExecutorService расширяет Executor: добавляет submit(), shutdown() и др.
        // Абстрактная реализация AbstractExecutorService. Конкретная ThreadPoolExecutor.
        ExecutorService ex1 = new ThreadPoolExecutor(
                10,
                100,
                1000,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(1000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
        // 10 - corePoolSize - минимальное количество потоков, которые всегда остаются активным (даже если они ничего не делают)
        // 100 - macimumPoolSize - максимальное количество потоков, которое может быть создано при высокой нагрузке
        // 1000 -  // keepAliveTime — время (в заданных единицах), которое "лишние" потоки (выше corePoolSize) живут без задач перед завершением
        // единицы измерения для keepAliveTime (секунды, миллисекунды и т.д.)
        // new LinkedBlockingDeque<>() очередь задач сюда поподают задачи, если все core потоки занятны
        // Executors.defaultThreadFactory(), фабрика для создания новых потоков (можно передать свою, чтобы задать имена, приоритет и т.п.)
        // new ThreadPoolExecutor.DiscardPolicy() обработчик переполнения — что делать, если очередь полна и потоки достигли maximumPoolSize:
        // Варианты:
        // - AbortPolicy (по умолчанию) — выбрасывает исключение
        // - CallerRunsPolicy — задача выполняется в том потоке, который вызвал submit()
        // - DiscardPolicy — задача просто отбрасывается без ошибки
        // - DiscardOldestPolicy — удаляет из очереди самую старую задачу и пытается добавить новую
        // ЧТОБЫ КАЖДЫЙ РАЗ не писать все эти параметры вручную, есть класс Executors — ФАБРИКА, которая создает готовые экземпляры ExecutorService.

        List<Callable<Integer>> taskds = new LinkedList<>();
        for (int i = 0; i < 1000; i++) {
            taskds.add(() -> {
                int sum = 0;
                for (int j = 0; j < 1_000_000; j++) {
                    sum += j;
                }
                return sum;
            });
        }
        taskds.stream().parallel()
                .map(ex1::submit)
                .forEachOrdered(i -> {
                    try {
                        System.out.println(i.get() + "->" + randomm.nextInt());
                    } catch (ExecutionException | InterruptedException e) {
                        e.getMessage();
                    }
                });
        ex1.shutdown();
        ((ExecutorService) executor).shutdown();
        // ВАЖНО: пулы потоков ПЕРЕИСПОЛЬЗУЮТ потоки, а не создают их заново для каждой задачи.
        // Это критично для производительности, потому что создание потока дорогая операция:
        // - каждый поток создаётся через вызов ОС (pthread_create на Linux),
        // - системе нужно выделить стек (~1 МБ),
        // - поток нужно зарегистрировать в планировщике ОС,
        // - а ещё переключение контекста между потоками тоже затратное.
        // Поэтому лучше один раз создать пул с нужным числом потоков и просто переиспользовать их,
        // отправляя задачи через submit() — это быстро и экономит ресурсы.

        // etc ... На самом деле, про Executor и всю инфраструктуру вокруг можно написать целую книгу.
        // ScheduledExecutorService
        // 
    }

    static void example8() {
        // multithreding
        // part 4
        // ForkJoinPool java 7
        // разделяй и властвуй
        // public class ForkJoinPool extends AbstractExecutorService {
        // не удивительно что он расширяет функционал ExecutorService

        // ПРОБЛЕМА:
        // когда у нас ThreadPoolExecutor у нас есть очевидная проблема:
        // поток берет тяжелую задачу и он там зависает
        // и если у нас данные общие-(без него общую задачу не собрать) = это ПРОБЛЕМА
        // остальные потоки могут уже все сделать и просто сидеть ждать
        // РЕШЕНИЕ:
        // ЕСЛИ ЗАДАЧА ДЕЛИТСЯ НА МАЛЕНЬКИЕ ПОДЗАДАЧИ → ForkJoinPool как раз тут и заходит
        // он делит рекурсивно задачу на мелкие куски
        // у каждого потока своя очередь (deque)
        // если поток освободился — он не сидит без дела, а крадёт задачу из очереди другого (work stealing)
        // вот и всё — максимальная загрузка ядер
        // НО:
        // Если задача не делится ForkJoin ничем не поможет, это просто другой Executor
        // тут идея БЫСТРО ВЫПОЛНИТЬ МАЛЕНЬКИЕ ЗАДАЧИ, разумно разделяя на части
        // ВАЖНЫЕ ДЕТАЛИ:
        // 1. Очереди и Work-Stealing
        //    - У каждого потока своя двухсторонняя очередь (deque)
        //    - Поток берёт задачи с головы своей очереди (LIFO) — это быстрее для кэша
        //    - Если поток освободился и в своей очереди задач нет он крадет задачи из хвоста очереди другого потока (FIFO)
        //    - Это уменьшает простаивание потоков максимальная загрузка CPU
        // 2. Разделение задачи
        //    - ForkJoinPool не делит задачи автоматически — это делает наш метод compute()
        //    - Если не делит адекватно никакого выигрыша не будет
        //    - Обычно используется паттерн "разделяй до порога":
        //          if (size < THRESHOLD) { // маленькая задача
        //              делаем напрямую
        //          } else {
        //              делим и форкаем
        //          }
        // 3. Асинхронный режим (asyncMode)
        //    - По умолчанию LIFO (последние задачи выполняются первыми)
        //    - Если в конструкторе указать asyncMode=true будет FIFO
        //    - Это полезно для асинхронных и потоково-ориентированных задач
        // 4. Ограничение на блокирующие вызовы
        //    - НЕЛЬЗЯ блокировать потоки ForkJoinPool (Thread.sleep(), синхронное IO и т.д.)
        //    - Это ворует потоки из пула общее выполнение замедляется
        //    - Для блокирующих операций есть ForkJoinPool.managedBlock() — он временно увеличивает число потоков
        // 5. Разница с обычным ExecutorService
        //    - ExecutorService — "запланировал и забыл", задачи тупо из общей очереди
        //    - ForkJoinPool — "разделил и выполнил", задачи сами порождают новые задачи
        //    - Автоматический баланс нагрузки через воровство задач
        // ПРИМЕР:
        // ЗАДАЧА — есть огромное дерево, надо посчитать количество узлов
        //    - не знаем заранее глубину и количество узлов
        //    - некоторые ветви очень длинные (дорого обходить), некоторые короткие
        // заранее нарезать ExecutorService-ом куски — хрень получится
        // ForkJoinPool решает тем что делит динамически (рекурсивно) + балансирует работу потоков (если реализовано адекватно)
        TreeNode root = new TreeNode();
        for (int i = 0; i < 100; i++) {
            TreeNode child = new TreeNode();
            for (int j = 0; j < 100; j++) {
                child.addChild(new TreeNode());
            }
            root.addChild(child);
        }

        ForkJoinPool pool = new ForkJoinPool(8); // создаём пул на 8 потоков
        int result = pool.invoke(new CountNodesTask(root));
        System.out.println("всего узлов: " + result);
        try {
            System.out.println("хмм: " + pool.commonPool());
            pool.close();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        // new ForkJoinPool():
        //   - parallelism (число потоков, по дефолту = Runtime.getRuntime().availableProcessors())
        //   - ForkJoinWorkerThreadFactory (фабрика потоков, можно своё)
        //   - UncaughtExceptionHandler (обработка исключений)
        //   - asyncMode (false по умолчанию, если true - FIFO)
        // ForkJoinTask — базовый абстрактный класс
        // Есть 2 готовых типа:
        //    - RecursiveTask<V> (с возвращаемым значением)
        //    - RecursiveAction (без возвращаемого значения)
        // МЕТОДЫ:
        // pool.invoke(task) — запускает и ждёт результата
        // pool.submit(task) — запускает асинхронно
        // fork() — отправить подзадачу на выполнение
        // join() — подождать результат подзадачи
        // invokeAll(tasks) — запустить пачку задач
    }

    static void example9() {
        // multithreading
        // part 5
        // java 8: CompletableFuture, parallelstream
    }

    static void example10() {
        // mutithreadin
        // part 6
        // java 21: Virtual Threads
    }

    static void example11() {
        // Collections
        // JCF
        // part 1
        // Java Collection Framework (JCF) представляет собой набор классов и интерфейсов,
        // предназначенных для хранения и обработки данных в оперативной памяти.

        // Iterable - базовый интерфес для всех коллекция в Java
        // Он предоставляет метод iterator(), который возвращает объект типа Iterator, необходимого для перебора элементов коллекции.
        // public interface Iterable<T> {
        // Iterator<T> iterator();
        // }
        // Интерфеqс Iterator предоставляет 4 ключевых метода для работы с элементами:
        // public interface Iterator<E> {
        //    boolean hasNext();
        //    E next();
        //    default void remove()
        //    default void forEachRemaining(Consumer<? super E> action)
        ArrayList<Integer> arr1 = new ArrayList<>();
        arr1.add(10);
        arr1.add(11);
        Iterator<Integer> ir = arr1.iterator();
        ir.forEachRemaining(printNumber);
        while (ir.hasNext()) {
            print.accept(ir.next() + "");
            ir.remove();
        }
        print.accept(arr1.size() + " " + ir.hasNext());

        // Эти интерфейсы позволяет коллекциям быть целевыми для использования в цикле for-each, упрошает итерацию по элементам.
        List<String> list1 = List.of("one", "teo", "three");
        for (String e : list1) {
            print.accept(e);
        }
        // в for - each используется iterator для выполнения цикла

        // Исключение ConcurrentModificationException
        // Исключение ConcurrentModificationException возникает когда структура коллекции изменяется во время коллекции
        // это может произойти если коллекция модифицируется через другой поток или даже в том же потоке
        List<String> list2 = new ArrayList<>();
        list2.add("add1");
        list2.add("add2");
        list2.add("add3");
        for (String s : list2) {
            if ("add1".equals(s)) {
                // list2.remove(s); // CuncurrentModificatonException
            }
        }

        // Интерфейс Collection<T>
        // public interface Collection<E> extends Iterable<E> {
        // расширяет интерфейс Itarable добавляет новые методы // size(), isEmpty(), contains(Object), add(E), remove(Object), containsAll(Collection), addAll(Collection), removeAll(Collection), retainAll(Collection), clear(), iterator(), forEach(Consumer), spliterator(), toArray(), toArray(T[]), stream(), parallelStream(), removeIf(Predicate), of(), of(E), of(E,E), of(E,E,E), of(E,E,E,E), of(E,E,E,E,E), of(E,E,E,E,E,E), of(E,E,E,E,E,E,E), of(E,E,E,E,E,E,E,E), of(E,E,E,E,E,E,E,E,E), of(E...)
        Collection<Integer> col = new ArrayList<>();

        // Интерфейс List<E> представляет упорядоченную коллекцию
        // public interface List<E> extends SequencedCollection<E> {}
        // public interface SequencedCollection<E> extends Collection<E> {} // java 21+
        List<Integer> listt = new ArrayList<>();

        // Интерфейс Set<E> коллекция без дубликатов. Каждый элемент в Set уникален.
        // public interface Set<E> extends Collection<E> {}
        Set<Integer> set = new TreeSet<>();
        // Если бы тут был HashSet я уже показывал в example2(), там была бы проблема если не переопределить правильно
        // equals и hashcode
        // А тут у нас TreeSet(и для HashSet также верно некоторые моменты) и история другая: 
        // ЭЛЕМЕНТЫ СОРТИРУЮТСЯ по compareTo() 
        // Если после добавления элемента в TreeSet мы меняем поля, которые влияют на порядок (compareTo),
        // коллекция может начать вести себя непредсказуемо: 
        //    - порядок "сломается"
        //    - contains() может вернуть false, хотя элемент явно есть
        //    - поведение итератора станет странным
        // И даже может случиться что в Set дубликаты
        print.accept("Set:");
        Cat cat1 = new Cat("cat1", 1);
        Cat cat2 = new Cat("cat2", 2);
        Cat cat3 = new Cat("cat3", 3);
        Set<Cat> cats = new TreeSet<>(Set.of(cat1, cat2, cat3));
        print.accept(cats.toString());

        cat2.setAge(-123);
        cat2.setName("cat-123");
        print.accept(cats.toString());
        print.accept(cats.contains(cat2) + "");

        cat2.setAge(1);
        cat2.setName("cat1");
        print.accept(cats.toString());

        // Интерфейс Queue<E> предоставляет функциональность очереди — структуры данных, работающей по принципу “первый пришел, первый обслужен” (FIFO).
        // public interface Queue<E> extends Collection<E> {}
        print.accept("Queue:");
        Queue<Integer> que = new LinkedList<>();
        que.add(78);
        que.offer(10);
        que.offer(11);
        que.offer(1);
        print.accept(que.toString());
        print.accept(que.poll() + " <- poll()");
        print.accept(que.toString());
        print.accept(que.peek() + " <- peek()");
        print.accept(que.toString());
        print.accept(que.remove() + " remove()");
        print.accept(que.toString());

        // Интерфейс Deque<E> (Double Ended Queue) расширяет Queue и представляет двустороннюю очередь, где элементы могут быть добавлены и удалены как с начала, так и с конца.
        // public interface Deque<E> extends Queue<E>, SequencedCollection<E> {} 
        print.accept("Deque:");
        Deque<Integer> deq = new ArrayDeque<>();
        deq.add(10);
        deq.addFirst(1);
        deq.addFirst(2);
        deq.addLast(15);
        deq.addLast(16);
        deq.offer(1000);
        deq.offerFirst(900);
        deq.offerFirst(901);
        deq.offerLast(1100);
        deq.offerLast(1101);
        deq.add(99999);
        print.accept(deq.toString());
        print.accept(deq.getFirst() + " <- getFirst()");
        print.accept(deq.getLast() + " <- getLast()");
        print.accept(deq.peekFirst() + " <- peekFirst()");
        print.accept(deq.peekLast() + " <- peekLast()");
        print.accept(deq.toString());
        print.accept(deq.poll() + " <- poll");
        print.accept(deq.pop() + " <- pop");
        print.accept(deq.toString());
        // Разница в поведении методов при ошибках, одни методы возвращают false/null, другие выбрасывают исключения

        // Интерфейс Map<K, V> представляет коллекцию пар “ключ-значение”, где каждому ключу сопоставлено одно значение.
        // отличается от других интерфейсов коллекций тем, что не является потомком интерфейса Collection.
        // public interface Map<K, V> {}
        print.accept("Map::");
        Map<Integer, Integer> map = new TreeMap<>();
        map.put(10, 100);
        map.put(11, 101);
        map.put(12, 102);
        map.putIfAbsent(13, 103);
        map.putIfAbsent(12, 1001);
        print.accept(map.toString());
        print.accept(map.containsKey(12) + " <- containsKey(12)");
        print.accept(map.containsValue(100) + " <- containsValue(100)");
        print.accept(map.getOrDefault(1000, 123) + " <- getOrDefault(1000, 123)");
        print.accept(map.get(10) + " <- get(10)");
        print.accept(map.toString());

        Set<Integer> sMap = map.keySet();
        Collection<Integer> cMap = map.values();

        print.accept(sMap.toString() + " <- public Set<K> keySet()");
        print.accept(cMap.toString() + " <- Collection<V> values()");

        // public interface Map<K, V> {
        //      public static interface Entry<K, V> {}
        // }        
        Set<Map.Entry<Integer, Integer>> entryMap = map.entrySet();
        print.accept(entryMap.toString() + " <- Set<Entry<K, V>> entrySet();");

        // Реализации коллекций
        // ArrayList это динамический массив, который хранит свои элементы внутри обычного массива объектов (Object[])
        // public class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, Serializable
        // transient Object[] elementData; }
        // Поле elementData помечено как transient для оптимизации сериализации:
        // - Реальный размер массива (capacity) обычно больше количества элементов (size)
        // - Чтобы не сериализовать пустые ячейки массива (null-элементы)
        // - ArrayList переопределяет writeObject/readObject для сериализации только нужных элементов
        // ArrayList не синхронизирован, поэтому не является потокобезопасным.
        ArrayList<Integer> arr = new ArrayList<>(10);
        // initialCapacity = 10;
        // по мере добавление новых элементов в ArrayList его емкость увиличивается автоматически
        // при увиличение размера создается новый массив и туда копируются все старые элементы
        // - ArrayList допускает хранение null значний
        // доступ по индексу за O(1);
        // удаление элемента в худшем случае за O(n);

        // LinkedList двусвязанный список
        // 
        // public class LinkedList<E>
        // extends AbstractSequentialList<E>
        // implements List<E>, Deque<E>, Cloneable, java.io.Serializable
        // {   
        // /**
        //  * Pointer to first node.
        //  */
        // transient Node<E> first;

        // /**
        //  * Pointer to last node.
        //  */
        // transient Node<E> last;
        LinkedList<Integer> lin = new LinkedList<>();
        // вставка/удаление не учитывая посик за O(1);
        // посик элемента в худшем случае за O(N);


        // HashMap - хранит данные в виде ключ-значение
        // public class HashMap<K,V> extends AbstractMap<K,V>
        // implements Map<K,V>, Cloneable, Serializable {}
        // transient Node<K,V>[] table;
        HashMap<Integer, Integer> mapp = new HashMap<>();
        // внутри hashmap использует массив где каждый элемент Node
        // static class Node<K,V> implements Map.Entry<K,V> {
        // final int hash; // хеш код
        // final K key; // ключ
        // V value; // значение 
        // Node<K,V> next; } // ссылка на следущий узел если есть коллизии
        // У HashMap есть два ключевых параметра, которые влияют на производительность это начальная емкость и коэфицент загрузки
        print.accept(mapp.put(10, 100) + " ");
        print.accept(mapp.put(10, 1000) + " "); // @return the previous value associated with {@code key}
            /**
            //  * Associates the specified value with the specified key in this map.
            //  * If the map previously contained a mapping for the key, the old
            //  * value is replaced.
            //  *
            //  * @param key key with which the specified value is to be associated
            //  * @param value value to be associated with the specified key
            //  * @return the previous value associated with {@code key}, or
            //  *         {@code null} if there was no mapping for {@code key}.
            //  *         (A {@code null} return can also indicate that the map
            //  *         previously associated {@code null} with {@code key}.)
            //  */
            // public V put(K key, V value) {
            //     return putVal(hash(key), key, value, false, true);
            // }
        // В общем обычная хеш таблица


        // TreeMap 
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        treeMap.put(100, 100);
        // на основе лежит красно-черное дерево
        // узлы дерево у нас такие 
        // satic final class Entry<K,V> implements Map.Entry<K,V> {
        // K key;
        // V value;
        // Entry<K,V> left;
        // Entry<K,V> right;
        // Entry<K,V> parent;
        // boolean color = BLACK;}
        // добавление/удаление/вставка за O(long N);
        
        // HashSet // TreeSet
        // etc....

        
        // Сортировка коллекций
        // Интерфейс Comparator<T>
        // Этот интерфейс позволяет вам создать внешний "сортировщик" (компаратор),
        // который можно передать в методы сортировки.
        // Создайте класс, который реализует интерфейс Comparator, и определите метод compare(T o1, T o2) для сравнения двух объектов.
        // @FunctionalInterface
        // public interface Comparator<T> {
        //  int compare(T o1, T o2); }
        // Если нужно отсортировать элементы в обратном порядке, вместо создания нового компаратора можно использовать метод reversed()
        Comparator<Cat> com = (o1, o2) -> o1.getAge() - o2.getAge();
        com.reversed();

        // Интерфейс Comparable<T>
        // Если вы хотите сделать сортировку “встроенной” в ваш класс, можно реализовать интерфейс 
        // Comparable у самого класса, который нужно сортировать.

        // etc. ..... .
    }
}