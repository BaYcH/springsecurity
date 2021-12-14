package com.bayc.springsecurity.config;

import com.bayc.springsecurity.handler.UserAccessDeniedHandler;
import com.bayc.springsecurity.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserServiceImpl userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserAccessDeniedHandler userAccessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);

        http.exceptionHandling()
                .accessDeniedHandler(userAccessDeniedHandler)
                .and()
                .requestMatchers()
                .antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers("/auth/login", "/auth/authorize", "/oauth/**", "/logout", "/login", "/custom/confirm_access")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .logout().logoutUrl("/logout")
                .and()
                .rememberMe()
                .alwaysRemember(true)
//                .and()
                //.addLogoutHandler(new TokenLogoutHandler(tokenManager))
                //.addFilter(new TokenLoginFilter(authenticationManager()))
                //.addFilter(new TokenAuthenticationFilter(authenticationManager()))
//                .formLogin()
//                .loginPage("/auth/login")
//                .loginProcessingUrl("/auth/authorize")
        ;
        http.httpBasic().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        // 不起用Spring Security
        //web.ignoring().antMatchers("/testa/**");
    }

    @Bean("authenticationManager")
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}