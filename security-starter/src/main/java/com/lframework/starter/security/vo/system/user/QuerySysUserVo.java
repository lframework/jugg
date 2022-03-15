package com.lframework.starter.security.vo.system.user;

import com.lframework.starter.web.vo.BaseVo;
import com.lframework.starter.web.vo.PageVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySysUserVo extends PageVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String code;

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 岗位ID
     */
    private String positionId;

    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 状态
     */
    private Boolean available;

    /**
     * 是否锁定
     */
    private Boolean lockStatus;
}
