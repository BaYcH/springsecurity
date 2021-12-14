package com.bayc.springsecurity.constant;

/**
 * @author bayc
 * @packageName com.bayc.springsecurity.constant
 * @className RedisKeys
 * @Description
 * @date 2021/2/24 下午4:00
 */
public class RedisKeys {
    /**
     * 用户基本信息
     */
    public static final String SSO_USER_INFO = "SSO:USER:INFO";

    /**
     * 用户登录认证信息
     */
    public static final String SSO_USER_AUTHORIZATION_TOKEN = "SSO:USER:AUTHORIZATION:TOKEN";

    /**
     * 客户端基本信息
     */
    public static final String SSO_CLIENT_INFO = "SSO:CLIENT:INFO";

    /**
     * 客户端授权码信息
     */
    public static final String SSO_CLIENT_AUTHORIZATION_CODE = "SSO:CLIENT:AUTHORIZATION:CODE:{0}";

    /**
     * 客户端认证信息
     */
    public static final String SSO_CLIENT_AUTHORIZATION_TOKEN = "SSO:CLIENT:AUTHORIZATION:TOKEN:{0}";
}
