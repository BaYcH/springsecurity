package com.bayc.springsecurity.handler;

import com.bayc.springsecurity.model.response.Result;
import com.bayc.springsecurity.utils.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: UnauthorizedEntryPoint
 * @Description: UnauthorizedEntryPoint
 * @Author oyc
 * @Date 2021/1/18 10:58
 * @Version 1.0
 */
@Component
public class UserAuthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseUtil.out(response, Result.unAuth(authException.getMessage()));
    }
}