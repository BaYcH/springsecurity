package com.bayc.springsecurity.handler;

import com.bayc.springsecurity.exception.UserOAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * @author bayc
 * @packageName com.bayc.springsecurity.handler
 * @className UserWebResponseException
 * @description
 * @date 2021/3/5 下午4:30
 */
@Component
public class UserWebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {
    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception exception) throws Exception {
        if (exception instanceof OAuth2Exception) {
            OAuth2Exception oAuth2Exception = (OAuth2Exception) exception;
            return ResponseEntity
                    .status(oAuth2Exception.getHttpErrorCode())
                    .body(new UserOAuthException(oAuth2Exception.getMessage()));
        } else if (exception instanceof AuthenticationException) {
            AuthenticationException authenticationException = (AuthenticationException) exception;
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new UserOAuthException(authenticationException.getMessage()));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UserOAuthException(exception.getMessage()));
    }
}
