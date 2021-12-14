package com.bayc.springsecurity.model.oauth2;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author bayc
 * @packageName com.bayc.springsecurity.model
 * @className UserRole
 * @Description
 * @date 2021/2/24 下午3:47
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRole implements Serializable {
    private static final long serialVersionUID = 8112464524084100212L;

    private String code;
    private String roleName;

    private List<UserAuthority> authorities;
}
