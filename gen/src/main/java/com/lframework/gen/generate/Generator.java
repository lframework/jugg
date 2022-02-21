package com.lframework.gen.generate;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.IdWorker;
import com.lframework.common.utils.StringUtil;
import com.lframework.gen.builders.DataObjectBuilder;
import com.lframework.gen.components.DataObject;
import com.lframework.gen.components.DataObjectColumn;
import com.lframework.gen.directives.FormatDirective;
import com.lframework.gen.dto.generate.GenerateDto;
import com.lframework.gen.enums.GenConvertType;
import com.lframework.gen.enums.GenDataType;
import com.lframework.gen.enums.GenKeyType;
import com.lframework.gen.enums.GenViewType;
import com.lframework.gen.generate.templates.*;
import com.lframework.starter.mybatis.constants.MyBatisStringPool;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.components.validation.Pattern;
import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.JsonUtil;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Generator {

    private DataObject dataObject;

    private Generator() {

    }

    public static Generator getInstance(String dataObjId) {

        DataObjectBuilder builder = ApplicationUtil.getBean(DataObjectBuilder.class);
        Generator generator = new Generator();

        generator.setDataObject(builder.build(dataObjId));

        return generator;
    }

    private void setDataObject(DataObject dataObject) {

        this.dataObject = dataObject;
    }

    public List<GenerateDto> generateAll() {

        List<GenerateDto> results = new ArrayList<>();
        // Java代码
        GenerateDto controllerJava = this.generateController();
        GenerateDto serviceJava = this.generateService();
        GenerateDto serviceImplJava = this.generateServiceImpl();
        GenerateDto mapperJava = this.generateMapper();
        GenerateDto mapperXml = this.generateListMapperXml();
        GenerateDto entityJava = this.generateEntity();
        GenerateDto dtoJava = this.generateDto();

        GenerateDto queryVoJava = this.generateQueryVo();
        GenerateDto createVoJava = this.generateCreateVo();
        GenerateDto updateVoJava = this.generateUpdateVo();
        GenerateDto queryBoJava = this.generateQueryBo();
        GenerateDto getBoJava = this.generateGetBo();

        results.add(controllerJava);
        results.add(serviceJava);
        results.add(serviceImplJava);
        results.add(mapperJava);
        results.add(mapperXml);
        results.add(entityJava);
        results.add(dtoJava);

        if (queryVoJava != null) {
            results.add(queryVoJava);
        }
        if (createVoJava != null) {
            results.add(createVoJava);
        }
        if (updateVoJava != null) {
            results.add(updateVoJava);
        }
        if (queryBoJava != null) {
            results.add(queryBoJava);
        }
        if (getBoJava != null) {
            results.add(getBoJava);
        }

        // Vue代码
        GenerateDto apiJs = this.generateApiJs();
        GenerateDto indexVue = this.generateIndexVue();
        GenerateDto addVue = this.generateAddVue();
        GenerateDto modifyVue = this.generateModifyVue();
        GenerateDto detailVue = this.generateDetailVue();

        results.add(apiJs);
        results.add(indexVue);
        if (addVue != null) {
            results.add(addVue);
        }
        if (modifyVue != null) {
            results.add(modifyVue);
        }
        if (detailVue != null) {
            results.add(detailVue);
        }

        // sql
        GenerateDto sql = this.generateSql();
        results.add(sql);

        return results;
    }

    /**
     * 生成Entity.java代码
     *
     * @return
     */
    public GenerateDto generateEntity() {

        EntityTemplate template = this.getEntityTemplate();

        String content = this.generate("entity.java.ftl", template);

        return this.buildGenerateResult(
                "java" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
                        + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator
                        + "entity", template.getClassName() + ".java", content);
    }

    /**
     * 生成Mapper.java代码
     *
     * @return
     */
    public GenerateDto generateMapper() {

        MapperTemplate template = this.getMapperTemplate();

        String content = this.generate("mapper.java.ftl", template);

        return this.buildGenerateResult(
                "java" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
                        + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator
                        + "mappers", template.getClassName() + "Mapper.java", content);
    }

    /**
     * 生成Mapper.xml代码
     *
     * @return
     */
    public GenerateDto generateListMapperXml() {

        MapperTemplate template = this.getMapperTemplate();

        String content = this.generate("mapper.list.xml.ftl", template);

        return this.buildGenerateResult(
                "java" + File.separator + "src" + File.separator + "main" + File.separator + "resources"
                        + File.separator + "mappers" + File.separator + template.getModuleName(),
                template.getClassName() + "Mapper.xml", content);
    }

    /**
     * 生成QueryVo.java代码
     *
     * @return
     */
    public GenerateDto generateQueryVo() {

        QueryParamsTemplate template = this.getQueryParamsTemplate();
        if (template == null) {
            return null;
        }

        String content = this.generate("queryvo.java.ftl", template);

        return this.buildGenerateResult(
                "java" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
                        + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator + "vo"
                        + File.separator + template.getModuleName() + File.separator + template.getBizName(),
                "Query" + template.getClassName() + "Vo.java", content);
    }

    /**
     * 生成Service.java代码
     *
     * @return
     */
    public GenerateDto generateService() {

        ServiceTemplate template = this.getServiceTemplate();

        String content = this.generate("service.java.ftl", template);

        return this.buildGenerateResult(
                "java" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
                        + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator
                        + "service" + File.separator + template.getModuleName(),
                "I" + template.getClassName() + "Service.java", content);
    }

    /**
     * 生成ServiceImpl.java代码
     *
     * @return
     */
    public GenerateDto generateServiceImpl() {

        ServiceTemplate template = this.getServiceTemplate();

        String content = this.generate("serviceimpl.java.ftl", template);

        return this.buildGenerateResult(
                "java" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
                        + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator + "impl"
                        + File.separator + template.getModuleName(), template.getClassName() + "ServiceImpl.java",
                content);
    }

    /**
     * 生成CreateVo.java代码
     *
     * @return
     */
    public GenerateDto generateCreateVo() {

        CreateTemplate template = this.getCreateTemplate();

        if (template == null) {
            return null;
        }

        String content = this.generate("createvo.java.ftl", template);

        return this.buildGenerateResult(
                "java" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
                        + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator + "vo"
                        + File.separator + template.getModuleName() + File.separator + template.getBizName(),
                "Create" + template.getClassName() + "Vo.java", content);
    }

    /**
     * 生成Dto.java代码
     *
     * @return
     */
    public GenerateDto generateDto() {
        // dto的生成参数与entity一样
        EntityTemplate template = this.getEntityTemplate();

        String content = this.generate("dto.java.ftl", template);

        return this.buildGenerateResult(
                "java" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
                        + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator + "dto"
                        + File.separator + template.getModuleName() + File.separator + template.getBizName(),
                template.getClassName() + "Dto.java", content);
    }

    /**
     * 生成UpdateVo.java代码
     *
     * @return
     */
    public GenerateDto generateUpdateVo() {

        UpdateTemplate template = this.getUpdateTemplate();

        if (template == null) {
            return null;
        }

        String content = this.generate("updatevo.java.ftl", template);

        return this.buildGenerateResult(
                "java" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
                        + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator + "vo"
                        + File.separator + template.getModuleName() + File.separator + template.getBizName(),
                "Update" + template.getClassName() + "Vo.java", content);
    }

    /**
     * 生成QueryBo.java代码
     *
     * @return
     */
    public GenerateDto generateQueryBo() {

        QueryTemplate template = this.getQueryTemplate();

        if (template == null) {
            return null;
        }

        String content = this.generate("querybo.java.ftl", template);

        return this.buildGenerateResult(
                "java" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
                        + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator + "bo"
                        + File.separator + template.getModuleName() + File.separator + template.getBizName(),
                "Query" + template.getClassName() + "Bo.java", content);
    }

    /**
     * 生成GetBo.java代码
     *
     * @return
     */
    public GenerateDto generateGetBo() {

        DetailTemplate template = this.getDetailTemplate();

        if (template == null) {
            return null;
        }

        String content = this.generate("getbo.java.ftl", template);

        return this.buildGenerateResult(
                "java" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
                        + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator + "bo"
                        + File.separator + template.getModuleName() + File.separator + template.getBizName(),
                "Get" + template.getClassName() + "Bo.java", content);
    }

    /**
     * 生成Controller.java代码
     *
     * @return
     */
    public GenerateDto generateController() {

        ControllerTemplate template = this.getControllerTemplate();

        String content = this.generate("controller.java.ftl", template);

        return this.buildGenerateResult(
                "java" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
                        + template.getPackageName().replaceAll("\\.", "\\" + File.separator) + File.separator
                        + "controller" + File.separator + template.getModuleName(),
                template.getClassName() + "Controller.java", content);
    }

    /**
     * 生成api.js代码
     *
     * @return
     */
    public GenerateDto generateApiJs() {

        ControllerTemplate template = this.getControllerTemplate();

        String content = this.generate("api.js.ftl", template);

        return this.buildGenerateResult(
                "vue" + File.separator + "src" + File.separator + "api" + File.separator + "modules" + File.separator
                        + template.getModuleName(), template.getBizName() + ".js", content);
    }

    /**
     * 生成index.vue代码
     *
     * @return
     */
    public GenerateDto generateIndexVue() {
        ControllerTemplate template = this.getControllerTemplate();
        String content = this.generate("index.vue.ftl", template);

        return this.buildGenerateResult(
                "vue" + File.separator + "src" + File.separator + "views" + File.separator
                        + template.getModuleName() + File.separator + template.getBizName(), "index.vue", content);
    }

    /**
     * add.vue代码
     *
     * @return
     */
    public GenerateDto generateAddVue() {

        CreateTemplate template = this.getCreateTemplate();

        if (template == null) {
            return null;
        }

        String content = this.generate("add.vue.ftl", template);

        return this.buildGenerateResult(
                "vue" + File.separator + "src" + File.separator + "views" + File.separator
                        + template.getModuleName() + File.separator + template.getBizName(), "add.vue", content);
    }

    /**
     * modify.vue代码
     *
     * @return
     */
    public GenerateDto generateModifyVue() {

        UpdateTemplate template = this.getUpdateTemplate();

        if (template == null) {
            return null;
        }

        String content = this.generate("modify.vue.ftl", template);

        return this.buildGenerateResult(
                "vue" + File.separator + "src" + File.separator + "views" + File.separator
                        + template.getModuleName() + File.separator + template.getBizName(), "modify.vue", content);
    }

    /**
     * detail.vue代码
     *
     * @return
     */
    public GenerateDto generateDetailVue() {

        DetailTemplate template = this.getDetailTemplate();

        if (template == null) {
            return null;
        }

        String content = this.generate("detail.vue.ftl", template);

        return this.buildGenerateResult(
                "vue" + File.separator + "src" + File.separator + "views" + File.separator
                        + template.getModuleName() + File.separator + template.getBizName(), "detail.vue", content);
    }

    /**
     * detail.vue代码
     *
     * @return
     */
    public GenerateDto generateSql() {

        SqlTemplate template = this.getSqlTemplate();

        String content = this.generate("sql.ftl", template);

        return this.buildGenerateResult(StringPool.EMPTY_STR, "sql.sql", content);
    }

    /**
     * 获取freeMarker Template
     *
     * @param templateName
     * @return
     */
    private Template getTemplate(String templateName) {

        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        cfg.setClassForTemplateLoading(Generator.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setSharedVariable(FormatDirective.DIRECTIVE_NAME, new FormatDirective());
        try {
            return cfg.getTemplate(templateName);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new DefaultSysException(e.getMessage());
        }
    }

    /**
     * Entity.java模板数据
     *
     * @return
     */
    private EntityTemplate getEntityTemplate() {

        EntityTemplate entityTemplate = new EntityTemplate();
        entityTemplate.setPackageName(dataObject.getGenerateInfo().getPackageName());
        entityTemplate.setTableName(dataObject.getTable().getTableName());
        entityTemplate.setClassName(dataObject.getGenerateInfo().getClassName());
        entityTemplate.setModuleName(dataObject.getGenerateInfo().getModuleName());
        entityTemplate.setBizName(dataObject.getGenerateInfo().getBizName());
        entityTemplate.setClassDescription(dataObject.getGenerateInfo().getClassDescription());
        entityTemplate.setAuthor(dataObject.getGenerateInfo().getAuthor());

        Set<String> importPackages = new HashSet<>();
        List<EntityTemplate.Column> columns = new ArrayList<>();
        for (DataObjectColumn column : dataObject.getColumns()) {
            EntityTemplate.Column columnObj = new EntityTemplate.Column();
            columnObj.setIsKey(column.getIsKey());
            if (columnObj.getIsKey()) {
                // 如果是主键，判断是否是自增主键
                columnObj.setAutoIncrKey(dataObject.getGenerateInfo().getKeyType() == GenKeyType.AUTO);
                if (columnObj.getAutoIncrKey()) {
                    importPackages.add(TableId.class.getName());
                    importPackages.add(IdType.class.getName());
                }
            }
            if (column.getFixEnum()) {
                // 如果是枚举类型
                columnObj.setType(column.getEnumBack().substring(column.getEnumBack().lastIndexOf(".") + 1));
                columnObj.setFrontType(column.getEnumFront());
                importPackages.add(column.getEnumBack());
            } else {
                columnObj.setType(column.getDataType().getDesc());
            }
            // 以下类型需要单独引包
            if (column.getDataType() == GenDataType.LOCAL_DATE) {
                importPackages.add(LocalDate.class.getName());
            } else if (column.getDataType() == GenDataType.LOCAL_DATE_TIME) {
                importPackages.add(LocalDateTime.class.getName());
            } else if (column.getDataType() == GenDataType.LOCAL_TIME) {
                importPackages.add(LocalTime.class.getName());
            } else if (column.getDataType() == GenDataType.BIG_DECIMAL) {
                importPackages.add(BigDecimal.class.getName());
            }
            columnObj.setName(column.getColumnName());
            // MybatisPlus默认命名规则是下划线转驼峰，所以如果不是这个规则的话，需要单独指定TableField和TableId
            columnObj.setColumnName(column.getTableColumn().getColumnName());
            columnObj
                    .setDefaultConvertType(dataObject.getTable().getConvertType() == GenConvertType.UNDERLINE_TO_CAMEL);
            if (!columnObj.getDefaultConvertType()) {
                importPackages.add(TableId.class.getName());
                importPackages.add(TableField.class.getName());
            }
            columnObj.setDescription(column.getName());
            if (!columnObj.getIsKey()) {
                // 如果不是主键，需要判断是否需要字段填充
                if (MyBatisStringPool.COLUMN_CREATE_BY.equals(columnObj.getName())
                        || MyBatisStringPool.COLUMN_CREATE_TIME.equals(columnObj.getName())) {
                    columnObj.setFill(Boolean.TRUE);
                    columnObj.setFillStrategy(FieldFill.INSERT.name());
                    importPackages.add(TableField.class.getName());
                    importPackages.add(FieldFill.class.getName());
                } else if (MyBatisStringPool.COLUMN_UPDATE_BY.equals(columnObj.getName())
                        || MyBatisStringPool.COLUMN_UPDATE_TIME.equals(columnObj.getName())) {
                    columnObj.setFill(Boolean.TRUE);
                    columnObj.setFillStrategy(FieldFill.INSERT_UPDATE.name());
                    importPackages.add(TableField.class.getName());
                    importPackages.add(FieldFill.class.getName());
                }
            }

            columns.add(columnObj);
        }
        entityTemplate.setColumns(columns);

        entityTemplate.setImportPackages(importPackages);

        return entityTemplate;
    }

    /**
     * Mapper.java模板数据
     *
     * @return
     */
    private MapperTemplate getMapperTemplate() {

        MapperTemplate mapperTemplate = new MapperTemplate();
        mapperTemplate.setPackageName(dataObject.getGenerateInfo().getPackageName());
        mapperTemplate.setClassName(dataObject.getGenerateInfo().getClassName());
        mapperTemplate.setModuleName(dataObject.getGenerateInfo().getModuleName());
        mapperTemplate.setBizName(dataObject.getGenerateInfo().getBizName());
        mapperTemplate.setClassDescription(dataObject.getGenerateInfo().getClassDescription());
        mapperTemplate.setAuthor(dataObject.getGenerateInfo().getAuthor());
        Set<String> importPackages = new HashSet<>();
        List<MapperTemplate.Key> keys = new ArrayList<>();
        for (DataObjectColumn column : dataObject.getColumns()) {
            if (column.getIsKey()) {
                MapperTemplate.Key key = new MapperTemplate.Key();
                // 主键不允许是枚举，所以直接取desc
                key.setType(column.getDataType().getDesc());
                key.setName(column.getColumnName());
                key.setColumnName(column.getTableColumn().getColumnName());
                // 以下类型需要单独引包
                if (column.getDataType() == GenDataType.LOCAL_DATE) {
                    importPackages.add(LocalDate.class.getName());
                } else if (column.getDataType() == GenDataType.LOCAL_DATE_TIME) {
                    importPackages.add(LocalDateTime.class.getName());
                } else if (column.getDataType() == GenDataType.LOCAL_TIME) {
                    importPackages.add(LocalTime.class.getName());
                } else if (column.getDataType() == GenDataType.BIG_DECIMAL) {
                    importPackages.add(BigDecimal.class.getName());
                }
                keys.add(key);
            }
        }

        mapperTemplate.setKeys(keys);
        List<MapperTemplate.OrderColumn> orderColumns = new ArrayList<>();
        for (DataObjectColumn column : dataObject.getColumns()) {
            if (!column.getIsOrder()) {
                continue;
            }
            MapperTemplate.OrderColumn orderColumn = new MapperTemplate.OrderColumn();
            orderColumn.setColumnName(column.getTableColumn().getColumnName());
            orderColumn.setOrderType(column.getOrderType().getCode());
            orderColumns.add(orderColumn);
        }
        mapperTemplate.setOrderColumns(orderColumns);
        mapperTemplate.setEntity(this.getEntityTemplate());
        mapperTemplate.setImportPackages(importPackages);
        mapperTemplate.setQueryParams(this.getQueryParamsTemplate());
        if (mapperTemplate.getQueryParams() != null) {
            mapperTemplate.getImportPackages().addAll(mapperTemplate.getQueryParams().getImportPackages());
        }

        return mapperTemplate;
    }

    private ServiceTemplate getServiceTemplate() {

        ServiceTemplate serviceTemplate = new ServiceTemplate();
        serviceTemplate.setPackageName(dataObject.getGenerateInfo().getPackageName());
        serviceTemplate.setClassName(dataObject.getGenerateInfo().getClassName());
        serviceTemplate.setClassNameProperty(
                dataObject.getGenerateInfo().getClassName().substring(0, 1).toLowerCase() + dataObject.getGenerateInfo()
                        .getClassName().substring(1));
        serviceTemplate.setModuleName(dataObject.getGenerateInfo().getModuleName());
        serviceTemplate.setBizName(dataObject.getGenerateInfo().getBizName());
        serviceTemplate.setClassDescription(dataObject.getGenerateInfo().getClassDescription());
        serviceTemplate.setAuthor(dataObject.getGenerateInfo().getAuthor());
        serviceTemplate.setIsCache(dataObject.getGenerateInfo().getIsCache());
        serviceTemplate.setHasDelete(dataObject.getGenerateInfo().getHasDelete());
        Set<String> importPackages = new HashSet<>();
        if (serviceTemplate.getHasDelete()) {
            importPackages.add(Transactional.class.getName());
        }
        List<ServiceTemplate.Key> keys = new ArrayList<>();
        for (DataObjectColumn column : dataObject.getColumns()) {
            if (column.getIsKey()) {
                ServiceTemplate.Key key = new ServiceTemplate.Key();
                // 主键不允许是枚举，所以直接取desc
                key.setType(column.getDataType().getDesc());
                key.setName(column.getColumnName());
                key.setNameProperty(column.getColumnName().substring(0, 1).toUpperCase() + column.getColumnName().substring(1));
                key.setColumnName(column.getTableColumn().getColumnName());
                // 以下类型需要单独引包
                if (column.getDataType() == GenDataType.LOCAL_DATE) {
                    importPackages.add(LocalDate.class.getName());
                } else if (column.getDataType() == GenDataType.LOCAL_DATE_TIME) {
                    importPackages.add(LocalDateTime.class.getName());
                } else if (column.getDataType() == GenDataType.LOCAL_TIME) {
                    importPackages.add(LocalTime.class.getName());
                } else if (column.getDataType() == GenDataType.BIG_DECIMAL) {
                    importPackages.add(BigDecimal.class.getName());
                }
                keys.add(key);
            }
        }

        serviceTemplate.setKeys(keys);
        serviceTemplate.setImportPackages(importPackages);
        serviceTemplate.setQueryParams(this.getQueryParamsTemplate());
        serviceTemplate.setCreate(this.getCreateTemplate());
        serviceTemplate.setUpdate(this.getUpdateTemplate());
        if (serviceTemplate.getQueryParams() != null) {
            serviceTemplate.getImportPackages().addAll(serviceTemplate.getQueryParams().getImportPackages());
        }
        if (serviceTemplate.getCreate() != null) {
            serviceTemplate.getImportPackages().addAll(serviceTemplate.getCreate().getImportPackages());
        }
        if (serviceTemplate.getUpdate() != null) {
            serviceTemplate.getImportPackages().addAll(serviceTemplate.getUpdate().getImportPackages());
        }

        return serviceTemplate;
    }

    private QueryParamsTemplate getQueryParamsTemplate() {

        List<DataObjectColumn> targetColumns = dataObject.getColumns().stream()
                .filter(t -> t.getQueryParamsConfig() != null)
                .sorted(Comparator.comparing(t -> t.getQueryParamsConfig().getOrderNo())).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(targetColumns)) {
            return null;
        }
        QueryParamsTemplate queryParamsTemplate = new QueryParamsTemplate();
        queryParamsTemplate.setPackageName(dataObject.getGenerateInfo().getPackageName());
        queryParamsTemplate.setClassName(dataObject.getGenerateInfo().getClassName());
        queryParamsTemplate.setModuleName(dataObject.getGenerateInfo().getModuleName());
        queryParamsTemplate.setBizName(dataObject.getGenerateInfo().getBizName());
        queryParamsTemplate.setClassDescription(dataObject.getGenerateInfo().getClassDescription());
        queryParamsTemplate.setAuthor(dataObject.getGenerateInfo().getAuthor());
        Set<String> importPackages = new HashSet<>();
        importPackages.add(TypeMismatch.class.getName());
        List<QueryParamsTemplate.Column> columns = new ArrayList<>();
        for (DataObjectColumn column : targetColumns) {
            QueryParamsTemplate.Column columnObj = new QueryParamsTemplate.Column();
            if (column.getFixEnum()) {
                // 如果是枚举类型
                columnObj.setType(column.getEnumBack().substring(column.getEnumBack().lastIndexOf(".") + 1));
                columnObj.setFrontType(column.getEnumFront());
                columnObj.setViewType(column.getViewType().getCode());
                columnObj.setEnumCodeType(column.getDataType().getDesc());
                importPackages.add(column.getEnumBack());
                importPackages.add(IsEnum.class.getName());
            } else {
                columnObj.setType(column.getDataType().getDesc());
                columnObj.setViewType(column.getViewType().getCode());
            }
            // 以下类型需要单独引包
            if (column.getDataType() == GenDataType.LOCAL_DATE) {
                importPackages.add(LocalDate.class.getName());
            } else if (column.getDataType() == GenDataType.LOCAL_DATE_TIME) {
                importPackages.add(LocalDateTime.class.getName());
            } else if (column.getDataType() == GenDataType.LOCAL_TIME) {
                importPackages.add(LocalTime.class.getName());
            } else if (column.getDataType() == GenDataType.BIG_DECIMAL) {
                importPackages.add(BigDecimal.class.getName());
            }
            columnObj.setName(column.getColumnName());
            columnObj.setColumnName(column.getTableColumn().getColumnName());
            columnObj.setQueryType(column.getQueryParamsConfig().getQueryType().getCode());
            columnObj.setNameProperty(
                    column.getColumnName().substring(0, 1).toUpperCase() + column.getColumnName().substring(1));
            columnObj.setDescription(column.getName());
            columnObj.setHasAvailableTag(column.getViewType() == GenViewType.SELECT && column.getDataType() == GenDataType.BOOLEAN && "available".equals(column.getColumnName()));
            columnObj.setFixEnum(column.getFixEnum());
            if (!StringUtil.isBlank(column.getRegularExpression())) {
                columnObj.setRegularExpression(column.getRegularExpression());
                importPackages.add(Pattern.class.getName());
            }

            columns.add(columnObj);
        }
        queryParamsTemplate.setColumns(columns);
        queryParamsTemplate.setImportPackages(importPackages);

        return queryParamsTemplate;
    }

    private CreateTemplate getCreateTemplate() {

        List<DataObjectColumn> targetColumns = dataObject.getColumns().stream().filter(t -> t.getCreateConfig() != null)
                .sorted(Comparator.comparing(t -> t.getCreateConfig().getOrderNo())).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(targetColumns)) {
            return null;
        }
        Set<String> importPackages = new HashSet<>();
        CreateTemplate createTemplate = new CreateTemplate();
        createTemplate.setAppointId(dataObject.getGenerateInfo().getKeyType() != GenKeyType.AUTO);
        if (dataObject.getGenerateInfo().getKeyType() == GenKeyType.UUID) {
            // 如果是UUID，则引入IdUtil包
            importPackages.add(IdUtil.class.getName());
            createTemplate.setIdCode(IdUtil.class.getSimpleName() + ".getId()");
        } else if (dataObject.getGenerateInfo().getKeyType() == GenKeyType.SNOW_FLAKE) {
            // 如果是雪花算法，则引入IdWorker包
            importPackages.add(IdWorker.class.getName());
            importPackages.add(ApplicationUtil.class.getName());
            createTemplate.setIdCode(
                    ApplicationUtil.class.getSimpleName() + ".getBean(" + IdWorker.class.getSimpleName()
                            + ".class).nextIdStr()");
        }
        createTemplate.setPackageName(dataObject.getGenerateInfo().getPackageName());
        createTemplate.setClassName(dataObject.getGenerateInfo().getClassName());
        createTemplate.setModuleName(dataObject.getGenerateInfo().getModuleName());
        createTemplate.setBizName(dataObject.getGenerateInfo().getBizName());
        createTemplate.setClassDescription(dataObject.getGenerateInfo().getClassDescription());
        createTemplate.setAuthor(dataObject.getGenerateInfo().getAuthor());
        importPackages.add(TypeMismatch.class.getName());

        List<CreateTemplate.Column> columns = new ArrayList<>();
        for (DataObjectColumn column : targetColumns) {
            CreateTemplate.Column columnObj = new CreateTemplate.Column();
            columnObj.setIsKey(column.getIsKey());
            columnObj.setRequired(column.getCreateConfig().getRequired());
            if (column.getFixEnum()) {
                // 如果是枚举类型
                columnObj.setType(column.getEnumBack().substring(column.getEnumBack().lastIndexOf(".") + 1));
                columnObj.setFrontType(column.getEnumFront());
                columnObj.setViewType(column.getViewType().getCode());
                importPackages.add(column.getEnumBack());
                importPackages.add(EnumUtil.class.getName());
            } else {
                columnObj.setType(column.getDataType().getDesc());
                columnObj.setViewType(column.getViewType().getCode());
            }
            if (column.getViewType() == GenViewType.DATE_RANGE) {
                if (column.getDataType() == GenDataType.LOCAL_DATE_TIME) {
                    columnObj.setViewType(GenViewType.DATETIME.getCode());
                }else {
                    columnObj.setViewType(GenViewType.DATE.getCode());
                }
            }
            // 以下类型需要单独引包
            if (column.getDataType() == GenDataType.LOCAL_DATE) {
                importPackages.add(LocalDate.class.getName());
            } else if (column.getDataType() == GenDataType.LOCAL_DATE_TIME) {
                importPackages.add(LocalDateTime.class.getName());
            } else if (column.getDataType() == GenDataType.LOCAL_TIME) {
                importPackages.add(LocalTime.class.getName());
            } else if (column.getDataType() == GenDataType.BIG_DECIMAL) {
                importPackages.add(BigDecimal.class.getName());
            }
            columnObj.setFixEnum(column.getFixEnum());
            if (columnObj.getFixEnum()) {
                columnObj.setEnumCodeType(column.getDataType().getDesc());
            }
            if (columnObj.getRequired()) {
                // 如果必填，那么需要引Validation注解包
                if (column.getDataType() == GenDataType.STRING) {
                    // 如果是String，则引@NotBlank注解
                    columnObj.setValidateAnno(NotBlank.class.getSimpleName());
                    importPackages.add(NotBlank.class.getName());
                } else {
                    // 否则引@NotNull注解
                    columnObj.setValidateAnno(NotNull.class.getSimpleName());
                    importPackages.add(NotNull.class.getName());
                }

                if (column.getViewType() == GenViewType.SELECT) {
                    columnObj.setValidateMsg("请选择");
                } else {
                    columnObj.setValidateMsg("请输入");
                }

                if (columnObj.getFixEnum()) {
                    // 如果是内置枚举，那么引IsEnum注解包
                    importPackages.add(IsEnum.class.getName());
                }
            }
            columnObj.setName(column.getColumnName());
            columnObj.setColumnName(column.getTableColumn().getColumnName());
            columnObj.setNameProperty(
                    column.getColumnName().substring(0, 1).toUpperCase() + column.getColumnName().substring(1));
            columnObj.setDescription(column.getName());
            columnObj.setHasAvailableTag(column.getViewType() == GenViewType.SELECT && column.getDataType() == GenDataType.BOOLEAN && "available".equals(column.getColumnName()));
            if (!StringUtil.isBlank(column.getRegularExpression())) {
                columnObj.setRegularExpression(column.getRegularExpression());
                importPackages.add(Pattern.class.getName());
            }

            columns.add(columnObj);
        }

        createTemplate.setColumns(columns);
        createTemplate.setImportPackages(importPackages);
        List<DataObjectColumn> keyColumns = dataObject.getColumns().stream().filter(DataObjectColumn::getIsKey)
                .collect(Collectors.toList());
        List<CreateTemplate.Key> keys = keyColumns.stream().map(t -> {
            CreateTemplate.Key key = new CreateTemplate.Key();
            // 主键不会是枚举
            key.setType(t.getDataType().getDesc());
            key.setName(t.getColumnName());
            key.setNameProperty(t.getColumnName().substring(0, 1).toUpperCase() + t.getColumnName().substring(1));
            key.setColumnName(t.getTableColumn().getColumnName());
            key.setDescription(t.getName());

            return key;
        }).collect(Collectors.toList());

        createTemplate.setKeys(keys);

        return createTemplate;
    }

    private UpdateTemplate getUpdateTemplate() {

        List<DataObjectColumn> targetColumns = dataObject.getColumns().stream().filter(t -> t.getUpdateConfig() != null)
                .sorted(Comparator.comparing(t -> t.getUpdateConfig().getOrderNo())).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(targetColumns)) {
            return null;
        }
        Set<String> importPackages = new HashSet<>();
        UpdateTemplate updateTemplate = new UpdateTemplate();
        updateTemplate.setPackageName(dataObject.getGenerateInfo().getPackageName());
        updateTemplate.setClassName(dataObject.getGenerateInfo().getClassName());
        updateTemplate.setModuleName(dataObject.getGenerateInfo().getModuleName());
        updateTemplate.setBizName(dataObject.getGenerateInfo().getBizName());
        updateTemplate.setClassDescription(dataObject.getGenerateInfo().getClassDescription());
        updateTemplate.setAuthor(dataObject.getGenerateInfo().getAuthor());
        importPackages.add(TypeMismatch.class.getName());

        List<UpdateTemplate.Column> columns = new ArrayList<>();
        for (DataObjectColumn column : targetColumns) {
            UpdateTemplate.Column columnObj = new UpdateTemplate.Column();
            columnObj.setIsKey(column.getIsKey());
            columnObj.setRequired(column.getUpdateConfig().getRequired());
            if (column.getFixEnum()) {
                // 如果是枚举类型
                columnObj.setType(column.getEnumBack().substring(column.getEnumBack().lastIndexOf(".") + 1));
                columnObj.setFrontType(column.getEnumFront());
                columnObj.setViewType(column.getViewType().getCode());
                importPackages.add(column.getEnumBack());
                importPackages.add(EnumUtil.class.getName());
            } else {
                columnObj.setType(column.getDataType().getDesc());
                columnObj.setViewType(column.getViewType().getCode());
            }
            if (column.getViewType() == GenViewType.DATE_RANGE) {
                if (column.getDataType() == GenDataType.LOCAL_DATE_TIME) {
                    columnObj.setViewType(GenViewType.DATETIME.getCode());
                }else {
                    columnObj.setViewType(GenViewType.DATE.getCode());
                }
            }
            // 以下类型需要单独引包
            if (column.getDataType() == GenDataType.LOCAL_DATE) {
                importPackages.add(LocalDate.class.getName());
            } else if (column.getDataType() == GenDataType.LOCAL_DATE_TIME) {
                importPackages.add(LocalDateTime.class.getName());
            } else if (column.getDataType() == GenDataType.LOCAL_TIME) {
                importPackages.add(LocalTime.class.getName());
            } else if (column.getDataType() == GenDataType.BIG_DECIMAL) {
                importPackages.add(BigDecimal.class.getName());
            }
            columnObj.setFixEnum(column.getFixEnum());
            if (columnObj.getFixEnum()) {
                columnObj.setEnumCodeType(column.getDataType().getDesc());
            }
            if (columnObj.getRequired()) {
                // 如果必填，那么需要引Validation注解包
                if (column.getDataType() == GenDataType.STRING) {
                    // 如果是String，则引@NotBlank注解
                    columnObj.setValidateAnno(NotBlank.class.getSimpleName());
                    importPackages.add(NotBlank.class.getName());
                } else {
                    // 否则引@NotNull注解
                    columnObj.setValidateAnno(NotNull.class.getSimpleName());
                    importPackages.add(NotNull.class.getName());
                }

                if (column.getViewType() == GenViewType.SELECT) {
                    columnObj.setValidateMsg("请选择");
                } else {
                    columnObj.setValidateMsg("请输入");
                }

                if (columnObj.getFixEnum()) {
                    // 如果是内置枚举，那么引IsEnum注解包
                    importPackages.add(IsEnum.class.getName());
                }
            }
            columnObj.setName(column.getColumnName());
            columnObj.setColumnName(column.getTableColumn().getColumnName());
            columnObj.setNameProperty(
                    column.getColumnName().substring(0, 1).toUpperCase() + column.getColumnName().substring(1));
            columnObj.setDescription(column.getName());
            columnObj.setHasAvailableTag(column.getViewType() == GenViewType.SELECT && column.getDataType() == GenDataType.BOOLEAN && "available".equals(column.getColumnName()));
            if (!StringUtil.isBlank(column.getRegularExpression())) {
                columnObj.setRegularExpression(column.getRegularExpression());
                importPackages.add(Pattern.class.getName());
            }

            columns.add(columnObj);
        }

        updateTemplate.setColumns(columns);
        updateTemplate.setImportPackages(importPackages);
        List<DataObjectColumn> keyColumns = dataObject.getColumns().stream().filter(DataObjectColumn::getIsKey)
                .collect(Collectors.toList());
        List<UpdateTemplate.Key> keys = keyColumns.stream().map(t -> {
            UpdateTemplate.Key key = new UpdateTemplate.Key();
            // 主键不会是枚举
            key.setType(t.getDataType().getDesc());
            key.setName(t.getColumnName());
            key.setNameProperty(t.getColumnName().substring(0, 1).toUpperCase() + t.getColumnName().substring(1));
            key.setColumnName(t.getTableColumn().getColumnName());
            key.setDescription(t.getName());
            if (t.getDataType() == GenDataType.STRING) {
                // 如果是String，则引@NotBlank注解
                importPackages.add(NotBlank.class.getName());
            } else {
                // 否则引@NotNull注解
                importPackages.add(NotNull.class.getName());
            }
            return key;
        }).collect(Collectors.toList());
        updateTemplate.setKeys(keys);

        return updateTemplate;
    }

    private QueryTemplate getQueryTemplate() {

        List<DataObjectColumn> targetColumns = dataObject.getColumns().stream().filter(t -> t.getQueryConfig() != null)
                .sorted(Comparator.comparing(t -> t.getQueryConfig().getOrderNo())).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(targetColumns)) {
            return null;
        }
        QueryTemplate queryTemplate = new QueryTemplate();
        queryTemplate.setPackageName(dataObject.getGenerateInfo().getPackageName());
        queryTemplate.setClassName(dataObject.getGenerateInfo().getClassName());
        queryTemplate.setModuleName(dataObject.getGenerateInfo().getModuleName());
        queryTemplate.setBizName(dataObject.getGenerateInfo().getBizName());
        queryTemplate.setClassDescription(dataObject.getGenerateInfo().getClassDescription());
        queryTemplate.setAuthor(dataObject.getGenerateInfo().getAuthor());

        Set<String> importPackages = new HashSet<>();
        importPackages.add(TypeMismatch.class.getName());
        List<QueryTemplate.Column> columns = new ArrayList<>();
        for (DataObjectColumn column : targetColumns) {
            QueryTemplate.Column columnObj = new QueryTemplate.Column();
            if (column.getFixEnum()) {
                // 如果是枚举类型
                columnObj.setType(column.getEnumBack().substring(column.getEnumBack().lastIndexOf(".") + 1));
                columnObj.setFrontType(column.getEnumFront());
                columnObj.setViewType(column.getViewType().getCode());
                importPackages.add(column.getEnumBack());
                importPackages.add(EnumUtil.class.getName());
            } else {
                columnObj.setType(column.getDataType().getDesc());
                columnObj.setIsNumberType(GenDataType.isNumberType(column.getDataType()));
                columnObj.setViewType(column.getViewType().getCode());
                columnObj.setHasAvailableTag(column.getViewType() == GenViewType.SELECT && column.getDataType() == GenDataType.BOOLEAN && "available".equals(column.getColumnName()));
            }
            // 以下类型需要单独引包
            if (column.getDataType() == GenDataType.LOCAL_DATE) {
                importPackages.add(LocalDate.class.getName());
            } else if (column.getDataType() == GenDataType.LOCAL_DATE_TIME) {
                importPackages.add(LocalDateTime.class.getName());
            } else if (column.getDataType() == GenDataType.LOCAL_TIME) {
                importPackages.add(LocalTime.class.getName());
            } else if (column.getDataType() == GenDataType.BIG_DECIMAL) {
                importPackages.add(BigDecimal.class.getName());
            }
            columnObj.setFixEnum(column.getFixEnum());
            if (columnObj.getFixEnum()) {
                columnObj.setEnumCodeType(column.getDataType().getDesc());
            }
            columnObj.setName(column.getColumnName());
            columnObj.setNameProperty(
                    column.getColumnName().substring(0, 1).toUpperCase() + column.getColumnName().substring(1));
            columnObj.setWidthType(column.getQueryConfig().getWidthType().getCode());
            columnObj.setWidth(column.getQueryConfig().getWidth());
            columnObj.setSortable(column.getQueryConfig().getSortable());
            columnObj.setDescription(column.getName());

            columns.add(columnObj);
        }

        queryTemplate.setColumns(columns);
        queryTemplate.setHasFixEnum(columns.stream().anyMatch(QueryTemplate.Column::getFixEnum));

        List<DataObjectColumn> keyColumns = dataObject.getColumns().stream().filter(DataObjectColumn::getIsKey)
                .collect(Collectors.toList());
        List<QueryTemplate.Key> keys = keyColumns.stream().map(t -> {
            QueryTemplate.Key key = new QueryTemplate.Key();
            // 主键不会是枚举
            key.setType(t.getDataType().getDesc());
            key.setName(t.getColumnName());
            key.setNameProperty(t.getColumnName().substring(0, 1).toUpperCase() + t.getColumnName().substring(1));
            key.setDescription(t.getName());

            return key;
        }).collect(Collectors.toList());

        queryTemplate.setKeys(keys);

        queryTemplate.setImportPackages(importPackages);

        return queryTemplate;
    }

    private DetailTemplate getDetailTemplate() {

        List<DataObjectColumn> targetColumns = dataObject.getColumns().stream().filter(t -> t.getDetailConfig() != null)
                .sorted(Comparator.comparing(t -> t.getDetailConfig().getOrderNo())).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(targetColumns)) {
            return null;
        }
        DetailTemplate detailTemplate = new DetailTemplate();
        detailTemplate.setPackageName(dataObject.getGenerateInfo().getPackageName());
        detailTemplate.setClassName(dataObject.getGenerateInfo().getClassName());
        detailTemplate.setModuleName(dataObject.getGenerateInfo().getModuleName());
        detailTemplate.setBizName(dataObject.getGenerateInfo().getBizName());
        detailTemplate.setClassDescription(dataObject.getGenerateInfo().getClassDescription());
        detailTemplate.setAuthor(dataObject.getGenerateInfo().getAuthor());

        Set<String> importPackages = new HashSet<>();
        importPackages.add(TypeMismatch.class.getName());
        List<DetailTemplate.Column> columns = new ArrayList<>();
        for (DataObjectColumn column : targetColumns) {
            DetailTemplate.Column columnObj = new DetailTemplate.Column();
            if (column.getFixEnum()) {
                // 如果是枚举类型
                columnObj.setType(column.getEnumBack().substring(column.getEnumBack().lastIndexOf(".") + 1));
                columnObj.setFrontType(column.getEnumFront());
                importPackages.add(column.getEnumBack());
                importPackages.add(EnumUtil.class.getName());
            } else {
                columnObj.setType(column.getDataType().getDesc());
                columnObj.setHasAvailableTag(column.getViewType() == GenViewType.SELECT && column.getDataType() == GenDataType.BOOLEAN && "available".equals(column.getColumnName()));
                if (columnObj.getHasAvailableTag()) {
                    detailTemplate.setHasAvailableTag(Boolean.TRUE);
                }
            }
            // 以下类型需要单独引包
            if (column.getDataType() == GenDataType.LOCAL_DATE) {
                importPackages.add(LocalDate.class.getName());
            } else if (column.getDataType() == GenDataType.LOCAL_DATE_TIME) {
                importPackages.add(LocalDateTime.class.getName());
            } else if (column.getDataType() == GenDataType.LOCAL_TIME) {
                importPackages.add(LocalTime.class.getName());
            } else if (column.getDataType() == GenDataType.BIG_DECIMAL) {
                importPackages.add(BigDecimal.class.getName());
            }
            columnObj.setFixEnum(column.getFixEnum());
            if (columnObj.getFixEnum()) {
                columnObj.setEnumCodeType(column.getDataType().getDesc());
            }
            columnObj.setName(column.getColumnName());
            columnObj.setNameProperty(
                    column.getColumnName().substring(0, 1).toUpperCase() + column.getColumnName().substring(1));
            columnObj.setDescription(column.getName());
            columnObj.setSpan(column.getDetailConfig().getSpan());

            columns.add(columnObj);
        }

        detailTemplate.setColumns(columns);
        detailTemplate.setHasFixEnum(columns.stream().anyMatch(DetailTemplate.Column::getFixEnum));
        detailTemplate.setDetailSpan(dataObject.getGenerateInfo().getDetailSpan());
        List<DataObjectColumn> keyColumns = dataObject.getColumns().stream().filter(DataObjectColumn::getIsKey)
                .collect(Collectors.toList());
        List<DetailTemplate.Key> keys = keyColumns.stream().map(t -> {
            DetailTemplate.Key key = new DetailTemplate.Key();
            // 主键不会是枚举
            key.setType(t.getDataType().getDesc());
            key.setName(t.getColumnName());
            key.setNameProperty(t.getColumnName().substring(0, 1).toUpperCase() + t.getColumnName().substring(1));
            key.setDescription(t.getName());

            return key;
        }).collect(Collectors.toList());

        detailTemplate.setKeys(keys);

        detailTemplate.setImportPackages(importPackages);

        return detailTemplate;
    }

    private ControllerTemplate getControllerTemplate() {

        Set<String> importPackages = new HashSet<>();
        ControllerTemplate controllerTemplate = new ControllerTemplate();
        controllerTemplate.setPackageName(dataObject.getGenerateInfo().getPackageName());
        controllerTemplate.setClassName(dataObject.getGenerateInfo().getClassName());
        controllerTemplate.setClassNameProperty(
                dataObject.getGenerateInfo().getClassName().substring(0, 1).toLowerCase() + dataObject.getGenerateInfo()
                        .getClassName().substring(1));
        controllerTemplate.setModuleName(dataObject.getGenerateInfo().getModuleName());
        controllerTemplate.setBizName(dataObject.getGenerateInfo().getBizName());
        controllerTemplate.setClassDescription(dataObject.getGenerateInfo().getClassDescription());
        controllerTemplate.setAuthor(dataObject.getGenerateInfo().getAuthor());
        controllerTemplate.setHasDelete(dataObject.getGenerateInfo().getHasDelete());
        if (controllerTemplate.getHasDelete()) {
            importPackages.add(DeleteMapping.class.getName());
        }
        List<DataObjectColumn> keyColumns = dataObject.getColumns().stream().filter(DataObjectColumn::getIsKey)
                .collect(Collectors.toList());
        List<ControllerTemplate.Key> keys = keyColumns.stream().map(t -> {
            ControllerTemplate.Key key = new ControllerTemplate.Key();
            // 主键不会是枚举
            key.setType(t.getDataType().getDesc());
            key.setName(t.getColumnName());

            if (t.getDataType() == GenDataType.STRING) {
                importPackages.add(NotBlank.class.getName());
            }else {
                importPackages.add(NotNull.class.getName());
            }

            return key;
        }).collect(Collectors.toList());

        controllerTemplate.setKeys(keys);
        controllerTemplate.setImportPackages(importPackages);
        controllerTemplate.setCreate(this.getCreateTemplate());
        controllerTemplate.setUpdate(this.getUpdateTemplate());
        controllerTemplate.setQuery(this.getQueryTemplate());
        controllerTemplate.setQueryParams(this.getQueryParamsTemplate());
        controllerTemplate.setDetail(this.getDetailTemplate());
        if (controllerTemplate.getCreate() != null) {
            importPackages.addAll(controllerTemplate.getCreate().getImportPackages());
        }
        if (controllerTemplate.getUpdate() != null) {
            importPackages.addAll(controllerTemplate.getUpdate().getImportPackages());
        }
        if (controllerTemplate.getQuery() != null) {
            importPackages.addAll(controllerTemplate.getQuery().getImportPackages());
            if (!controllerTemplate.getHasAvailableTag()) {
                controllerTemplate.setHasAvailableTag(controllerTemplate.getQuery().getColumns().stream().anyMatch(QueryTemplate.Column::getHasAvailableTag));
            }
        }
        if (controllerTemplate.getQueryParams() != null) {
            importPackages.addAll(controllerTemplate.getQueryParams().getImportPackages());
            if (!controllerTemplate.getHasAvailableTag()) {
                controllerTemplate.setHasAvailableTag(controllerTemplate.getQueryParams().getColumns().stream().anyMatch(QueryParamsTemplate.Column::getHasAvailableTag));
            }
        }
        if (controllerTemplate.getDetail() != null) {
            importPackages.addAll(controllerTemplate.getDetail().getImportPackages());
        }

        return controllerTemplate;
    }

    private SqlTemplate getSqlTemplate() {
        SqlTemplate sqlTemplate = new SqlTemplate();
        sqlTemplate.setModuleName(dataObject.getGenerateInfo().getModuleName());
        sqlTemplate.setBizName(dataObject.getGenerateInfo().getBizName());
        sqlTemplate.setClassName(dataObject.getGenerateInfo().getClassName());
        sqlTemplate.setClassDescription(dataObject.getGenerateInfo().getClassDescription());
        sqlTemplate.setParentMenuId(dataObject.getGenerateInfo().getParentMenuId());
        sqlTemplate.setMenuId(dataObject.getGenerateInfo().getId());
        sqlTemplate.setMenuCode(dataObject.getGenerateInfo().getMenuCode());
        sqlTemplate.setMenuName(dataObject.getGenerateInfo().getMenuName());
        sqlTemplate.setCreate(this.getCreateTemplate());
        sqlTemplate.setUpdate(this.getUpdateTemplate());

        return sqlTemplate;
    }

    /**
     * 生成代码
     *
     * @param templateName 模板名称
     * @param data         数据
     * @return
     */
    private String generate(String templateName, Object data) {

        Template template = this.getTemplate(templateName);
        Map root = JsonUtil.convert(data, Map.class);
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);

        loadStaticClasses(root);

        try {
            template.process(root, writer);
        } catch (TemplateException | IOException e) {
            log.error(e.getMessage(), e);
            throw new DefaultSysException(e.getMessage());
        }

        return stringWriter.toString();
    }

    private GenerateDto buildGenerateResult(String path, String fileName, String content) {

        if (StringUtil.isBlank(content)) {
            return null;
        }
        GenerateDto result = new GenerateDto();
        result.setPath(path);
        result.setFileName(fileName);
        result.setContent(content);

        return result;
    }

    private void loadStaticClasses(Map root) {
        BeansWrapper wrapper = BeansWrapper.getDefaultInstance();

        TemplateHashModel staticModels = wrapper.getStaticModels();

        TemplateHashModel fileStatics = null;
        try {
            fileStatics = (TemplateHashModel) staticModels.get(IdUtil.class.getName());
        } catch (TemplateModelException e) {
            log.error("加载静态类失败", e);
            throw new DefaultSysException("加载静态类失败");
        }

        root.put(IdUtil.class.getSimpleName(), fileStatics);
    }
}
