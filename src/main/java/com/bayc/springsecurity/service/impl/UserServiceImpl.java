package com.bayc.springsecurity.service.impl;

import com.alibaba.fastjson.JSON;
import com.bayc.springsecurity.constant.ErrorMessages;
import com.bayc.springsecurity.constant.RedisKeys;
import com.bayc.springsecurity.model.oauth2.User;
import com.bayc.springsecurity.utils.RedisUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * @author bayc
 * @packageName com.bayc.springsecurity.service.impl
 * @className UserService
 * @Description
 * @date 2021/2/24 下午3:57
 */
@Service
public class UserServiceImpl implements UserDetailsService {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Object tempUser = redisUtil.hashGet(RedisKeys.SSO_USER_INFO, username);
        if (null == tempUser) {
            throw new UsernameNotFoundException(MessageFormat.format(ErrorMessages.USERNAME_NOT_FOUND, username));
        }
        User user = JSON.parseObject(tempUser.toString(), User.class);
        if (null == user) {
            throw new UsernameNotFoundException(MessageFormat.format(ErrorMessages.USERNAME_FOUND_BUT_PARSEERROR, username));
        }
        return user;
    }
}
