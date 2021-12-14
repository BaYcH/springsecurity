package com.bayc.springsecurity.handler;

import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;

/**
 * @author bayc
 * @packageName com.bayc.springsecurity.handler
 * @className UserObjectPostProcessor
 * @description
 * @date 2021/3/8 上午9:50
 */
public class UserObjectPostProcessor implements ObjectPostProcessor<Object> {

    private String realm = "oauth2/client";

    @Override
    public <O> O postProcess(O object) {
        if (object instanceof ClientCredentialsTokenEndpointFilter) {
            OAuth2AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
            authenticationEntryPoint.setTypeName("Form");
            authenticationEntryPoint.setRealmName(realm);
            authenticationEntryPoint.setExceptionTranslator(new UserWebResponseExceptionTranslator());
            ((ClientCredentialsTokenEndpointFilter) object).setAuthenticationEntryPoint(authenticationEntryPoint);
        }
        return object;
    }
}
