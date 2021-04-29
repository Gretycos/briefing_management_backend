package com.briefing_management.configuration;

import com.briefing_management.configuration.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Resource
    MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Resource
    MyLogoutSuccessHandler myLogoutSuccessHandle;
    @Resource
    MyAccessDeniedHandler myAccessDeniedHandler;
    @Resource
    MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    @Resource
    MyUserDetailsService myUserDetailsService;
    @Resource
    private DataSource dataSource;

    /**
     * 用户发送请求到UsernamePasswordAuthenticationFilter，
     * 当用户认证成功以后，会调一个RemeberMeService这样一个服务。
     * 这个服务里面有一个TokenRepository，会生成一个Token，将这个Token写入到浏览器的Cookie里面，
     * 同时TokenRepository把生成的Token写入到数据库里面（还有用户名）
     * springSecurity会根据情况自动将token插入persistent_logins
     * .antMatchers("/admin/**").access("hasRole('ADMIN') and hasIpAddress('123.123.123.123')") // pass SPEL using access method
     * 再次访问需要权限的资源时：用cookie中的加密串，到db中验证，如果通过，自动登录才算通过
     *
     * @return PersistentTokenRepository
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//       tokenRepository.setCreateTableOnStartup(true); // 首次使用要创建存储sessionID的表
        return tokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //将我们自定义的验证码过滤器，配置到UsernamePasswordAuthenticationFilter之前
//        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
        http
                //跨域
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/login","/mylogin","/user/add").permitAll()//定义不需要认证就可以访问
                //定义需要相应角色就可以访问，角色信息可以自定义，在sys_role表中存储
                //和在接口中使用注解同样效果hasAuthority 等同于hasRole,校验时角色将被增加 "ROLE_"
                .antMatchers( "/user/**").hasRole("admin")
                .anyRequest().authenticated()//其余所有请求都需要登录认证才能访问
                .and()
                //表单登录
                .formLogin()
                //指定url，可由相应的controller处理跳转到登录页如login_page.html
//                .loginPage("/mylogin")//自定义登录url
                //指定自定义form表单请求的路径
                .loginProcessingUrl("/login").usernameParameter("username").passwordParameter("password")
                //.defaultSuccessUrl("/success")
//                .successForwardUrl("/login/success")//设置了登入登出的Handler,优先响应Handler
//                .failureUrl("/login/fail")//设置了登入登出的Handler,优先响应Handler
                //自定义认证成功或者失败的返回json
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                //这个formLogin().permitAll()方法允许所有用户基于表单登录访问/login这个page。
                .permitAll()
                .and()
                //登出
                .logout()
                .logoutUrl("/logout")//自定义退出url
                .logoutSuccessUrl("/login/mylogin")//登出成功时访问
                .logoutSuccessHandler(myLogoutSuccessHandle)//设置了登入登出的Handler,优先响应Handler
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .rememberMe()// 记住我
                .rememberMeParameter("rememberMe")
                .rememberMeCookieName("remember-me")
                .tokenRepository(persistentTokenRepository()) // 写入token
                .tokenValiditySeconds(60 * 10)
                .userDetailsService(myUserDetailsService)
                .and()
                //session设置
                .sessionManagement()
                .invalidSessionUrl("/expired")//过期后访问的地址
                .maximumSessions(1)//限制最大登录数
                .maxSessionsPreventsLogin(false)// 已有在线用户时，防止登录
                .expiredSessionStrategy(new CustomExpiredSessionStrategy())//session过期策略
                .sessionRegistry(sessionRegistry());
        //默认都会产生一个hidden标签 里面有安全相关的验证 防止请求伪造 这边我们暂时不需要 可禁用掉
        http.csrf().disable();
        http.exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint);//未登录
        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler); // 无权访问

    }

    /**
     * 密码加密的bean
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8091"));
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "OPTIONS", "DELETE"));
        configuration.setAllowCredentials(true);//允许携带cookie
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
