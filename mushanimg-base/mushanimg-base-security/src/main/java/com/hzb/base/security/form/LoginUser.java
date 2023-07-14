package com.hzb.base.security.form;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: hzb
 * @Date: 2023/4/23
 */
@Data
@NoArgsConstructor
@ToString
public class LoginUser implements UserDetails {
    @Serial
    private static final long serialVersionUID = -6002920047120249346L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    private UserInfo user;

    private Set<String> roleKeys;
    private Set<String> permissions;
    private Set<String> routes;

    public LoginUser(Long userId, UserInfo user, Set<String> roleKeys, Set<String> permissions, Set<String> routes) {
        this.userId = userId;
        this.user = user;
        this.roleKeys = roleKeys;
        this.permissions = permissions;
        this.routes = routes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (permissions == null){
            return null;
        }
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (String permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        return authorities;
    }

    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return user.getPassword().getPassword();
    }

    @JSONField(serialize = false)
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
