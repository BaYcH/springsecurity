package com.bayc.springsecurity.model;

import com.bayc.springsecurity.model.oauth2.UserAuthority;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.SerializationUtils;

class UserAuthorityTest {
    @Test
    public void serialize() {
        UserAuthority userAuthority = UserAuthority.builder().code("111").authority("aaaa").build();
        byte[] serialize = SerializationUtils.serialize(userAuthority);
        userAuthority = (UserAuthority) SerializationUtils.deserialize(serialize);
        Assertions.assertNotNull(userAuthority);
    }
}