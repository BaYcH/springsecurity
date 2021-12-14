package com.bayc.springsecurity.controller;

import com.alibaba.fastjson.JSON;
import com.bayc.springsecurity.model.response.Result;
import com.bayc.springsecurity.utils.RedisUtil;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class TestControllerTest {

    @Autowired
    private TestController testController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Before
    void setup() {
        redisUtil = Mockito.mock(RedisUtil.class);
        when(redisUtil.hasKey(anyString())).thenReturn(anyBoolean());
        when(redisUtil.hashGet(anyString(), anyString())).thenReturn(anyString());
        when(redisUtil.hashSet(anyString(), anyString(), anyString())).thenReturn(anyBoolean());
        when(redisUtil.set(anyString(), anyString())).thenReturn(anyBoolean());
        when(redisUtil.get(anyString())).thenReturn(anyString());
    }

    @Test
    @WithMockUser(username = "bayc_test", authorities = {"del,add"})
    public void testDate() throws Exception {
        MockMvc build = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(SecurityMockMvcConfigurers.springSecurity()).build();

        MvcResult mvcResult = build.perform(MockMvcRequestBuilders.get("/test/date").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Result result = JSON.parseObject(mvcResult.getResponse().getContentAsString(), Result.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getStatus(), 500L);
    }


    @MockBean
    public RedisUtil redisUtil;
}