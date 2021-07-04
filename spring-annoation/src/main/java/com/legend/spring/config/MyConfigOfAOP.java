package com.legend.spring.config;

import com.legend.spring.aop.LogAspect;
import com.legend.spring.aop.MathCalculator;
import com.legend.spring.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 配置类=配置文件
 *
 * AOP：【动态代理】
 *      指在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的编程方式
 *
 *
 * 1.导入aop模块：Spring AOP：(spring-aspects)
 * 2.定义一个业务逻辑类MathCalculator,在业务逻辑运行的时候将日志进行打印(方法之前、方法运行结束、方法出现异常、xxx)
 * 3.定义一个日志切面：切面类里面的方法需要动态感知MathCalculator.div运行到哪里然后执行
 *      通和方法：
 *          前置通知(@Before)：logStart：目标方法div运行之前运行
 *          后置通知(@After)：logEnd：目标方法div运行结束之后运行 无论方法正常结束还是异常结束都执行
 *          返回通知(@AfterReturning)：logReturn：目标方法div正常返回之后运行
 *          异常通知(@AfterThrowing)：logException：目标方法div出现异常之后运行
 *          环绕通知(@Around)：动态地阿里，手动推进目标方法(joinPoint.proceed())
 *
 * 4.给切面类的目标方法标注何时何地运行(通知注解)
 * 5.将切面类和业务逻辑类(目标方法所在类)都加入到容器中
 * 6.必须告诉Spring容器哪个类是切面类
 * 7.给配置类中加 @EnableAspectJAutoProxy 【开启基于注解的aop模式】
 *      在Spring中很多的 @EnableXXX
 *
 * 三步：
 *      1.将业务逻辑组件和切面类都加入到容器中，告诉Spring哪个是切面类@Aspect
 *      2.在切面类上的每一个通知方法上标注通知注解，告诉Spring何时何地运行(切入点表达式)
 *      3.开启基于注解的AOP模式：@EnableAspectJAutoProxy
 *
 *
 * AOP原理：【看给容器中注册了什么组件，这个组件什么时候工作，这个组件的功能是什么】
 *      @EnableAspectJAutoProxy入口：
 *
 *  1.@EnableAspectJAutoProxy是什么?
 *      @Import(AspectJAutoProxyRegistrar.class)：给容器中导入 AspectJAutoProxyRegistrar
 *          利用AspectJAutoProxyRegistrar自定义给容器中注册bean  (AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(registry);)
 *          internalAutoProxyCreator = AnnotationAwareAspectJAutoProxyCreator
 *
 *      给容器中注册一个AnnotationAwareAspectJAutoProxyCreator   注解装配模式的自动代理创建器
 *
 * 2. AnnotationAwareAspectJAutoProxyCreator
 *      AnnotationAwareAspectJAutoProxyCreator
 *          ->AspectJAwareAdvisorAutoProxyCreator
 *              ->AbstractAdvisorAutoProxyCreator
 *                  ->AbstractAutoProxyCreator
 *                  ->AbstractAutoProxyCreator extends ProxyProcessorSupport implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware {
 *
 *                  关注后置处理器（在bean初始化完成前后做事情，自动装配BeanFactory）
 *
 *   AbstractAutoProxyCreator#setBeanFactory
 *   AbstractAutoProxyCreator#postProcessBeforeInstantiation  有后置处理器逻辑
 *
 *   AbstractAdvisorAutoProxyCreator#setBeanFactory ===》initBeanFactory
 *
 *   AnnotationAwareAspectJAutoProxyCreator#initBeanFactory
 *
 *  流程：
 *      1.传入主配置类MyConfigOfAOP,创建IOC容器,
 *      2.注册配置类,调用refresh() 刷新容器
 *      3.registerBeanPostProcessor(beanFactory);注册bean的后置处理器来方便拦截bean的创建
 *          1.先获取IOC容器已经定义了需要创建对象的所有BeanPostProcessor
 *          2.给容器中加别的BeanPostProcessor
 *          3.优先注册实现了PriorityOrdered接口的BeanPostProcessor
 *          4.再给容器中注册实现了Ordered接口的BeanPostProcessor
 *          5.注册没有实现优先级的BeanPostProcessor
 *          6.注册BeanPostProcessor，实际上就是创建BeanPostProcessor对象，保存在容器中
 *
 *             创建internalAutoProxyCreator的 BeanPostProcessor【AnnotationAwareAspectJAutoProxyCreator】
 *                  1.创建Bean的实例
 *                  2.populateBean：给Bean的各种属性赋值
 *                  3.initializeBean：初始化Bean
 *                      1.invokeAwareMethods()：处理Aware接口的方法回调
 *                      2.applyBeanPostProcessorBeforeInitialization()：应用后置处理器的postProcessorBeforeInitialization
 *                      3.invokeInitMethods()：执行自定义初始化的方法
 *                      4.applyBeanPostProcessorAfterInitialization()：应用后置处理器的postProcessorAfterInitialization
 *
 *                  4.BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)创建成功---》aspectJAdvisorsBuilder
 *          7.把BeanPostProcessor注册到BeanFactory中;
 *               beanFactory.addBeanPostProcessor(postProcessor);
 *
 * ====================以上是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程====================
 *
 *              AnnotationAwareAspectJAutoProxyCreator===>  InstantiationAwareBeanPostProcessor
 *
 *      4.finishBeanFactoryInitlization(beanFactory);完成BeanFactory工作;创建剩下的单实例bean
 *          1.遍历获取容器中所有的bean,依次创建对象getBean(beanName);
 *              getBean--->doGetBean--->getSingleton()
 *          2.创建Bean
 *              1.先从缓存中获取当前Bean,如果能获取到，说明bean是之前被创建过的,直接使用，否则再创建
 *              2.createBean();创建Bean
 *                  1.resolveBeforeInstantiation(beanName,mdbToUser);解析BeforeInstantiation
 *                    希望后置处理器在此能返回代理对象，如果能返回代理对象就使用，如果不能就继续
 *                    1.后置处理器先尝试返回对象
 *                  2.doCreateBean(BeanName,mdbToUse,agrs);真正的去创建Bean实例和3.6流程一样
 *
 *
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/4
 */
@EnableAspectJAutoProxy
@Configuration
public class MyConfigOfAOP {

    /**
     * 业务逻辑类和切面类加入到容器中
     *
     * @return
     */
    @Bean
    public MathCalculator mathCalculator() {
        return new MathCalculator();
    }

    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }
}
