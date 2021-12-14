package com.bayc.springsecurity.service.impl;

import com.bayc.springsecurity.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @author bayc
 * @packageName com.bayc.springsecurity.service.impl
 * @className TokenServiceImpl
 * @description
 * @date 2021/2/26 上午10:22
 */
@Service("tokenStore")
public class TokenServiceImpl extends JwtTokenStore {

    @Resource
    private RedisUtil redisUtil;

    private JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * Create a JwtTokenStore with this token enhancer (should be shared with the DefaultTokenServices if used).
     *
     * @param jwtAccessTokenConverter
     */
    @Autowired
    public TokenServiceImpl(JwtAccessTokenConverter jwtAccessTokenConverter) {
        super(jwtAccessTokenConverter);
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
    }

    @Override
    public void setApprovalStore(ApprovalStore approvalStore) {
        super.setApprovalStore(approvalStore);
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return super.readAuthentication(token);
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        return super.readAuthentication(token);
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        super.storeAccessToken(token, authentication);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        return super.readAccessToken(tokenValue);
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        super.removeAccessToken(token);
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        super.storeRefreshToken(refreshToken, authentication);
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        return super.readRefreshToken(tokenValue);
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return super.readAuthenticationForRefreshToken(token);
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        super.removeRefreshToken(token);
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        super.removeAccessTokenUsingRefreshToken(refreshToken);
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        return super.getAccessToken(authentication);
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        return super.findTokensByClientIdAndUserName(clientId, userName);
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        return super.findTokensByClientId(clientId);
    }

    @Override
    public void setTokenEnhancer(JwtAccessTokenConverter tokenEnhancer) {
        super.setTokenEnhancer(tokenEnhancer);
    }

}
