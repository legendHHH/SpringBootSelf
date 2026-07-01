## 每日学习

### 2026-07-01
#### 1.Java中如何实现线程安全的单例模式？
在Java中，实现线程安全的单例模式通常有以下几种方式：

- 1、懒汉式(线程安全)：使用synchronized关键字同步获取实例的方法，确保只有一个线程可以执行该方法，实现线程安全。但这种方式效率较低。
```java
public class Singleton {
    
    private static Singleton instance;

    private Singleton() {
    }
    
    //懒汉式(线程安全)
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```


- 2、饿汉式(线程安全)：实例在类加载时就创建，由于类加载机制保证了线程安全，这种方式简单但可能会导致资源浪费。
```java
public class Singleton2 {

    private static Singleton2 instance = new Singleton2();

    private Singleton2() {
    }

    //饿汉式(线程安全)
    public static Singleton2 getInstance() {
        return instance;
    }
}
```

- 3、双重校验锁(线程安全)：结合懒汉式和同步锁的优点，只在实例未创建时同步，提高效率。

```java
public class Singleton3 {

    private volatile static Singleton3 instance;

    private Singleton3() {
    }

    //双重校验锁(线程安全)
    public static Singleton3 getInstance() {
        if (instance == null) {
            synchronized (Singleton3.class) {
                if (instance == null) {
                    instance = new Singleton3();
                }
            }
        }
        return instance;
    }
}
```

- 4、静态内部类(推荐)：利用类加载机制保证初始化实例时只有一个线程。

```java
public class Singleton4 {

    private Singleton4() {
    }

    private static class SingletonHandle {
        private static final Singleton4 INSTANCE = new Singleton4();
    }

    //静态内部类(推荐)
    public static Singleton4 getInstance() {
        return SingletonHandle.INSTANCE;
    }
}
```

- 5、枚举(最佳方法)：利用枚举只有一个实例，并且枚举自身提供了序列化机制和线程安全的保证

```java
public enum Singleton5 {
    INSTANCE;
}
```


#### 2.Java中HashMap的工作原理是什么
Java中HashMap是基于散列的非同步实现，其原理如下：

- 1、数据结构：HashMap基于数组和链表的结构，数组用于存储元素，链表用于解决哈希冲突
- 2、哈希函数：当添加一个元素时，HashMap会使用哈希函数计算键的哈希码，然后用这个哈希码来决定元素在数组中的存储位置。
- 3、处理哈希冲突：当不同的键产生相同的哈希码时，会发生哈希冲突。HashMap通过链表来处理这种冲突，将具有相同哈希码的元素存储在同一数组位置的链表中。
- 4、动态扩容：当数组的填充度超过阈值(默认为数组容量的75%)，HashMap会进行扩容，通常扩容为原来的两倍，并重新分配所有元素。
- 5、Java8中的改进：当链表长度超过一定阈值(默认为8)时，链表会转换为红黑树，以改善在哈希冲突情况下的性能。


#### 3.Java中的垃圾回收机制如何工作
Java中的垃圾回收(GC) 机制是自动管理内存的过程，主要包括以下步骤：
- 1、标记：垃圾收集器会标记所有从根集合(如本地变量、活动线程等)可达的对象。
- 2、删除/压缩：对于未被标记的对象，GC会进行清除。它可能会直接删除这些对象，或者将存活的对象压缩到内存的一端，以减少碎片化。
- 3、分代收集：Java GC 采用分代垃圾收集机制，将对象分为年轻代(Young Generation)、老年代(Old Generation)和永久代(PermGen，Java8后改为元空间)。不同的代使用不同的垃圾回收策略。
- 4、垃圾收集算法：包括标记-清除、复制算法、标记-整理等。不同的收集器(如Serial、Parallel、CMS、G1) 实现了这些算法的不同组合和变种。
- 5、性能考量：GC的执行可能会暂停应用程序，称为“Stop-The-World”。不同的收集器在延迟和吞吐量之间提供不同的权衡。


#### 4.Java中，如何避免死锁
避免Java中的死锁可以通过以下方法实现：
- 1、避免嵌套锁：尽量避免一个线程在持有一个锁的同时去请求另一个锁。
- 2、请求和释放锁的顺序：确保所有线程以相同的顺序请求和释放锁。
- 3、使用定时锁：使用tryLock() 方法来请求锁，它允许线程等待锁一定的时间后放弃，从而避免死锁。
- 4、锁分割：将大的锁分割成几个小的锁，如果可能的话，使得不同的线程可以同时访问不同的资源。
- 5、检测死锁：使用工具或JVM内置功能(如JConsole)来监控和检测系统的死锁，然后进行相应的处理。




