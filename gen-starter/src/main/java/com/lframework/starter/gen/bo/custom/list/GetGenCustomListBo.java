package com.lframework.starter.gen.bo.custom.list;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.gen.entity.GenCustomForm;
import com.lframework.starter.gen.entity.GenCustomList;
import com.lframework.starter.gen.entity.GenCustomListCategory;
import com.lframework.starter.gen.entity.GenCustomListDetail;
import com.lframework.starter.gen.entity.GenCustomListHandleColumn;
import com.lframework.starter.gen.entity.GenCustomListQueryParams;
import com.lframework.starter.gen.entity.GenCustomListToolbar;
import com.lframework.starter.gen.entity.GenDataEntityDetail;
import com.lframework.starter.gen.entity.GenDataObj;
import com.lframework.starter.gen.enums.GenCustomListBtnType;
import com.lframework.starter.gen.enums.GenCustomListDetailType;
import com.lframework.starter.gen.service.IGenCustomFormService;
import com.lframework.starter.gen.service.IGenCustomListCategoryService;
import com.lframework.starter.gen.service.IGenCustomListDetailService;
import com.lframework.starter.gen.service.IGenCustomListHandleColumnService;
import com.lframework.starter.gen.service.IGenCustomListQueryParamsService;
import com.lframework.starter.gen.service.IGenCustomListToolbarService;
import com.lframework.starter.gen.service.IGenDataEntityDetailService;
import com.lframework.starter.gen.service.IGenDataObjService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.bo.SuperBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetGenCustomListBo extends BaseBo<GenCustomList> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 分类ID
   */
  @ApiModelProperty("分类ID")
  private String categoryId;

  /**
   * 分类名称
   */
  @ApiModelProperty("分类名称")
  private String categoryName;

  /**
   * 数据对象ID
   */
  @ApiModelProperty("数据对象ID")
  private String dataObjId;

  /**
   * 列表类型
   */
  @ApiModelProperty("列表类型")
  private Integer listType;

  /**
   * 数据对象名称
   */
  @ApiModelProperty("数据对象名称")
  private String dataObjName;

  /**
   * 表单Label宽度
   */
  @ApiModelProperty("表单Label宽度")
  private Integer labelWidth;

  /**
   * 是否分页
   */
  @ApiModelProperty("是否分页")
  private Boolean hasPage;

  /**
   * 是否树形列表
   */
  @ApiModelProperty("是否树形列表")
  private Boolean treeData;

  /**
   * ID字段
   */
  @ApiModelProperty("ID字段")
  private String idColumn;

  /**
   * 父级ID字段
   */
  @ApiModelProperty("父级ID字段")
  private String treePidColumn;

  /**
   * 树形节点字段
   */
  @ApiModelProperty("树形节点字段")
  private String treeNodeColumn;

  /**
   * 子节点Key值
   */
  @ApiModelProperty("子节点Key值")
  private String treeChildrenKey;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 查询前置SQL
   */
  @ApiModelProperty("查询前置SQL")
  private String queryPrefixSql;

  /**
   * 查询后置SQL
   */
  @ApiModelProperty("查询后置SQL")
  private String querySuffixSql;

  /**
   * 后置SQL
   */
  @ApiModelProperty("后置SQL")
  private String suffixSql;

  /**
   * 是否允许导出
   */
  @ApiModelProperty("是否允许导出")
  private Boolean allowExport;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 工具栏
   */
  @ApiModelProperty("工具栏")
  private List<ToolbarBo> toolbars;

  /**
   * 操作列
   */
  @ApiModelProperty("操作列")
  private List<HandleColumnBo> handleColumns;

  /**
   * 查询条件
   */
  @ApiModelProperty("查询条件")
  private List<QueryParamsBo> queryParams;

  /**
   * 详情
   */
  @ApiModelProperty("详情")
  private List<DetailBo> details;

  public GetGenCustomListBo() {
  }

  public GetGenCustomListBo(GenCustomList dto) {
    super(dto);
  }

  @Override
  protected void afterInit(GenCustomList dto) {
    if (!StringUtil.isBlank(dto.getCategoryId())) {
      IGenCustomListCategoryService genCustomListCategoryService = ApplicationUtil.getBean(
          IGenCustomListCategoryService.class);
      GenCustomListCategory category = genCustomListCategoryService.findById(dto.getCategoryId());
      this.categoryName = category.getName();
    }

    this.listType = dto.getListType().getCode();

    IGenDataEntityDetailService genDataEntityDetailService = ApplicationUtil
        .getBean(IGenDataEntityDetailService.class);
    IGenDataObjService genDataObjService = ApplicationUtil.getBean(IGenDataObjService.class);
    GenDataObj dataObj = genDataObjService.findById(dto.getDataObjId());
    this.dataObjName = dataObj.getName();

    IGenCustomListQueryParamsService genCustomListQueryParamsService = ApplicationUtil.getBean(
        IGenCustomListQueryParamsService.class);
    List<GenCustomListQueryParams> queryParams = genCustomListQueryParamsService.getByCustomListId(
        dto.getId());
    if (!CollectionUtil.isEmpty(queryParams)) {
      this.queryParams = queryParams.stream().map(t -> {
        GenDataEntityDetail entityDetail = genDataEntityDetailService.getById(t.getDataEntityId());
        QueryParamsBo bo = new QueryParamsBo();
        bo.setId(t.getDataEntityId());
        bo.setRelaId(t.getRelaId());
        bo.setFrontShow(t.getFrontShow());
        bo.setQueryType(t.getQueryType().getCode());
        bo.setFormWidth(t.getFormWidth());
        bo.setDefaultValue(t.getDefaultValue());
        bo.setType(t.getType().getCode());
        bo.setDataType(entityDetail.getDataType().getCode());
        bo.setViewType(entityDetail.getViewType().getCode());

        return bo;
      }).collect(Collectors.toList());
    }

    IGenCustomListDetailService genCustomListDetailService = ApplicationUtil.getBean(
        IGenCustomListDetailService.class);
    List<GenCustomListDetail> details = genCustomListDetailService.getByCustomListId(dto.getId());
    this.details = details.stream().map(t -> {
      DetailBo bo = new DetailBo();
      bo.setId(t.getDataEntityId());
      bo.setRelaId(t.getRelaId());
      bo.setWidthType(t.getWidthType().getCode());
      bo.setSortable(t.getSortable());
      bo.setWidth(t.getWidth());
      bo.setType(t.getType().getCode());
      if (t.getType() == GenCustomListDetailType.CUSTOM) {
        bo.setId(t.getRelaId());
      }
      bo.setFormatter(t.getFormatter());

      return bo;
    }).collect(Collectors.toList());

    IGenCustomListToolbarService genCustomListToolbarService = ApplicationUtil
        .getBean(IGenCustomListToolbarService.class);
    List<GenCustomListToolbar> toolbars = genCustomListToolbarService
        .getByCustomListId(dto.getId());

    IGenCustomFormService genCustomFormService = ApplicationUtil
        .getBean(IGenCustomFormService.class);

    this.toolbars = toolbars.stream().map(t -> {
      ToolbarBo toolbar = new ToolbarBo();
      toolbar.setId(t.getId());
      toolbar.setName(t.getName());
      toolbar.setViewType(t.getViewType().getCode());
      toolbar.setBtnType(t.getBtnType().getCode());
      if (t.getBtnType() == GenCustomListBtnType.CUSTOM_FORM) {
        GenCustomForm form = genCustomFormService.findById(t.getBtnConfig());
        if (form == null) {
          throw new DefaultClientException("工具栏关联的自定义表单不存在！");
        }
        toolbar.setCustomFormId(form.getId());
        toolbar.setCustomFormName(form.getName());
        toolbar.setRequestParam(t.getRequestParam());
      } else {
        toolbar.setBtnConfig(t.getBtnConfig());
      }
      toolbar.setIcon(t.getIcon());
      return toolbar;
    }).collect(Collectors.toList());

    IGenCustomListHandleColumnService genCustomListHandleColumnService = ApplicationUtil
        .getBean(IGenCustomListHandleColumnService.class);
    List<GenCustomListHandleColumn> handleColumns = genCustomListHandleColumnService
        .getByCustomListId(dto.getId());

    this.handleColumns = handleColumns.stream().map(t -> {
      HandleColumnBo handleColumn = new HandleColumnBo();
      handleColumn.setId(t.getId());
      handleColumn.setName(t.getName());
      handleColumn.setViewType(t.getViewType().getCode());
      handleColumn.setBtnType(t.getBtnType().getCode());
      if (t.getBtnType() == GenCustomListBtnType.CUSTOM_FORM) {
        GenCustomForm form = genCustomFormService.findById(t.getBtnConfig());
        if (form == null) {
          throw new DefaultClientException("操作列关联的自定义表单不存在！");
        }
        handleColumn.setCustomFormId(form.getId());
        handleColumn.setCustomFormName(form.getName());
        handleColumn.setRequestParam(t.getRequestParam());
      } else {
        handleColumn.setBtnConfig(t.getBtnConfig());
      }
      handleColumn.setWidth(t.getWidth());
      handleColumn.setIcon(t.getIcon());
      return handleColumn;
    }).collect(Collectors.toList());
  }

  @Data
  public static class ToolbarBo implements SuperBo {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private String id;

    /**
     * 显示名称
     */
    @ApiModelProperty(value = "显示名称")
    private String name;

    /**
     * 显示类型
     */
    @ApiModelProperty(value = "显示类型")
    private String viewType;

    /**
     * 按钮类型
     */
    @ApiModelProperty(value = "按钮类型")
    private Integer btnType;

    /**
     * 按钮配置
     */
    @ApiModelProperty(value = "按钮配置")
    private String btnConfig;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String requestParam;

    /**
     * 自定义表单ID
     */
    @ApiModelProperty(value = "自定义表单ID")
    private String customFormId;

    /**
     * 自定义表单名称
     */
    @ApiModelProperty(value = "自定义表单名称")
    private String customFormName;
  }

  @Data
  public static class QueryParamsBo implements SuperBo {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private String id;

    /**
     * 关联ID
     */
    @ApiModelProperty(value = "关联ID")
    private String relaId;

    /**
     * 前端显示
     */
    @ApiModelProperty(value = "前端显示")
    private Boolean frontShow;

    /**
     * 查询类型
     */
    @ApiModelProperty(value = "查询类型")
    private Integer queryType;

    /**
     * 表单宽度
     */
    @ApiModelProperty(value = "表单宽度")
    private Integer formWidth;

    /**
     * 默认值
     */
    @ApiModelProperty(value = "默认值")
    private String defaultValue;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private Integer type;

    /**
     * 数据类型
     */
    @ApiModelProperty("数据对象")
    private Integer dataType;

    /**
     * 显示类型
     */
    @ApiModelProperty("显示类型")
    private Integer viewType;
  }

  @Data
  public static class DetailBo implements SuperBo {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private String id;

    /**
     * 关联ID
     */
    @ApiModelProperty("关联ID")
    private String relaId;

    /**
     * 宽度类型
     */
    @ApiModelProperty(value = "宽度类型")
    private Integer widthType;

    /**
     * 是否页面排序
     */
    @ApiModelProperty(value = "是否页面排序")
    private Boolean sortable;

    /**
     * 宽度
     */
    @ApiModelProperty(value = "宽度")
    private Integer width;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private Integer type;

    /**
     * 格式化脚本
     */
    @ApiModelProperty("格式化脚本")
    private String formatter;
  }

  @Data
  public static class HandleColumnBo implements SuperBo {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private String id;

    /**
     * 显示名称
     */
    @ApiModelProperty(value = "显示名称")
    private String name;

    /**
     * 显示类型
     */
    @ApiModelProperty(value = "显示类型")
    private String viewType;

    /**
     * 按钮类型
     */
    @ApiModelProperty(value = "按钮类型")
    private Integer btnType;

    /**
     * 按钮配置
     */
    @ApiModelProperty(value = "按钮配置")
    private String btnConfig;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String requestParam;

    /**
     * 宽度
     */
    @ApiModelProperty(value = "宽度")
    private Integer width;

    /**
     * 自定义表单ID
     */
    @ApiModelProperty(value = "自定义表单ID")
    private String customFormId;

    /**
     * 自定义表单名称
     */
    @ApiModelProperty(value = "自定义表单名称")
    private String customFormName;
  }
}
