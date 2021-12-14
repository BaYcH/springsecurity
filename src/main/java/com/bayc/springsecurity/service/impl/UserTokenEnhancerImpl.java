package com.bayc.springsecurity.service.impl;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

/**
 * @author bayc
 * @packageName com.bayc.springsecurity.service.impl
 * @className UserTokenEnhancer
 * @description
 * @date 2021/3/11 下午5:27
 */
@Component
public class UserTokenEnhancerImpl implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String clientId = authentication.getOAuth2Request().getClientId();

        return accessToken;
    }
}
