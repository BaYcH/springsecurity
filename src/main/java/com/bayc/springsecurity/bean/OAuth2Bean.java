package com.bayc.springsecurity.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * @author bayc
 * @packageName com.bayc.springsecurity.bean
 * @className OAuth2Bean
 * @description
 * @date 2021/2/25 上午11:51
 */
@Configuration
public class OAuth2Bean {

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        ClassPathResource classPathResource = new ClassPathResource("oauth-token.jks");
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(classPathResource, "wmdxyt".toCharArray());
        jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("oauth"));
        return jwtAccessTokenConverter;
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
