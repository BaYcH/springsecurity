package com.bayc.springsecurity.service.impl;

import com.alibaba.fastjson.JSON;
import com.bayc.springsecurity.constant.ErrorMessages;
import com.bayc.springsecurity.constant.RedisKeys;
import com.bayc.springsecurity.exception.AuthorizationCodeCreateError;
import com.bayc.springsecurity.utils.RedisUtil;
import lombok.SneakyThrows;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * @author bayc
 * @packageName com.bayc.springsecurity.service.impl
 * @className AuthorizationCodeServiceImpl
 * @description
 * @date 2021/2/26 上午10:21
 */
@Service
public class AuthorizationCodeServiceImpl implements AuthorizationCodeServices {

    private final long AuthorizationCodeTime = 300;
    @Resource
    private RedisUtil redisUtil;

    private RandomValueStringGenerator generator = new RandomValueStringGenerator();

    @SneakyThrows
    @Override
    public String createAuthorizationCode(OAuth2Authentication authentication) {
        String code = generator.generate();
        int count = 0;
        while (redisUtil.hasKey(MessageFormat.format(RedisKeys.SSO_CLIENT_AUTHORIZATION_CODE, code))) {
            code = generator.generate();
            count++;
            if (count > 20) {
                throw new AuthorizationCodeCreateError(ErrorMessages.AUTHORIZATION_CODE_CREATE_ERROR);
            }
        }
        redisUtil.set(MessageFormat.format(RedisKeys.SSO_CLIENT_AUTHORIZATION_CODE, code), JSON.toJSONString(SerializationUtils.serialize(authentication)), AuthorizationCodeTime);
        return code;
    }

    @Override
    public OAuth2Authentication consumeAuthorizationCode(String code) throws InvalidGrantException {
        Object authentication = redisUtil.get(MessageFormat.format(RedisKeys.SSO_CLIENT_AUTHORIZATION_CODE, code));
        if (null == authentication) {
            return null;
        }
        redisUtil.delete(MessageFormat.format(RedisKeys.SSO_CLIENT_AUTHORIZATION_CODE, code));
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) SerializationUtils.deserialize(JSON.parseObject(authentication.toString(), byte[].class));
        return oAuth2Authentication;
    }
}
