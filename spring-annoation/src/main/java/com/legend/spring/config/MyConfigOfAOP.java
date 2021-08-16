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
 *            //以AnnotationAwareAspectJAutoProxyCreator为例子：
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
                             * 	//接着先调用父类的setBeanFactory,在执行initBeanFactory  ===》参考 (org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator#setBeanFactory(org.springframework.beans.factory.BeanFactory))
                             * 	public void setBeanFactory(BeanFactory beanFactory) {
                             *         super.setBeanFactory(beanFactory);
                             *         if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
                             *             throw new IllegalArgumentException("AdvisorAutoProxyCreator requires a ConfigurableListableBeanFactory: " + beanFactory);
                             *         } else {
                             *             this.initBeanFactory((ConfigurableListableBeanFactory)beanFactory);
                             *         }
                             *     }
 *
 *                           //this.initBeanFactory 具体实现逻辑  接着看 4
                             *     protected void initBeanFactory(ConfigurableListableBeanFactory beanFactory) {
                             *         super.initBeanFactory(beanFactory);
                             *         if (this.aspectJAdvisorFactory == null) {
                             *             //重新包装工厂
                             *             this.aspectJAdvisorFactory = new ReflectiveAspectJAdvisorFactory(beanFactory);
                             *         }
                             *
                             *         this.aspectJAdvisorsBuilder = new AnnotationAwareAspectJAutoProxyCreator.BeanFactoryAspectJAdvisorsBuilderAdapter(beanFactory, this.aspectJAdvisorFactory);
                             *     }
 *
 *
 *                      2.applyBeanPostProcessorsBeforeInitialization()：应用后置处理器的postProcessorBeforeInitialization
 *                           //具体执行方法：
 *                              @Override
                             * 	public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
                             * 			throws BeansException {
                             *
                             * 		Object result = existingBean;
                             * 		for (BeanPostProcessor processor : getBeanPostProcessors()) {
                             * 			Object current = processor.postProcessBeforeInitialization(result, beanName);
                             * 			if (current == null) {
                             * 				return result;
                             * 			}
                             * 			result = current;
                             * 		}
                             * 		return result;
                             * 	}
 *
 *
 *                      3.invokeInitMethods()：执行自定义初始化的方法
 *
                             *      protected void invokeInitMethods(String beanName, final Object bean, @Nullable RootBeanDefinition mbd)
                             * 			throws Throwable {
                             *
                             * 		boolean isInitializingBean = (bean instanceof InitializingBean);
                             * 		if (isInitializingBean && (mbd == null || !mbd.isExternallyManagedInitMethod("afterPropertiesSet"))) {
                             * 			if (logger.isTraceEnabled()) {
                             * 				logger.trace("Invoking afterPropertiesSet() on bean with name '" + beanName + "'");
                             * 			}
                             * 			if (System.getSecurityManager() != null) {
                             * 				try {
                             * 					AccessController.doPrivileged((PrivilegedExceptionAction<Object>) () -> {
                             * 						((InitializingBean) bean).afterPropertiesSet();
                             * 						return null;
                             * 					}, getAccessControlContext());
                             * 				}
                             * 				catch (PrivilegedActionException pae) {
                             * 					throw pae.getException();
                             * 				}
                             * 			}
                             * 			else {
                             * 				((InitializingBean) bean).afterPropertiesSet();
                             * 			}
                             * 		}
                             *
                             * 		if (mbd != null && bean.getClass() != NullBean.class) {
                             * 			String initMethodName = mbd.getInitMethodName();
                             * 			if (StringUtils.hasLength(initMethodName) &&
                             * 					!(isInitializingBean && "afterPropertiesSet".equals(initMethodName)) &&
                             * 					!mbd.isExternallyManagedInitMethod(initMethodName)) {
                             * 				invokeCustomInitMethod(beanName, bean, mbd);
                             * 			}
                             * 		}
                             * 	}
 *
 *
 *
 *                      4.applyBeanPostProcessorsAfterInitialization()：应用后置处理器的postProcessAfterInitialization;
 *                           //具体执行方法：
 *                              @Override
                             * 	public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
                             * 			throws BeansException {
                             *
                             * 		Object result = existingBean;
                             * 		for (BeanPostProcessor processor : getBeanPostProcessors()) {
                             * 			Object current = processor.postProcessAfterInitialization(result, beanName);
                             * 			if (current == null) {
                             * 				return result;
                             * 			}
                             * 			result = current;
                             * 		}
                             * 		return result;
                             * 	}
 *
 *
 *                  4.BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)创建成功---》aspectJAdvisorsBuilder
                             *   protected void initBeanFactory(ConfigurableListableBeanFactory beanFactory) {
                             *         super.initBeanFactory(beanFactory);
                             *         if (this.aspectJAdvisorFactory == null) {
                             *             this.aspectJAdvisorFactory = new ReflectiveAspectJAdvisorFactory(beanFactory);
                             *         }
                             *
                             *         this.aspectJAdvisorsBuilder = new AnnotationAwareAspectJAutoProxyCreator.BeanFactoryAspectJAdvisorsBuilderAdapter(beanFactory, this.aspectJAdvisorFactory);
                             *     }
 *
 *
 *          7.把BeanPostProcessor注册到BeanFactory中;
 *            //org.springframework.context.support.PostProcessorRegistrationDelegate#registerBeanPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory, java.util.List)
 *            registerBeanPostProcessors(beanFactory, orderedPostProcessors);
 *               //核心逻辑
 *               beanFactory.addBeanPostProcessor(postProcessor);
                             *  //具体执行
                             *  private static void registerBeanPostProcessors(
                             * 			ConfigurableListableBeanFactory beanFactory, List<BeanPostProcessor> postProcessors) {
                             *
                             * 		for (BeanPostProcessor postProcessor : postProcessors) {
                             * 			beanFactory.addBeanPostProcessor(postProcessor);
                             * 		}
                             * 	}
 *
 *
 *
 *
 * ====================以上是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程====================
 *
 *              //AnnotationAwareAspectJAutoProxyCreator 的处理器类型为 InstantiationAwareBeanPostProcessor   (跟BeanPostProcessor很类似)
 *              AnnotationAwareAspectJAutoProxyCreator===>  InstantiationAwareBeanPostProcessor
 *
 *
 *      4.finishBeanFactoryInitialization(beanFactory);完成BeanFactory初始化工作;创建剩下的单实例bean
 *          //完整源码可以参考：org.springframework.context.support.AbstractApplicationContext#finishBeanFactoryInitialization(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
 *          1.遍历获取容器中所有的bean,依次创建对象getBean(beanName);
 *              //点这个方法进去 beanFactory.preInstantiateSingletons();
 *              getBean--->doGetBean--->getSingleton()
 *
 *          2.创建Bean
 *              【AnnotationAwareAspectJAutoProxyCreator会在所有bean创建之前有一个拦截,因为它是 InstantiationAwareBeanPostProcessor后置处理器,会调用postProcessBeforeInstantiation方法】
 *
 *              1.先从缓存中获取当前Bean,如果能获取到，说明bean是之前被创建过的,直接使用，否则再创建
 *                注意：只要创建好的bean都会被缓存起来
 *
 *              2.createBean();创建Bean    AnnotationAwareAspectJAutoProxyCreator会在任何bean创建之前先尝试返回bean的实例
 *                【BeanPostProcessor是在Bean对象创建完成初始化前后调用的】
 *                【InstantiationAwareBeanPostProcessor是在创建Bean实例之前先尝试用后置处理器返回对象】
 *
 *                  //org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#createBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
 *                  1.resolveBeforeInstantiation(beanName,mdbToUser);解析BeforeInstantiation
 *                     // Give BeanPostProcessors a chance to return a proxy instead of the target bean instance.
 *                     //Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
 *                    希望后置处理器在此能返回代理对象，如果能返回代理对象就使用，如果不能就继续 2.doCreateBean
 *                    1.后置处理器先尝试返回对象
 *                        //
 *                        bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
 *                           //拿到所有的后置处理器,如果是InstantiationAwareBeanPostProcessor  就执行 postProcessBeforeInstantiation方法
                             *  @Nullable
                             * 	protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
                             * 		for (BeanPostProcessor bp : getBeanPostProcessors()) {
                             * 			if (bp instanceof InstantiationAwareBeanPostProcessor) {
                             * 				InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
                             * 				Object result = ibp.postProcessBeforeInstantiation(beanClass, beanName);
                             * 				if (result != null) {
                             * 					return result;
                             * 				}
                             * 			}
                             * 		}
                             * 		return null;
                             * 	}
 *
 *
 *
 *
 *                            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
 *
 *
 *
 *
                             *  @Nullable
                             * 	protected Object resolveBeforeInstantiation(String beanName, RootBeanDefinition mbd) {
                             * 		Object bean = null;
                             * 		if (!Boolean.FALSE.equals(mbd.beforeInstantiationResolved)) {
                             * 			// Make sure bean class is actually resolved at this point.
                             * 			if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
                             * 				Class<?> targetType = determineTargetType(beanName, mbd);
                             * 				if (targetType != null) {
                             * 					bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
                             * 					if (bean != null) {
                             * 						bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
                             * 					}
                             * 				}
                             * 			}
                             * 			mbd.beforeInstantiationResolved = (bean != null);
                             * 		}
                             * 		return bean;
                             * 	}
 *
 *                  2.doCreateBean(BeanName,mdbToUse,agrs);真正的去创建Bean实例和3.6流程一样
 *                     //org.springframework.beans.factory.support.DefaultSingletonBeanRegistry#getSingleton(java.lang.String, org.springframework.beans.factory.ObjectFactory)
 *
 *
 *                           //finishBeanFactoryInitialization 处理源码
                             *    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
                             * 		// Initialize conversion service for this context.
                             * 		if (beanFactory.containsBean(CONVERSION_SERVICE_BEAN_NAME) &&
                             * 				beanFactory.isTypeMatch(CONVERSION_SERVICE_BEAN_NAME, ConversionService.class)) {
                             * 			beanFactory.setConversionService(
                             * 					beanFactory.getBean(CONVERSION_SERVICE_BEAN_NAME, ConversionService.class));
                             * 		}
                             *
                             * 		// Register a default embedded value resolver if no bean post-processor
                             * 		// (such as a PropertyPlaceholderConfigurer bean) registered any before:
                             * 		// at this point, primarily for resolution in annotation attribute values.
                             * 		if (!beanFactory.hasEmbeddedValueResolver()) {
                             * 			beanFactory.addEmbeddedValueResolver(strVal -> getEnvironment().resolvePlaceholders(strVal));
                             * 		}
                             *
                             * 		// Initialize LoadTimeWeaverAware beans early to allow for registering their transformers early.
                             * 		String[] weaverAwareNames = beanFactory.getBeanNamesForType(LoadTimeWeaverAware.class, false, false);
                             * 		for (String weaverAwareName : weaverAwareNames) {
                             * 			getBean(weaverAwareName);
                             * 		}
                             *
                             * 		// Stop using the temporary ClassLoader for type matching.
                             * 		beanFactory.setTempClassLoader(null);
                             *
                             * 		// Allow for caching all bean definition metadata, not expecting further changes.
                             * 		beanFactory.freezeConfiguration();
                             *
                             * 		// Instantiate all remaining (non-lazy-init) singletons.
                             * 		beanFactory.preInstantiateSingletons();
                             * 	}
 *
                             * 	//具体的  beanFactory.preInstantiateSingletons(); 逻辑
                             * 	@Override
                             * 	public void preInstantiateSingletons() throws BeansException {
                             * 		if (logger.isTraceEnabled()) {
                             * 			logger.trace("Pre-instantiating singletons in " + this);
                             * 		}
                             *
                             * 		// Iterate over a copy to allow for init methods which in turn register new bean definitions.
                             * 		// While this may not be part of the regular factory bootstrap, it does otherwise work fine.
                             * 		List<String> beanNames = new ArrayList<>(this.beanDefinitionNames);
                             *
                             * 		// Trigger initialization of all non-lazy singleton beans...
                             * 		for (String beanName : beanNames) {
                             * 			RootBeanDefinition bd = getMergedLocalBeanDefinition(beanName);
                             * 			if (!bd.isAbstract() && bd.isSingleton() && !bd.isLazyInit()) {
                             * 				if (isFactoryBean(beanName)) {
                             * 					Object bean = getBean(FACTORY_BEAN_PREFIX + beanName);
                             * 					if (bean instanceof FactoryBean) {
                             * 						final FactoryBean<?> factory = (FactoryBean<?>) bean;
                             * 						boolean isEagerInit;
                             * 						if (System.getSecurityManager() != null && factory instanceof SmartFactoryBean) {
                             * 							isEagerInit = AccessController.doPrivileged((PrivilegedAction<Boolean>)
                             * 											((SmartFactoryBean<?>) factory)::isEagerInit,
                             * 									getAccessControlContext());
                             * 						}
                             * 						else {
                             * 							isEagerInit = (factory instanceof SmartFactoryBean &&
                             * 									((SmartFactoryBean<?>) factory).isEagerInit());
                             * 						}
                             * 						if (isEagerInit) {
                             * 							getBean(beanName);
                             * 						}
                             * 					}
                             * 				}
                             * 				else {
                             * 					getBean(beanName);
                             * 				}
                             * 			}
                             * 		}
                             *
                             * 		// Trigger post-initialization callback for all applicable beans...
                             * 		for (String beanName : beanNames) {
                             * 			Object singletonInstance = getSingleton(beanName);
                             * 			if (singletonInstance instanceof SmartInitializingSingleton) {
                             * 				final SmartInitializingSingleton smartSingleton = (SmartInitializingSingleton) singletonInstance;
                             * 				if (System.getSecurityManager() != null) {
                             * 					AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
                             * 						smartSingleton.afterSingletonsInstantiated();
                             * 						return null;
                             * 					}, getAccessControlContext());
                             * 				}
                             * 				else {
                             * 					smartSingleton.afterSingletonsInstantiated();
                             * 				}
                             * 			}
                             * 		}
                             * 	}
 *
 *
 *
 * AnnotationAwareAspectJAutoProxyCreator 【InstantiationAwareBeanPostProcessor】的作用
 *    1.在每个bean创建之前,调用postProcessBeforeInstantiation();
 *        关心MathCalculate和LogAspect的创建
 *        1.判断当前bean是否在advisedBeans中（保存了所有需要增强的bean）
 *        2.判断当前bean是否是基础类型的Advice、PointCut、Advisor、AopInfrastructureBean、或者是否是切面
 *          或者是否是切面（@Apspect）
 *        3.是否需要跳过
 *           1.获取候选的增强器（切面里的通知方法）【list<Advisor> candidateAdvisors】
 *             每一个封装的通知方法的增强器是InstantiationModelAwarePointCutAdvisor的返回true
 *           2.永远返回false
 *
 *    2.创建对象
 *    beanPostProcessorsAfterInitialization：
 *      //包装如果需要的情况
 *      return wrapIfNecessary(bean,beanName,cacheKey);
 *
 *         1.获取当前bean的所有增强器（通知方法）Object[] specificInterceptors
 *             1.找到候选的所有的增强器（找到通知方法是需要切入当前bean方法的）
 *             2.获取到能在bean使用的增强器
 *             3.给增强器排序
 *         2.保存当前bean在advisorBean中
 *         3.如果当前bean需要增强，创建当前bean的代理对象
 *             1.获取所有增强器（通知方法）
 *             2.保存到proxyFactory
 *             3.创建代理对象：Spring自动决定
 *                  JdkDynamicAopProxy(config); jdk动态代理
 *                  ObjenesisCglibAopProxy(config);  cglib动态代理
 *         4.给容器中返回当前组件使用cglib增强了的代理对象
 *         5.以后容器中获取到的就是这个组件的代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程。
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
