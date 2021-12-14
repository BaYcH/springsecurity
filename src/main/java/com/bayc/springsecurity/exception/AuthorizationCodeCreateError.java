package com.bayc.springsecurity.exception;

/**
 * @author bayc
 * @packageName com.bayc.springsecurity.exception
 * @className AuthorizationCodeCreateError
 * @description
 * @date 2021/3/1 上午10:25
 */
public class AuthorizationCodeCreateError extends Exception {
    public AuthorizationCodeCreateError() {
    }

    public AuthorizationCodeCreateError(String message) {
        super(message);
    }

    public AuthorizationCodeCreateError(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationCodeCreateError(Throwable cause) {
        super(cause);
    }

    public AuthorizationCodeCreateError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
