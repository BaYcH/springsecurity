package com.bayc.springsecurity.constant;

/**
 * @author bayc
 * @packageName com.bayc.springsecurity.constant
 * @className ErrorMessages
 * @Description
 * @date 2021/2/24 下午4:31
 */
public class ErrorMessages {
    public static final String USERNAME_NOT_FOUND = "用户【{0}】不存在";
    public static final String USERNAME_FOUND_BUT_PARSEERROR = "用户【{0}】序列化错误";
    public static final String CLIENT_NOT_FOUND = "客户端【{0}】不存在";
    public static final String AUTHORIZATION_CODE_CREATE_ERROR = "认证码生成次数超过限制";
}
