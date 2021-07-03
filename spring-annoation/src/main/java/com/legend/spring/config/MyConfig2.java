package com.legend.spring.config;

import com.legend.spring.bean.Color;
import com.legend.spring.bean.ColorFactoryBean;
import com.legend.spring.bean.Person;
import com.legend.spring.bean.Red;
import com.legend.spring.condition.LinuxCondition;
import com.legend.spring.condition.MyImportBeanDefinitionRegistrar;
import com.legend.spring.condition.MyImportSelector;
import com.legend.spring.condition.WindowsCondition;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;

/**
 * 配置类=配置文件
 *
 * @Scope 调整Bean的作用域
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/3
 */
//类中组件统一设置。满足当前条件,这个类中配置的所有bean注册才能生效
@Conditional({WindowsCondition.class})
@Configuration
//快速导入组件 id
@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
@ComponentScan(value = "com.legend.spring",
        //excludeFilters = Filter[],指定扫描的时候按照规则排除那些组件
        /*excludeFilters = {
                @Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
        })*/
        //includeFilters = Filter[],指定扫描的时候只需要包含那些组件
        includeFilters = {
                /*@Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class}),
                @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookDao.class})*/
                @Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
        }, useDefaultFilters = false)
//FilterType.ANNOTATION,按照注解
//FilterType.ASSIGNABLE_TYPE,按照给定的类型
//FilterType.ASPECTJ,不常用
//FilterType.REGEX,按照正则表达式
//FilterType.CUSTOM,按照自定义
public class MyConfig2 {

    /**
     * 给容器中注册一个Bean;类型为返回值的类型,id默认是方法名作为id
     * 默认是单实例
     * @Scope 调整Bean的作用域
     * <p>
     * * @see ConfigurableBeanFactory#SCOPE_PROTOTYPE prototype
     * * @see ConfigurableBeanFactory#SCOPE_SINGLETON singleton
     * * @see org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST  request
     * * @see org.springframework.web.context.WebApplicationContext#SCOPE_SESSION  session
     * <p>
     * prototype：多实例：IOC容器启动并不会去调用方法创建对象放在容器中每次获取的时候才会调用方法创建对象
     * singleton：单实例(默认的)：IOC容器启动会调用方法创建对象放到IOC容器中。以后每次获取就是直接从容器(map.get())中拿
     * request：同一次请求创建一个实例
     * session：同一个session创建一个实例
     *
     * 懒加载@Lazy：
     *      单实例bean：默认在容器启动的时候创建对象;
     *      懒加载：容器启动不创建对象。第一次使用(获取)Bean创建对象,并初始化
     *
     *
     * @return
     */
    //@Scope(value = "prototype")
    @Lazy
    @Bean
    public Person person() {
        System.out.println("给容器中添加Person.....");
        return new Person(1, "44444");
    }

    /**
     * @Conditional({})：按照一定的条件进行判断,满足条件给容器中注册bean
     * 如果是Windows，给容器中注册("bill")
     * 如果是Linux，给容器中注册("linus")
     *
     * @return
     */
    @Conditional({WindowsCondition.class})
    @Bean("bill")
    public Person person01() {
        return new Person(2, "Bill Gates比尔·盖茨");
    }

    @Conditional({LinuxCondition.class})
    @Bean("linus")
    public Person person02() {
        return new Person(3, "Linus Benedict Torvalds林纳斯·托瓦兹");
    }

    /**
     * 给容器中注册组件：
     *      1.包扫描+组件标注注解(@Controller/@Service/@Repository/@Component)
     *      2.@Bean[导入的第三方包里面的组件]
     *      3.@Import[快速给容器中导入一个组件]
     *          1.@Import(要导入到容器中的组件);容器中就会自动注册这个组件,id默认是全类名
     *          2.@ImportSelector：返回需要导入的组件的全类名数组;
     *          3.@ImportBeanDefinitionRegistrar：手动注册bean到容器中
     *
     *      4.使用Spring提供的FactoryBean(工厂Bean)
     *          1.默认获取到的是工厂bean调用getObject创建的对象
     *          2.要获取工厂Bean本身我们需要给id前面加一个 &  ("&colorFactoryBean")
     */
    @Bean
    public ColorFactoryBean colorFactoryBean(){
        return new ColorFactoryBean();
    }
}
