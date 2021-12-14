package com.bayc.springsecurity.exception;

import com.bayc.springsecurity.exception.serialize.UserOauthExceptionSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author bayc
 * @packageName com.bayc.springsecurity.exception
 * @className UserOAuthException
 * @description
 * @date 2021/3/5 下午4:29
 */
@JsonSerialize(using = UserOauthExceptionSerializer.class)
public class UserOAuthException extends OAuth2Exception {
    public UserOAuthException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserOAuthException(String msg) {
        super(msg);
    }
}
