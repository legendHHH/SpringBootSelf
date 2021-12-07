package com.qcl.springsecurity.config;

//import com.qcl.springsecurity.MyPasswordEncoder;

//import com.qcl.springsecurity.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * SpringSecurityConfig
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/21
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    //@Autowired
    //private MyUserService myUserService;

    /**
     * 可以快速搭建安全认证功能
     * （CASE：只要能登录即可，指定用户名和密码）
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //基于内存的验证(即在内存中创建虚拟用户不在数据库中创建)
        /*auth.inMemoryAuthentication().withUser("admin").password("123456").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("zhangsan").password("zhangsan").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("demo").password("demo").roles("USER");*/

        //替换成数据库管理用户
        //auth.userDetailsService(myUserService).passwordEncoder(new MyPasswordEncoder());

        //SpringSecurity默认的数据库验证
        //auth.jdbcAuthentication().usersByUsernameQuery("").authoritiesByUsernameQuery("").passwordEncoder(new MyPasswordEncoder());
    }

    /**
     * 决定哪些请求要被拦截
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //配置需要登录验证
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().permitAll()
                .and()
                .formLogin();

        //配置不需要登录验证
        /*http.authorizeRequests()
                .anyRequest().permitAll().and().logout().permitAll();*/

        //关闭默认得csrf认证
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //忽略掉静态资源
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }
}
