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
 * 6.必须告诉Spring容器哪个类是切面类(给切面类上加一个注解：@Aspect)
 * 7.给配置类中加 @EnableAspectJAutoProxy 【开启基于注解的aop模式】
 *      在Spring中很多的 @EnableXXX
 *
 *
 * 加入AOP后执行顺序：执行前置通知---》目标方法---》后置通知---》返回通知(没异常)
 *
 *
 * 三步：
 *      1.将业务逻辑组件和切面类都加入到容器中，告诉Spring哪个是切面类@Aspect
 *      2.在切面类上的每一个通知方法上标注通知注解，告诉Spring何时何地运行(切入点表达式)
 *      3.开启基于注解的AOP模式(配置文件方式)：@EnableAspectJAutoProxy
 *
 *
 * JoinPoint：内部可以获取到方法名【joinPoint.getSignature().getName()】、
 *           参数列表【Object[] args = joinPoint.getArgs();】、
 *           获取返回值【切面的返回方法添加参数 Object result】、
 *           获取异常信息【切面的异常方法添加参数 Exception exception】
 *
 *
 * AspectJAutoProxyRegistrar#registerBeanDefinitions 这个方法上打上断点就能可以看到调试效果了
 *
 * AOP原理：【看给容器中注册了什么组件，这个组件什么时候工作，这个组件的功能是什么】
 *      @EnableAspectJAutoProxy入口：
 *
 *  1.@EnableAspectJAutoProxy是什么?
 *      @Import(AspectJAutoProxyRegistrar.class)：给容器中导入 AspectJAutoProxyRegistrar  (测试debug流程可以在 AspectJAutoProxyRegistrar#registerBeanDefinitions 打个方法断点)
 *          利用AspectJAutoProxyRegistrar自定义给容器中注册bean  (AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(registry);//注册registerAspectJAnnotationAutoProxyCreator类如果需要的话)
 *
 *          //具体参考源码方法：org.springframework.aop.config.AopConfigUtils#registerOrEscalateApcAsRequired(java.lang.Class, org.springframework.beans.factory.support.BeanDefinitionRegistry, java.lang.Object)
 *          //beanDefinition = org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator
 *          //registry.registerBeanDefinition("org.springframework.aop.config.internalAutoProxyCreator", beanDefinition);
 *          //得出以下结论：
 *          internalAutoProxyCreator = AnnotationAwareAspectJAutoProxyCreator
 *
 *      给容器中注册一个AnnotationAwareAspectJAutoProxyCreator   注解装配模式的自动代理创建器
 *
 *
 * AnnotationAwareAspectJAutoProxyCreator继承关系如下：
 * 2. AnnotationAwareAspectJAutoProxyCreator
 *      AnnotationAwareAspectJAutoProxyCreator
 *          ->AspectJAwareAdvisorAutoProxyCreator
 *              ->AbstractAdvisorAutoProxyCreator
 *                  ->AbstractAutoProxyCreator
 *                  ->AbstractAutoProxyCreator extends ProxyProcessorSupport implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware {
 *
 *                  关注后置处理器（在bean初始化完成前后做事情，自动装配BeanFactory）
 *
 *
 * 抽象父类：
 *   AbstractAutoProxyCreator#setBeanFactory
 *   AbstractAutoProxyCreator#postProcessBeforeInstantiation  有后置处理器逻辑
 *
 * 重写了 setBeanFactory方法并且还会调用 initBeanFactory方法
 *   AbstractAdvisorAutoProxyCreator#setBeanFactory ===》initBeanFactory
 *
 *   AspectJAwareAdvisorAutoProxyCreator无处理
 *
 *   AnnotationAwareAspectJAutoProxyCreator#initBeanFactory
 *
 * debug打断点测试：（方法断点）
 *     AnnotationAwareAspectJAutoProxyCreator#initBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
 *     AbstractAdvisorAutoProxyCreator#setBeanFactory(org.springframework.beans.factory.BeanFactory)
 *
 *
 *  流程：
 *      1.传入主配置类MyConfigOfAOP,创建IOC容器,
 *      2.注册配置类,调用refresh() 刷新容器就是要把容器中的所有bean创建出来,包括所有功能  (org.springframework.context.support.AbstractApplicationContext#refresh()---》registerBeanPostProcessors(beanFactory);)
 *      3.registerBeanPostProcessor(beanFactory);注册bean的后置处理器来方便拦截bean的创建
 *          1.先获取IOC容器已经定义了需要创建对象的所有BeanPostProcessor
 *             org.springframework.context.support.PostProcessorRegistrationDelegate#registerBeanPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory, org.springframework.context.support.AbstractApplicationContext)
 *                  //所有后置处理器
 *                  String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false);
 *
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
                             *   private void invokeAwareMethods(final String beanName, final Object bean) {
                             * 		if (bean instanceof Aware) {
                             * 			if (bean instanceof BeanNameAware) {
                             * 				((BeanNameAware) bean).setBeanName(beanName);
                             * 			}
                             * 			if (bean instanceof BeanClassLoaderAware) {
                             * 				ClassLoader bcl = getBeanClassLoader();
                             * 				if (bcl != null) {
                             * 					((BeanClassLoaderAware) bean).setBeanClassLoader(bcl);
                             * 				}
                             * 			}
                             * 			if (bean instanceof BeanFactoryAware) {
                             * 				((BeanFactoryAware) bean).setBeanFactory(AbstractAutowireCapableBeanFactory.this);
                             * 			}
                             * 		}
                             * 	}
 *
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
