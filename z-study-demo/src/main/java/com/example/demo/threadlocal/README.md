## ThreadLocal总结

场景一：每个线程需要一个独享的对象(通常是工具类、典型需要使用的类有SimpleDateFormat和Random)

场景二：每个线程内需要保存全局变量(例如在拦截器中获取用户信息) 可以让不同方法直接使用,避免参数传递的麻烦



### ThreadLocal的两个作用
让某个需要用到的对象在线程隔离(每个线程都有自己的独立对象)
在任何方法中都可以轻松获取到该对象


根据共享对象的生成时机不同,选择initialValue或者set来保存对象


#### initialValue
场景一：在ThreadLocal第一次get的时候把对象给初始化出来,对象的初始化时机可以由我们控制


#### set
场景二：如果需要保存到ThreadLocal里的对象的生成时机不由我们随意控制,例如拦截器生成的用户信息

用ThreadLocal.set 直接放到我们的ThreadLocal中去,以便后续使用



### ThreadLocal好处
- 达到线程安全

- 不需要加锁,提高执行效率

- 更高效利用内存、节省开销
    相比于每个任务都创建一个SimpleDateFormat显然用ThreadLocal可以节省内存和开销
    
- 免去传参的繁琐
    不需要每次都传同样的参数
    ThreadLocal使得代码耦合度更低,更优雅
    


### ThreadLocal的主要方法
- T initialValue()∶初始化

该方法会返回当前线程对应的“初始值”，这是一个延迟加载的方法，只有在调用get的时候，才会触发
当线程第一次使用get方法访问变量时，将调用此方法
每个线程最多调用一次此方法，但如果已经调用了remove()后，再调用get()，则可以再次调用此方法
如果不重写本方法，这个方法会返回null。一般使用匿名内部类的方法来重写initialValue()方法


- void set(T t):为这个线程设置一个新值



- T get():得到这个线程对应的value。如果是首次调用get()，则会调用initialize来得到这个值

- void remove():删除对应这个线程的值



### ThreadLocal原理

搞清楚Thread ,ThreadLocal以及
ThreadLocalMap三者之间的关系



每个Thread对象中都持有一个
ThreadLocalMap成员变量
![](https://img2020.cnblogs.com/blog/1231979/202107/1231979-20210724152025931-864523633.png)


get方法
get方法是先取出当前线程的ThreadLocalMap
然后调用map.getEntry方法，把本ThreadLocal的引用作为参数传入
取出map中属于本ThreadLocal的value


注意:这个map以及map中的key和value都是保存在线程中的，而不是保存在ThreadLocal中


ThreadLocalMap类，也就是Thread.threadLocals
ThreadLocalMap类是每个线程Thread类里面的变量，里面最重要的是一个键值对数组Entry[] table，可以认为是一个map，键值对︰
键:这个ThreadLocal
值:实际需要的成员变量，比如user或者simpleDateFormat对象


### ThreadLocal注意点
- 内存泄露
什么是内存泄漏:某个对象不再有用，但是占用的内存却不能被回收

弱引用的特点∶如果这个对象只被弱引用关联（没有任何强引用关联)，那么这个对象就可以被回收
强引用：Object value = null; ....   value = v;



Value的泄露
ThreadLocalMap的每个Entry都是一个对key的弱引用，同时，每个Entry都包含了一个对value的强引用
正常情况下，当线程终止，保存在ThreadLocal里的value会被垃圾回收，因为没有任何强引用了
但是，如果线程不终止（比如线程需要保持很久），那么key对应的value就不能被回收，因为有以下的调用链︰

Thread ---> ThreadLocalMap ---> Entry ( key为null )--->Value


因为value和Thread之间还存在这个强引用链路，所以导致value无法回收，就可能会出现OOM


扫描key为null的Entry，并把对应的value设置为null
如果一个ThreadLocal不被使用，就可能导致value的内存泄漏





### 如何避免内存泄露（阿里规约)
调用remove方法，就会删除对应的Entry对象，可以避免内存泄漏，所以使用完ThreadLocal之后，应该调用remove方法
