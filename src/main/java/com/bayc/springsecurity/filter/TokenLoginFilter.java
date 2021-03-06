package com.bayc.springsecurity.filter;

import com.alibaba.fastjson.JSONArray;
import com.bayc.springsecurity.model.oauth2.User;
import com.bayc.springsecurity.model.response.Result;
import com.bayc.springsecurity.utils.JwtTokenUtil;
import com.bayc.springsecurity.utils.ResponseUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @ClassName: TokenLoginFilter
 * @Description: TokenLoginFilter
 * @Author oyc
 * @Date 2021/1/18 10:59
 * @Version 1.0
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public TokenLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            username = username != null ? username.trim() : "";
            password = password != null ? password : "";
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ????????????
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        try {
            User user = (User) auth.getPrincipal();
            String authrorities = JSONArray.toJSONString(user.getAuthorities());
            String token = JwtTokenUtil.createToken(user.getUsername(), authrorities);
            HashMap<Object, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("user", user);
            map.put("loginName", user.getUsername());
            ResponseUtil.out(response, Result.ok(map));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * ????????????
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ResponseUtil.out(response, Result.error("????????????"));
    }
}