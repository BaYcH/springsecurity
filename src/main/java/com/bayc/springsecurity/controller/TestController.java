package com.bayc.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author bayc
 * @packageName com.bayc.springsecurity.controller
 * @className TestController
 * @Description
 * @date 2021/2/24 下午10:33
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/date")
    @PreAuthorize("hasAuthority(\"add\")")
    public String date() {
        //OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }
}
