package com.bayc.springsecurity.model.oauth2;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author bayc
 * @packageName com.bayc.springsecurity.model
 * @className User
 * @Description
 * @date 2021/2/24 下午3:18
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails, Serializable {

    private static final long serialVersionUID = 4877469991811089096L;

    private int id;
    private String usercode;
    private String username;
    private String password;
    private String mailaddress;
    private String phonenumber;
    private UserRole userRole;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    @Override
    @JSONField(serialize = false, deserialize = false)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UserAuthority> authorities = userRole.getAuthorities();
        authorities.add(UserAuthority.builder().code("0000").authority("ROLE_ADMIN").build());
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
