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
 * @className TestAController
 * @description
 * @date 2021/3/9 下午3:55
 */
@RestController
@RequestMapping("/testa")
public class TestAController {

    @GetMapping("/date")
    @PreAuthorize("hasAuthority(\"add\")")
    public String date() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }
}
