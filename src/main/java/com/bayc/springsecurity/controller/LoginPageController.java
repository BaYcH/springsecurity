package com.bayc.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author bayc
 * @packageName com.bayc.springsecurity.controller
 * @className LoginPageController
 * @description
 * @date 2021/3/2 上午10:41
 */
@Controller
public class LoginPageController {

    @GetMapping("/auth/login")
    public String loginPage(Model model) {
        model.addAttribute("loginProcessUrl", "/auth/authorize");
        return "base-login";
    }
}
