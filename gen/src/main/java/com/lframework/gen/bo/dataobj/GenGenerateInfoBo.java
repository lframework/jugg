package com.lframework.gen.bo.dataobj;

import com.lframework.common.utils.StringUtil;
import com.lframework.gen.dto.dataobj.GenGenerateInfoDto;
import com.lframework.starter.security.service.system.ISysMenuService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GenGenerateInfoBo extends BaseBo<GenGenerateInfoDto> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 生成模板类型
     */
    private Integer templateType;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 业务名
     */
    private String bizName;

    /**
     * 类名
     */
    private String className;

    /**
     * 类描述
     */
    private String classDescription;

    /**
     * 父级菜单ID
     */
    private String parentMenuId;

    /**
     * 父级菜单名称
     */
    private String parentMenuName;

    /**
     * 主键类型
     */
    private Integer keyType;

    /**
     * 作者
     */
    private String author;

    /**
     * 本级菜单编号
     */
    private String menuCode;

    /**
     * 本级菜单名称
     */
    private String menuName;

    /**
     * 详情页Span总数量
     */
    private Integer detailSpan;

    /**
     * 是否应用缓存
     */
    private Boolean isCache;

    /**
     * 是否内置删除功能
     */
    private Boolean hasDelete;

    public GenGenerateInfoBo() {

    }

    public GenGenerateInfoBo(GenGenerateInfoDto dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<GenGenerateInfoDto> convert(GenGenerateInfoDto dto) {

        return super.convert(dto, GenGenerateInfoBo::getTemplateType, GenGenerateInfoBo::getKeyType);
    }

    @Override
    protected void afterInit(GenGenerateInfoDto dto) {

        this.templateType = dto.getTemplateType().getCode();
        this.keyType = dto.getKeyType().getCode();

        if (!StringUtil.isBlank(dto.getParentMenuId())) {
            ISysMenuService sysMenuService = ApplicationUtil.getBean(ISysMenuService.class);
            this.parentMenuName = sysMenuService.getById(dto.getParentMenuId()).getName();
        }
    }
}
