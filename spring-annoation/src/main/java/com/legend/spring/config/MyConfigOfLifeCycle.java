package com.legend.spring.config;

import com.legend.spring.bean.Car;
import com.legend.spring.bean.Cat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * bean的生命周期
 * bean创建---初始化---销毁的过程
 * <p>
 * 容器管理bean的生命周期
 * 可以自定义初始化和销毁方法;容器在bean进行到当前生命周期的时候来调用我们自定义的初始化和销毁
 *
 * BeanPostProcessor.postProcessBeforeInitialization
 *
 * 遍历得到容器中所有的 BeanPostProcessor,逐个执行beforeInitialization
 * 一旦返回null跳出for循环不会执行后面的BeanProcessor.postProcessBeforeInitialization(result, beanName);
 *
 * BeanPostProcessor的原理
 *     //org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#doCreateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
 *     //AbstractAutowireCapableBeanFactory#doCreateBean的方法内  populateBean(beanName, mbd, instanceWrapper);//给bean进行属性赋值
 * 	        initializeBean(beanName, exposedObject, mbd);方法内部处理
 *              wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
 *              invokeInitMethods(beanName, wrappedBean, mbd);//执行自定义初始化方法
 *              wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
 *
 * 构造(对象创建)：
 *      单实例：在容器启动的时候创建对象
 *      多实例：在每次获取的时候创建对象
 *
 *  初始化：
 *      对象创建完成,并赋值好,调用初始化方法....
 *
 *  销毁：
 *      单实例：容器关闭的时候
 *      多实例：容器不会管理这个bean,容器不会调用销毁方法
 *
 *
 * 1.指定初始化和销毁方法：
 *      通过@Bean注解指定init-method和 destroy-method
 *
 * 2.通过让bean实现 InitializingBean(定义初始化逻辑),
 *                  DisposableBean(定义销毁逻辑)
 *
 * 3.可以使用JSR250
 *      @PostConstruct：在bean创建完成并且属性赋值完成；来执行初始化方法
 *      @PreDestroy：在容器销毁bena之前通知我们进行清理工作
 *
 *
*  4.BeanPostProcessor[Interface]：bean的后置处理器;
 *  在bean初始化前后进行一些处理工作
 *  postProcessBeforeInitialization：在初始化之前工作
 *  postProcessAfterInitialization：在初始化之后
 *
 *
 * Spring底层对BeanPostProcessor的使用：
 *      bean赋值,注入其他组件,@Autowire,生命周期注解功能,@Async,xxx BeanPostProcessor;
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/3
 */
@ComponentScan({"com.legend.spring.bean"})
@Configuration
public class MyConfigOfLifeCycle {

    //@Scope("prototype")
    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public Car car() {
        return new Car();
    }

    @Bean
    public Cat cat() {
        return new Cat();
    }

    @Bean
    public MyBeanPostProcessor myBeanPostProcessor() {
        return new MyBeanPostProcessor();
    }
}
