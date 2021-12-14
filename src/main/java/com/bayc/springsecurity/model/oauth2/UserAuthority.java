package com.bayc.springsecurity.model.oauth2;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author bayc
 * @packageName com.bayc.springsecurity.model
 * @className UserAuthority
 * @Description
 * @date 2021/2/24 下午3:43
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthority implements GrantedAuthority {

    private static final long serialVersionUID = 4588856703707678892L;

    private String code;
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
