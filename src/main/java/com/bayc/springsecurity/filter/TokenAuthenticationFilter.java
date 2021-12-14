package com.bayc.springsecurity.filter;

import com.alibaba.fastjson.JSONArray;
import com.bayc.springsecurity.model.oauth2.UserAuthority;
import com.bayc.springsecurity.model.response.Result;
import com.bayc.springsecurity.utils.JwtTokenUtil;
import com.bayc.springsecurity.utils.ResponseUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName: TokenAuthenticationFilter
 * @Description: TokenAuthenticationFilter
 * @Author oyc
 * @Date 2021/1/18 10:59
 * @Version 1.0
 */
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    public TokenAuthenticationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("=================" + request.getRequestURI());
        //不需要鉴权
        if (request.getRequestURI().startsWith("index")
                || request.getRequestURI().startsWith("/favicon.ico")) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = getAuthentication(request);
        } catch (Exception e) {
            ResponseUtil.out(response, Result.error(e.getMessage()));
        }
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
//            ResponseUtil.out(response, Result.error("鉴权失败"));
//            return;
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // 获取Token字符串，token 置于 header 里
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            token = request.getParameter("token");
        }

        if (token != null && !"".equals(token.trim())) {
            // 从Token中解密获取用户名
            String userName = JwtTokenUtil.getUserNameFromToken(token);

            if (userName != null) {
                // 从Token中解密获取用户角色
                String role = JwtTokenUtil.getUserRoleFromToken(token);
                List<UserAuthority> authorityList = JSONArray.parseArray(role, UserAuthority.class);
                return new UsernamePasswordAuthenticationToken(userName, token, authorityList);
            }
            return null;
        }
        return null;
    }
}