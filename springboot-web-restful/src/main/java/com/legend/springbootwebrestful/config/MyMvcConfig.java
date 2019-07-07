package com.legend.springbootwebrestful.config;


import com.legend.springbootwebrestful.component.LoginHandlerInteceptor;
import com.legend.springbootwebrestful.component.MyLocaleResolver;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 扩展SpringMVC
 * 使用WebMvcConfigurerAdapter 可以来扩展SpringMVC功能
 */
//不要接管SpringMVC
@EnableWebMvc
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 添加视图映射
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //super.addViewControllers(registry);
        //添加视图映射规则(即把什么请求映射到什么页面)
        registry.addViewController("/legend").setViewName("success");//浏览器发送 /legend请求来到success页面
    }


    //所有的WebMvcConfigurerAdapter组件都会一起起作用
    @Bean //将组件注册在容器中
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                //super.addViewControllers(registry);
                //添加视图映射
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
                registry.addViewController("/main.html").setViewName("dashboard");
                registry.addViewController("/prod").setViewName("emp/product");
            }

            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                //super.addInterceptors(registry);
                //静态资源  *.css *.js   SpringBoot 已经做好了资源映射
                // /**  表示拦截任意多层路径下的任意请求
                registry.addInterceptor(new LoginHandlerInteceptor()).addPathPatterns("/**")
                        .excludePathPatterns("/index.html","/","/user/login");//排除方法：登陆页面、登陆请求排除
            }
        };
        return adapter;
    }


    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }
}
