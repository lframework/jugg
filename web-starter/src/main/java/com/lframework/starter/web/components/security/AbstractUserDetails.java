package com.lframework.starter.web.components.security;

import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统内UserDetails都需要继承此类
 *
 * @author zmj
 */
@Data
public abstract class AbstractUserDetails implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     * 由AbstractUserDetailsService查询
     */
    private String id;

    /**
     * 登录名
     * 由AbstractUserDetailsService查询
     */
    private String username;

    /**
     * 姓名
     * 由AbstractUserDetailsService查询
     */
    private String name;

    /**
     * 密码
     * 由AbstractUserDetailsService查询
     */
    private String password;

    /**
     * 状态
     * 由AbstractUserDetailsService查询
     */
    private Boolean available;

    /**
     * 权限
     * 由IMenuService查询
     */
    private Set<String> permissions;

    /**
     * 用户IP地址
     * 会自动获取
     */
    private String ip;

    /**
     * 锁定状态
     */
    private Boolean lockStatus;

    /**
     * Spring Security所需权限
     */
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (!CollectionUtil.isEmpty(this.permissions) && CollectionUtil.isEmpty(this.authorities)) {

            this.authorities = this.permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        }

        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return !this.lockStatus;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return this.available;
    }

    /**
     * 是否无权限
     * @return
     */
    public boolean isNoPermission() {

        return CollectionUtil.isEmpty(this.permissions);
    }

    public boolean isAdmin() {

        return CollectionUtil.isEmpty(this.permissions) ?
                false :
                this.permissions.contains(StringPool.PERMISSION_ADMIN_NAME);
    }

}
