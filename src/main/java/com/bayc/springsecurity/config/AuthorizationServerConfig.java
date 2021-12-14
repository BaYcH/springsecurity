package com.bayc.springsecurity.config;

import com.bayc.springsecurity.handler.UserAccessDeniedHandler;
import com.bayc.springsecurity.handler.UserAuthorizedEntryPoint;
import com.bayc.springsecurity.handler.UserObjectPostProcessor;
import com.bayc.springsecurity.service.impl.ClientServiceImpl;
import com.bayc.springsecurity.service.impl.UserServiceImpl;
import com.bayc.springsecurity.service.impl.UserTokenEnhancerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private TokenStore tokenStore;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Resource
    private AuthorizationCodeServices authorizationCodeServices;

    @Resource
    private ClientServiceImpl clientService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserServiceImpl userService;

    @Resource
    private WebResponseExceptionTranslator webResponseExceptionTranslator;

    @Resource
    private UserAuthorizedEntryPoint userAuthorizedEntryPoint;

    @Resource
    private UserAccessDeniedHandler userAccessDeniedHandler;

    @Resource
    private UserTokenEnhancerImpl userTokenEnhancer;

    @Resource
    private TokenEnhancerChain tokenEnhancerChain;

    /**
     * 配置授权服务器的安全，意味着实际上是/oauth/token端点。
     * /oauth/authorize端点也应该是安全的
     * 默认的设置覆盖到了绝大多数需求，所以一般情况下你不需要做任何事情。
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.accessDeniedHandler(userAccessDeniedHandler)
                .authenticationEntryPoint(userAuthorizedEntryPoint)
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(passwordEncoder)
                .addObjectPostProcessor(new UserObjectPostProcessor())
        ;
    }

    /**
     * 配置ClientDetailsService
     * 注意，除非你在下面的configure(AuthorizationServerEndpointsConfigurer)中指定了一个AuthenticationManager，否则密码授权方式不可用。
     * 至少配置一个client，否则服务器将不会启动。
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(this.clientService);
    }

    /**
     * 该方法是用来配置Authorization Server endpoints的一些非安全特性的，比如token存储、token自定义、授权类型等等的
     * 默认情况下，你不需要做任何事情，除非你需要密码授权，那么在这种情况下你需要提供一个AuthenticationManager
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.userDetailsService(userService)
                .tokenStore(tokenStore)
                .tokenEnhancer(tokenEnhancerChain)
                .accessTokenConverter(jwtAccessTokenConverter)
                .authorizationCodeServices(authorizationCodeServices)
                .authenticationManager(authenticationManager);

        endpoints.pathMapping("/oauth/confirm_access", "/custom/confirm_access");
        endpoints.exceptionTranslator(webResponseExceptionTranslator);
    }


    @Bean
    public TokenEnhancerChain tokenEnhancerChain() {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancers = new ArrayList<>();
        enhancers.add(userTokenEnhancer);
        enhancers.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(enhancers);
        return enhancerChain;
    }
}