### 2026-06-30

#### 1.Java中的内存模型是如何设计的
- 1、堆(Heap): 这是Java内存管理中最大的一块，被所有线程共享。在堆中主要存放Java对象实例和数组。
- 2、栈(Stack): 每个线程运行时都会创建一个栈，用于存放局部变量、操作数栈、动态链接和方法出口等。栈的生命周期和线程同步。
- 3、方法区(Method Area): 也被称为元空间，在Java8之前称为永久代。同样被所有线程共享、用于存储已被虚拟机加载的类信息、常量、静态变量等数据。
- 4、本地方法栈(Native Method Stack): 专门用于处理本地方法的调用。
- 5、程序计数器(Program Counter)：每个线程都有自己的程序计数器，用于指示线程当前执行指令的位置。

>此外，Java内存模型还包括程序计数器等组件，共同构成了Java的运行时数据区。
>Java内存模型的设计使得JVM可以高效地处理内存分配和垃圾回收，保障多线程环境下的内存一致性和线程隔离。



### 2.Java多线程中，synchronized和ReentrantLock的区别是什么？
synchronized和ReentrantLock都是用于控制多线程访问同步资源的机制，但它们有以下不同点：

- 1、锁的实现方式：synchronized是依赖于JVM实现的，而ReentrantLock是Java提供的API。
- 2、锁的公平性：synchronized只能是非公平锁，而ReentrantLock可以指定为公平锁或非公平锁。
- 3、锁的灵活性：ReentrantLock提供了更多的功能，比如可以终端等待锁的线程。
- 4、性能：在JDK1.6之后，synchronized的性能得到了很大的优化，和ReentrantLock比较接近。
- 5、锁的细粒度控制：ReentrantLock可以更精确的控制锁，有丰富的锁操作方法。



### 3.Java中的垃圾回收机制
Java的垃圾回收机制是自动管理内存的一种方式，其核心原理和步骤如下：

- 1、标记：首先标记出所有可达的对象。
- 2、删除/整理：删除所有不可达的对象或者将存活的对象移动到连续的空间内，释放内存空间。
- 3、垃圾回收器类型：Java提供了多种垃圾回收器，如：Serial、Parallel、CMS、G1等，每种回收器适用于不同的场景和需求。
- 4、Minor GC和Major GC：Minor GC清理年轻代，而Major GC通常清理老年代，Full GC会清理整个堆空间。


### 4.Java中的反射机制是什么？
Java反射机制是指运行状态中，对于任意一个类，都能够知道这个类的所有属性和方法；对于任意一个对象，都能够调用它的任意一个
方法和属性。这种动态获取信息及以动态调用对象的方法的功能称为Java语言的反射机制。主要包括以下内容：

- 1、获取Class对象的三种方式：直接通过对象调用getClass() 方法，使用Class类的静态方法forName(String className)，或者通过类名.class属性。
- 2、创建对象：通过Class对象的newInstance()方法创建其对应类的实例。
- 3、获取方法：使用Class对象的getDeclareMethods() 方法获取类内定义的所有方法。
- 4、询问字段：使用Class对象的getDeclareFields()  方法访问类内定义的字段。
- 5、调用方法：通过Method对象的invoke() 方法调用具体的方法


### 5.Java异常处理机制
Java的异常处理机制是一种错误处理的机制，它将错误和异常的处理代码从正常代码中分离出来，以提高程序的可读性和可维护性。
主要包括以下几个要点：

- 1、异常类层次：Java中的异常分为检查型一场(checked exceptions)和非检查型异常(unchecked exceptions，包括运行时异常和错误)。
- 2、try-catch-finally块：使用try块包围可能产生异常的代码，catch块捕获和处理异常，finally块提供总会执行的代码。
- 3、抛出异常：使用throws关键字抛出异常实例。
- 4、异常链：通过在一个异常的构造器中传递另一个异常，可以创建一个异常链，反映多重失败的情况。
- 5、自定义异常：通过继承Exception类或子类来创建自定义异常。
