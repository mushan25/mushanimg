package com.hzb.auth.form;

import com.alibaba.fastjson2.annotation.JSONField;
import com.hzb.lib.user.proto.UserProto;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Set;

/**
 * @author: hzb
 * @Date: 2023/4/23
 */
@Data
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

    private UserProto.User user;

    public LoginUser(Long userId, UserProto.User user, Set<String> permissions){
        this.userId = userId;
        this.user = user;
        this.permissions = permissions;
    }

    /**
     * 权限列表
     */
    private Set<String> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
