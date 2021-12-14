package com.bayc.springsecurity.service.impl;

import com.alibaba.fastjson.JSON;
import com.bayc.springsecurity.constant.ErrorMessages;
import com.bayc.springsecurity.constant.RedisKeys;
import com.bayc.springsecurity.model.oauth2.AuthorizedClient;
import com.bayc.springsecurity.utils.RedisUtil;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * @author bayc
 * @packageName com.bayc.springsecurity.service.impl
 * @className ClientServiceImpl
 * @description
 * @date 2021/2/25 上午11:52
 */
@Service
public class ClientServiceImpl implements ClientDetailsService {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        if (!redisUtil.hasKey(RedisKeys.SSO_CLIENT_INFO)) {
            throw new ClientRegistrationException(MessageFormat.format(ErrorMessages.CLIENT_NOT_FOUND, clientId));
        }
        Object clientInfo = redisUtil.hashGet(RedisKeys.SSO_CLIENT_INFO, clientId);
        if (null == clientInfo) {
            throw new ClientRegistrationException(MessageFormat.format(ErrorMessages.CLIENT_NOT_FOUND, clientId));
        }

        AuthorizedClient authorizedClient = JSON.parseObject(clientInfo.toString(), AuthorizedClient.class);
        return authorizedClient;
    }
}
