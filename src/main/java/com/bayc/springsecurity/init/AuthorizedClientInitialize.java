package com.bayc.springsecurity.init;

import com.alibaba.fastjson.JSON;
import com.bayc.springsecurity.constant.RedisKeys;
import com.bayc.springsecurity.model.oauth2.AuthorizedClient;
import com.bayc.springsecurity.model.oauth2.UserAuthority;
import com.bayc.springsecurity.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author bayc
 * @packageName com.bayc.springsecurity.init
 * @className AuthorizedClientInitialize
 * @description
 * @date 2021/2/26 上午11:03
 */
@Component
@Order(2)
@Slf4j
public class AuthorizedClientInitialize implements CommandLineRunner {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (redisUtil.hasKey(RedisKeys.SSO_CLIENT_INFO)) {
            return;
        }

        AuthorizedClient authorizedClient = AuthorizedClient.builder()
                .accessTokenValiditySeconds(24 * 3600)
                .additionalInformation(new HashMap<>())
                .authorities(new ArrayList<UserAuthority>() {
                    {
                        add(UserAuthority.builder().code("100").authority("QUERY").build());
                        add(UserAuthority.builder().code("101").authority("DEL").build());
                    }
                })
                .authorizedGrantTypes(new HashSet<String>() {
                    {
                        add("authorization_code");
                        add("refresh_token");
                        add("password");
                        add("client_credentials");
                    }
                })
                .autoApprove(false)
                .clientId("client_1")
                .clientSecret(bCryptPasswordEncoder.encode("123456"))
                .refreshTokenValiditySeconds(3 * 24 * 3600)
                .registeredRedirectUris(new HashSet<String>() {
                    {
                        add("https://www.baidu.com");
                    }
                })
                .resourceIds(new HashSet<String>() {
                    {
                        add("101");
                    }
                })
                .scopes(new HashSet<String>() {
                    {
                        add("all");
                        add("admin");
                    }
                })
                .scoped(true)
                .secretRequired(true)
                .build();
        if (redisUtil.hashSet(RedisKeys.SSO_CLIENT_INFO, authorizedClient.getClientId(), JSON.toJSONString(authorizedClient))) {
            log.info("客户端信息初始化完成");
        }
    }
}
