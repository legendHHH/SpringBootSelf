package com.example.demo.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadLocal的使用
 *
 * @author legend
 */
public class ThreadLocalTest {

    /**
     * 只创建一次 SimpleDateFormat 对象，避免不必要的资源消耗
     */
    static SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static ExecutorService THREADPOOL = Executors.newFixedThreadPool(10);


    public static void main(String[] args) {
        //ThreadLocal测试2,避免参数传递的麻烦
        threadLocalDemo();
        //ThreadLocal测试
        //threadLocalMethod();
        //synchronized同步方法解决线程安全的问题
        //synchronizedTest();
        //一千个线程打印日期,用线程池来执行,出现线程安全问题
        //printOneThousandThreadForPool();
        //一千个线程打印日期 用线程池来执行
        //printOneThousandThread();
        //三十个线程打印日期
        //printThirtyThread();
        //两个线程打印日期
        //printTwoThread();
    }

    private static void threadLocalDemo() {
        new Service1().process();
    }


    static class Service1 {
        public static void process() {
            User user = new User("legend鲁毅");
            //将User对象存储到 holder 中
            UserContextHolder.holder.set(user);
            new Service2().process();
        }
    }

    static class Service2 {
        public void process() {
            User user = UserContextHolder.holder.get();
            System.out.println("Service2拿到用户名: " + user.name);
            new Service3().process();
        }
    }


    static class Service3 {
        public void process() {
            User user = UserContextHolder.holder.get();
            System.out.println("Service3拿到用户名: " + user.name);
        }
    }

    static class UserContextHolder {
        public static ThreadLocal<User> holder = new ThreadLocal<>();
    }

    static class User {
        String name;

        public User(String name) {
            this.name = name;
        }
    }


    /**
     * 利用 ThreadLocal 给每个线程分配自己的 dateFormat 对象
     * 不但保证了线程安全，还高效的利用了内存
     */
    public static void threadLocalMethod() {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            //提交任务
            THREADPOOL.submit(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalTest().date4(finalI);
                    System.out.println(date);
                }
            });
        }
        THREADPOOL.shutdown();
    }

    public String date4(int seconds) {

        //参数的单位是毫秒，从1970.1.1 00:00:00 GMT 开始计时
        Date date = new Date(1000 * seconds);
        //获取 SimpleDateFormat 对象
        SimpleDateFormat dateFormat = ThreadSafeFormatter.dateFormatThreadLocal.get();
        return dateFormat.format(date);
    }

    static class ThreadSafeFormatter {
        public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {

            //创建一份 SimpleDateFormat 对象
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            }
        };
    }


    /**
     * synchronized同步方法解决线程安全的问题
     */
    public static void synchronizedTest() {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            //提交任务
            THREADPOOL.submit(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalTest().date3(finalI);
                    System.out.println(date);
                }
            });
        }
        THREADPOOL.shutdown();
    }

    public String date3(int seconds) {

        //参数的单位是毫秒，从1970.1.1 00:00:00 GMT 开始计时
        Date date = new Date(1000 * seconds);
        String s;
        synchronized (ThreadLocalTest.class) {
            s = DATEFORMAT.format(date);
        }
        return s;
    }


    /**
     * 1000个线程打印日期,用线程池来执行,出现线程安全问题
     */
    public static void printOneThousandThreadForPool() {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            //提交任务
            THREADPOOL.submit(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalTest().date2(finalI);
                    System.out.println(date);
                }
            });
        }
        THREADPOOL.shutdown();
    }

    public String date2(int seconds) {
        //参数的单位是毫秒，从1970.1.1 00:00:00 GMT 开始计时
        Date date = new Date(1000 * seconds);
        return DATEFORMAT.format(date);
    }


    /**
     * 一千个线程打印日期 用线程池来执行
     */
    public static void printOneThousandThread() {
        //ExecutorService threadPool = Executors.newFixedThreadPool(10);

        //使用第二种方法创建线程池
        int corePoolSize = 2;
        int maximumPoolSize = 4;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
        ThreadFactory threadFactory = new NameTreadFactory();
        RejectedExecutionHandler handler = new MyIgnorePolicy();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue, threadFactory, handler);
        // 预启动所有核心线程
        threadPool.prestartAllCoreThreads();
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            //提交任务
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalTest().date(finalI);
                    System.out.println(date);
                }
            });
        }
        //threadPool.shutdown();

    }

    static class NameTreadFactory implements ThreadFactory {
        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "my-thread-" + mThreadNum.getAndIncrement());
            System.out.println(t.getName() + " has been created");
            return t;
        }
    }

    public static class MyIgnorePolicy implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            doLog(r, e);
        }

        private void doLog(Runnable r, ThreadPoolExecutor e) {
            // 可做日志记录等
            System.err.println(r.toString() + " rejected");
//          System.out.println("completedTaskCount: " + e.getCompletedTaskCount());
        }
    }


    /**
     * 三十个线程打印日期
     */
    public static void printThirtyThread() {
        for (int i = 0; i < 30; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalTest().date(finalI);
                    System.out.println(date);
                }
            }).start();

            //线程启动后，休眠100ms
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 两个线程打印日期
     */
    public static void printTwoThread() {
        //线程一
        new Thread(new Runnable() {
            @Override
            public void run() {
                String date = new ThreadLocalTest().date(10);
                System.out.println(date);
            }
        }).start();

        //线程二
        new Thread(new Runnable() {
            @Override
            public void run() {
                String date = new ThreadLocalTest().date(104707);
                System.out.println(date);
            }
        }).start();
    }


    /**
     * 格式化日期
     *
     * @param seconds
     * @return
     */
    public String date(int seconds) {
        //参数的单位是毫秒,从1970.1.1 00:00:00 GMT 开始计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }
}
