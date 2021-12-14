package com.bayc.springsecurity.init;

import com.alibaba.fastjson.JSON;
import com.bayc.springsecurity.constant.RedisKeys;
import com.bayc.springsecurity.model.oauth2.User;
import com.bayc.springsecurity.model.oauth2.UserAuthority;
import com.bayc.springsecurity.model.oauth2.UserRole;
import com.bayc.springsecurity.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author bayc
 * @packageName com.bayc.springsecurity.init
 * @className UserInitialize
 * @Description
 * @date 2021/2/24 下午4:51
 */
@Component
@Order(1)
public class UserInitialize implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(UserInitialize.class);

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (redisUtil.hasKey(RedisKeys.SSO_USER_INFO)) {
            return;
        }

        User user = User.builder()
                .credentialsNonExpired(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .enabled(true)
                .id(1)
                .mailaddress("123456@qq.com")
                .password(bCryptPasswordEncoder.encode("123456"))
                .phonenumber("18571511021")
                .usercode("bayc")
                .username("bayc")
                .userRole(UserRole.builder()
                        .code("1001")
                        .roleName("管理员")
                        .authorities(new ArrayList<UserAuthority>() {
                            {
                                add(new UserAuthority("101", "add"));
                                add(new UserAuthority("102", "del"));
                            }
                        }).build())
                .build();

        if (redisUtil.hashSet(RedisKeys.SSO_USER_INFO, user.getUsername(), JSON.toJSONString(user))) {
            logger.info("用户数据初始化成功");
        }
    }
}
