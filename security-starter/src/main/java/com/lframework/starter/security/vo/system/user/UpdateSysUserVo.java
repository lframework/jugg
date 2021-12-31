package com.lframework.starter.security.vo.system.user;

import com.lframework.starter.mybatis.enums.Gender;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class UpdateSysUserVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotBlank(message = "ID不能为空！")
    private String id;

    /**
     * 编号
     */
    @NotBlank(message = "请输入编号！")
    private String code;

    /**
     * 姓名
     */
    @NotBlank(message = "请输入姓名！")
    private String name;

    /**
     * 用户名
     */
    @NotBlank(message = "请输入用户名！")
    private String username;

    /**
     * 密码 如果不为空则为修改密码
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
    @NotNull(message = "请选择性别！")
    @IsEnum(message = "请选择性别！", enumClass = Gender.class)
    private Integer gender;

    /**
     * 岗位ID
     */
    private List<String> positionIds;

    /**
     * 角色ID
     */
    private List<String> roleIds;

    /**
     * 部门ID
     */
    private List<String> deptIds;

    /**
     * 状态
     */
    @NotNull(message = "请选择状态！")
    private Boolean available;

    /**
     * 备注
     */
    private String description;
}
