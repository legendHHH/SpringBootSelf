package com.example.demo.java8;

import com.example.demo.java8.entity.Dish;
import com.example.demo.java8.entity.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * StreamAPI的汇总
 *
 * @author legend
 */
public class StreamApiDemo {

    private static Logger log = LoggerFactory.getLogger(StreamApiDemo.class);

    /**
     * 创建菜肴对象
     */
    private static List<Dish> DISHESLIST = new ArrayList<>();

    /**
     * 创建菜肴集合对象
     *
     * @return
     */
    private static List<Dish> createDishes() {
        Type type = new Type(1L, "类型1");
        Type type2 = new Type(2L, "类型2");
        for (int i = 0; i <= 5; i++) {
            Dish d = new Dish();
            d.setCalories(398 + i);
            d.setName("菜肴" + i);
            d.setVegetarian(true);
            if (i % 2 == 0) {
                d.setType(type);
            } else {
                d.setType(type2);
            }
            DISHESLIST.add(d);
        }
        return DISHESLIST;

    }


    /**
     * Java7之前的比较
     *
     * @param dishList
     * @return
     */
    private static List<String> beforeJava7(List<Dish> dishList) {
        List<Dish> lowCaloricDishes = new ArrayList<>();
        //1.筛选出卡路里小于400的菜肴
        for (Dish dish : dishList) {
            if (dish.getCalories() < 400) {
                lowCaloricDishes.add(dish);
            }
        }
        //2.筛选出的菜肴进行排序
        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return Integer.compare(o1.getCalories(), o2.getCalories());
            }
        });
        //3.获取排序后菜肴的名字
        List<String> lowCaloricDishesName = new ArrayList<>();
        for (Dish d : lowCaloricDishes) {
            lowCaloricDishesName.add(d.getName());
        }
        return lowCaloricDishesName;
    }


    /**
     * Java8之后的比较方式
     *
     * @param dishList
     * @return
     */
    private static List<String> afterJava8(List<Dish> dishList) {
        return dishList.stream()
                //筛选出卡路里小于400的菜肴
                .filter(d -> d.getCalories() < 400)
                //根据卡路里排序
                .sorted(Comparator.comparing(Dish::getCalories))
                //提取菜肴的名字
                .map(Dish::getName)
                .collect(Collectors.toList());
    }


    /**
     * 对数据库查询到的菜肴根据菜肴种类进行分类
     *
     * @param dishList
     * @return
     */
    private static Map<Type, List<Dish>> beforeJdk8(List<Dish> dishList) {
        Map<Type, List<Dish>> result = new HashMap<>(16);

        for (Dish dish : dishList) {
            //不存在则初始化
            if (result.get(dish.getType()) == null) {
                List<Dish> dishes = new ArrayList<>();
                dishes.add(dish);
                result.put(dish.getType(), dishes);
            } else {
                //存在则追加
                result.get(dish.getType()).add(dish);
            }
        }
        return result;
    }


    /**
     * jdk8之后的
     *
     * @param dishList
     * @return
     */
    private static Map<Type, List<Dish>> afterJdk8(List<Dish> dishList) {
        return dishList.stream().collect(groupingBy(Dish::getType));
    }


    /**
     * 生成流的方式
     */
    public static void getStreamTest() {
        List<Integer> integerList = Arrays.asList(1, 2, 4, 6, 7);
        Stream<Integer> integerStream = integerList.stream();
        System.out.println("通过集合生成数值流...." + integerStream);

        int[] intArr = new int[]{1, 2, 3, 4};
        IntStream intStream = Arrays.stream(intArr);
        System.out.println("通过数组生成数值流...." + intStream);

        Stream<Integer> integerValueStream = Stream.of(1, 2, 3, 4);
        //通过Stream的empty方法可以生成一个空流
        Stream<Object> empty = Stream.empty();
        System.out.println("通过值生成数值流...." + integerValueStream);

        try {
            //通过Files.line方法得到一个流,并且得到的每个流是给定文件中的一行
            Stream<String> fileLinesStream = Files.lines(Paths.get("D:/data.txt"), Charset.defaultCharset());
            System.out.println("通过文件生成流...." + fileLinesStream);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //iterate方法接受两个参数,第一个为初始化值,第二个为进行的函数操作,因为iterator生成的流为无限流,通过limit方法对流进行了截断,只生成5个偶数
        Stream<Integer> limitIterateStream = Stream.iterate(0, n -> n + 2).limit(5);
        System.out.println("通过iterate静态方法生成流...." + limitIterateStream);

        //generate方法接受一个参数,方法参数类型为Supplier,由它为流提供值.generate生成的流也是无限流,因此通过limit对流进行了截断
        Stream<Double> limitGenerateStream = Stream.generate(Math::random).limit(5);
        System.out.println("通过generate静态方法生成流...." + limitGenerateStream);
    }


    /**
     * filter筛选
     * distinct去除重复元素
     */
    public static void filterAndDistinctTest() {
        List<Integer> integerList = Arrays.asList(1, 1, 2, 3, 4, 5, 5, 6, 8, 9, 9);
        Stream<Integer> streamFilter = integerList.stream().distinct().filter(i -> i > 3);
        log.info("StreamFilterDistinct.....{}", streamFilter.collect(Collectors.toList()));

        Stream<Integer> streamDistinct = integerList.stream().distinct();
        log.info("Stream...streamDistinct{}", streamDistinct.collect(Collectors.toList()));
    }

    /**
     * limit返回指定流个数
     */
    public static void limitTest() {
        List<Integer> integerList = Arrays.asList(1, 1, 2, 3, 4, 5);
        Stream<Integer> stream = integerList.stream().limit(3);
        System.out.println(stream.collect(Collectors.toList()));
    }

    /**
     * skip跳过流中的元素
     */
    public static void skipTest() {
        List<Integer> integerList = Arrays.asList(1, 1, 2, 3, 4, 5);
        Stream<Integer> stream = integerList.stream().skip(2);
        System.out.println(stream.collect(Collectors.toList()));
    }

    /**
     * map流映射-->所谓流映射就是将接受的元素映射成另外一个元素
     */
    public static void mapTest() {
        List<String> stringList = Arrays.asList("Java 8", "Lambdas", "In", "Action");
        Stream<Integer> stream = stringList.stream().map(String::length);
        System.out.println(stream.collect(Collectors.toList()));
    }

    /**
     * flatMap流转换-->将一个流中的每个值都转换为另一个流
     */
    public static void flatMapTest() {
        List<String> wordList = Arrays.asList("Hello", "World");
        List<String> stringList = wordList.stream()
                .map(w -> w.split(" "))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(stringList);

    }

    /**
     * allMatch匹配所有
     */
    public static void allMatchTest() {
        List<Integer> integerList = Arrays.asList(10, 9, 7, 4, 5);
        List<Integer> integerList2 = Arrays.asList(1, 1, 3, 4, 5);
        if (integerList.stream().allMatch(i -> i > 3)) {
            System.out.println("integerList值都大于3");
        }

        if (integerList2.stream().allMatch(i -> i > 3)) {
            System.out.println("值都大于3");
        }
    }

    /**
     * anyMatch匹配其中一个
     */
    public static void anyMatchTest() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
        if (integerList.stream().anyMatch(i -> i > 3)) {
            System.out.println("存在大于3的值");
        }
    }

    /**
     * noneMatch全部不匹配
     */
    public static void noneMatchTest() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
        if (integerList.stream().noneMatch(i -> i > 3)) {
            System.out.println("值都小于3");
        }
    }

    /**
     * 统计流中元素个数----通过count
     */
    public static void countTest() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
        Long result = integerList.stream().count();
        System.out.println("长度为:" + result);
    }

    /**
     * 统计流中元素个数----通过counting
     */
    public static void countingTest() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
        Long result = integerList.stream().collect(counting());
        System.out.println("长度为:" + result);
    }

    /**
     * findFirst查找第一个
     */
    public static void findFirstTest() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
        //查找第一个大于3的数字
        Optional<Integer> first = integerList.stream().filter(i -> i > 3).findFirst();
        System.out.println("第一个大于三的元素为:" + first.get());
    }

    /**
     * findAny随机查找一个
     */
    public static void findAnyTest() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> first = integerList.stream().filter(i -> i > 3).findAny();
        System.out.println("随机一个大于三的元素为:" + first.get());
    }

    /**
     * reduce将流中的元素组合起来
     * 对一个集合中的值进行求和
     */
    public static void reduceTest() {
        List<Integer> integerList = new ArrayList<>(Arrays.asList(1, 13, 2, 3, 34, 42));
        //0 表示从0开始计算
        Integer sum = integerList.stream().reduce(0, (a, b) -> (a + b));
        System.out.println("集合求和:" + sum);

        Optional<Integer> reduceSum = integerList.stream().reduce(Integer::sum);
        System.out.println("简化集合求和2:" + reduceSum);
    }

    /**
     * 通过min/max获取最小最大值
     */
    public static void minAndMaxTest() {
        List<Dish> dishes = createDishes();
        System.out.println(dishes.toString());

        //第一种写法
        Optional<Integer> min = dishes.stream().map(Dish::getCalories).min(Integer::compareTo);
        Optional<Integer> max = dishes.stream().map(Dish::getCalories).max(Integer::compareTo);
        System.out.println("min:" + min.get());
        System.out.println("max:" + max.get());

        //第二种写法
        OptionalInt min1 = dishes.stream().mapToInt(Dish::getCalories).min();
        OptionalInt max1 = dishes.stream().mapToInt(Dish::getCalories).max();
        System.out.println("min1:" + min1);
        System.out.println("max1:" + max1);
    }


    /**
     * 通过minBy/maxBy获取最小最大值
     */
    public static void minByAndMaxByTest() {
        List<Dish> dishes = createDishes();
        Optional<Integer> minBy = dishes.stream().map(Dish::getCalories).collect(minBy(Integer::compareTo));
        Optional<Integer> maxBy = dishes.stream().map(Dish::getCalories).collect(maxBy(Integer::compareTo));
        System.out.println("minBy:" + minBy.get());
        System.out.println("maxBy:" + maxBy.get());
    }


    /**
     * comparator
     * 通过reduce获取最小最大值
     */
    public static void comparatorReduceMinAndMaxTest() {
        List<Dish> dishes = createDishes();
        Optional<Integer> min = dishes.stream().map(Dish::getCalories).reduce(Integer::min);
        Optional<Integer> max = dishes.stream().map(Dish::getCalories).reduce(Integer::max);
        System.out.println("min:" + min);
        System.out.println("max:" + max);
    }

    /**
     * 求和----通过summingInt
     */
    public static void summingIntTest() {
        List<Dish> dishes = createDishes();
        int sum = dishes.stream().collect(summingInt(Dish::getCalories));
        System.out.println(sum);
    }

    /**
     * 求和----通过reduce
     */
    public static void reduceSummingIntTest() {
        List<Dish> dishes = createDishes();
        int sum = dishes.stream().map(Dish::getCalories).reduce(0, Integer::sum);
        System.out.println("reduceSum:" + sum);
    }

    /**
     * 求和----通过sum
     */
    public static void sumTest() {
        List<Dish> dishes = createDishes();
        int sum = dishes.stream().mapToInt(Dish::getCalories).sum();
        System.out.println("Sum:" + sum);
    }

    /**
     * 通过averagingInt求平均值
     */
    public static void averagingIntTest() {
        List<Dish> dishes = createDishes();
        Double average = dishes.stream().collect(averagingInt(Dish::getCalories));
        System.out.println(average);
    }

    /**
     * 通过summarizingInt同时求总和、平均值、最大值、最小值
     */
    public static void summarizingIntTest() {
        List<Dish> dishes = createDishes();
        //求总和
        IntSummaryStatistics intSummaryStatistics = dishes.stream().collect(summarizingInt(Dish::getCalories));
        //获取平均值
        double average = intSummaryStatistics.getAverage();
        //获取最大值
        int max = intSummaryStatistics.getMax();
        //获取最小值
        int min = intSummaryStatistics.getMin();
        //获取总和
        long sum = intSummaryStatistics.getSum();

        log.info("intSummaryStatistics:{}....平均值:{}...最大值:{}...最小值:{}....总和:{}....", intSummaryStatistics, average, max, min, sum);
    }

    /**
     * 通过foreach进行元素遍历
     */
    public static void foreachTest() {
        List<Integer> integerList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        integerList.stream().forEach(System.out::println);
    }

    /**
     * 返回集合
     */
    public static void returnCollectionTest() {
        List<Dish> dishes = createDishes();
        List<String> nameList = dishes.stream().map(Dish::getName).collect(toList());
        Set<String> nameSet = dishes.stream().map(Dish::getName).collect(toSet());
        nameList.stream().forEach(System.out::print);

        System.out.println();
        nameSet.stream().forEach(System.out::print);
    }

    /**
     * 通过joining拼接流中的元素
     */
    public static void joiningTest() {
        List<Dish> dishes = createDishes();
        String result = dishes.stream().map(Dish::getName).collect(joining(","));
        System.out.println("拼接后的结果是:" + result);
    }

    /**
     * 通过groupingBy进行分组
     */
    public static void groupingByTest() {
        List<Dish> dishes = createDishes();
        Map<Type, List<Dish>> groupBy = dishes.stream().collect(groupingBy(Dish::getType));
        System.out.println(groupBy);


        //多级分类
        /*Map<Type, List<Dish>> result = dishes.stream().collect(groupingBy(Dish::getType,
                groupingBy(dish -> {
                    if (dish.getCalories() <= 400) {
                        return CaloricLevel.DIET;
                    } else if (dish.getCalories() <= 700) {
                        return CaloricLevel.NORMAL;
                    } else {
                        return CaloricLevel.FAT;
                    }
                })));*/
    }


    /**
     * 通过partitioningBy进行分区
     */
    public static void partitioningByTest() {
        List<Dish> dishes = createDishes();
        //分区是特殊的分组，它分类依据是true和false，所以返回的结果最多可以分为两组
        Map<Boolean, List<Dish>> partitioningByMap = dishes.stream().collect(partitioningBy(Dish::isVegetarian));
        System.out.println(partitioningByMap);

        //等价于
        Map<Boolean, List<Dish>> groupingByMap = dishes.stream().collect(groupingBy(Dish::isVegetarian));
        System.out.println(groupingByMap);

        //例子比较
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
        Map<Boolean, List<Integer>> result = integerList.stream().collect(partitioningBy(i -> i < 3));
        System.out.println(result);
    }


    public static void main(String[] args) {
        //创建集合
        List<Dish> dishList = createDishes();

        //语法比较7和8
        List<String> beforeJava7 = beforeJava7(dishList);
        List<String> afterJava8 = afterJava8(dishList);
        log.info("Java7.....{}", beforeJava7);
        log.info("Java8.....{}", afterJava8);

        //groupingBy 分组函数的使用
        Map<Type, List<Dish>> typeListMap1 = afterJdk8(dishList);
        Map<Type, List<Dish>> typeListMap2 = beforeJdk8(dishList);
        System.out.println("typeListMap1..." + typeListMap1);
        System.out.println("typeListMap2..." + typeListMap2);


        ////////////////////////////////////////中间操作start////////////////////////////////////////

        /////////////////////流的使用start/////////////////////
        //生成流的方式
        getStreamTest();
        //filter过滤和distinct去重方法
        filterAndDistinctTest();
        //limit返回指定流个数
        limitTest();
        //跳过流中的元素
        skipTest();
        //map流映射-->所谓流映射就是将接受的元素映射成另外一个元素
        mapTest();
        //flatMap流转换-->将一个流中的每个值都转换为另一个流
        flatMapTest();
        /////////////////////流的使用end/////////////////////


        /////////////////////元素匹配start/////////////////////
        //allMatch匹配所有
        allMatchTest();
        //anyMatch匹配其中一个
        anyMatchTest();
        //noneMatch全部不匹配
        noneMatchTest();
        /////////////////////元素匹配end/////////////////////

        ////////////////////////////////////////中间操作end////////////////////////////////////////


        ////////////////////////////////////////终端操作start////////////////////////////////////////

        /////////////////////统计流中元素个数start/////////////////////
        //计算流中的个数方法1----通过count
        countTest();
        //计算流中的个数方法2----通过counting
        countingTest();

        /////////////////////统计流中元素个数end/////////////////////

        /////////////////////统计流中元素个数start/////////////////////
        //查找第一个出现字符
        findFirstTest();
        //随机查找一个
        findAnyTest();
        /////////////////////统计流中元素个数end/////////////////////


        //集合求和
        reduceTest();

        /////////////////////获取流中元素最大值和最小值start/////////////////////
        //获取最小最大值
        minAndMaxTest();
        //获取最小最大值minBy
        minByAndMaxByTest();
        //通过reduce获取最大最小值
        comparatorReduceMinAndMaxTest();
        /////////////////////获取流中元素最大值和最小值end/////////////////////


        /////////////////////求和start/////////////////////
        //求和--通过summingInt
        summingIntTest();
        //求和--通过reduce
        reduceSummingIntTest();
        //通过sum
        sumTest();
        /////////////////////求和end/////////////////////


        //通过averagingInt求平均值
        averagingIntTest();

        //通过summarizingInt同时求总和、平均值、最大值、最小值
        summarizingIntTest();

        //foreach遍历元素
        foreachTest();

        //返回集合
        returnCollectionTest();

        //通过joining拼接流中的元素
        joiningTest();

        //通过groupingBy进行分组
        groupingByTest();

        //通过partitioningBy进行分区
        partitioningByTest();
        ////////////////////////////////////////终端操作end/////////////////////////////////////////

    }
}
