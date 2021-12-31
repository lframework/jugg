package com.lframework.starter.security.dto.system.user;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class DefaultSysUserDto implements BaseDto, Serializable {

    public static final String CACHE_NAME = "DefaultSysUserDto";

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 编号
     */
    private String code;

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 性别 0-未知 1-男 2-女
     */
    private Integer gender;

    /**
     * 状态 1-在用 0停用
     */
    private Boolean available;

    /**
     * 备注
     */
    private String description;

    /**
     * 创建人ID
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
