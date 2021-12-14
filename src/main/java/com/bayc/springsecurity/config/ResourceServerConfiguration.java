package com.bayc.springsecurity.config;

import com.bayc.springsecurity.handler.UserAccessDeniedHandler;
import com.bayc.springsecurity.handler.UserAuthorizedEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    public static final String RESOURCE_ID = "101";

    @Resource
    private TokenStore tokenStore;

    @Resource
    private UserAuthorizedEntryPoint userAuthorizedEntryPoint;

    @Resource
    private UserAccessDeniedHandler userAccessDeniedHandler;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID)
                .tokenStore(tokenStore)
                .authenticationEntryPoint(userAuthorizedEntryPoint)
                .stateless(true)
        ;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .requestMatchers()
                .antMatchers("/**/**/")
                .and()
                .authorizeRequests()
                .antMatchers("/testa/**")
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(userAuthorizedEntryPoint)
                .accessDeniedHandler(userAccessDeniedHandler)
        ;
    }




}