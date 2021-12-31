package com.lframework.starter.security.vo.system.position;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
public class SysUserPositionSettingVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空！")
    private String userId;

    /**
     * 岗位ID
     */
    private List<String> positionIds;
}
