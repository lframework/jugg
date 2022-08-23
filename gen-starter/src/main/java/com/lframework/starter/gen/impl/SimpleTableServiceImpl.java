package com.lframework.starter.gen.impl;

import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.gen.converters.GenStringConverter;
import com.lframework.starter.gen.dto.simpledb.SimpleTableColumnDto;
import com.lframework.starter.gen.dto.simpledb.SimpleTableDto;
import com.lframework.starter.gen.entity.GenSimpleTable;
import com.lframework.starter.gen.enums.DataObjectGenStatus;
import com.lframework.starter.gen.enums.GenConvertType;
import com.lframework.starter.gen.enums.GenKeyType;
import com.lframework.starter.gen.enums.GenTemplateType;
import com.lframework.starter.gen.mappers.GenSimpleTableMapper;
import com.lframework.starter.gen.service.IDataObjectService;
import com.lframework.starter.gen.service.IGenerateInfoService;
import com.lframework.starter.gen.service.ISimpleTableColumnService;
import com.lframework.starter.gen.service.ISimpleTableService;
import com.lframework.starter.gen.vo.dataobj.UpdateGenerateInfoVo;
import com.lframework.starter.gen.vo.simpledb.CreateSimpleTableVo;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.EnumUtil;
import java.util.List;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimpleTableServiceImpl extends BaseMpServiceImpl<GenSimpleTableMapper, GenSimpleTable>
    implements ISimpleTableService {

  @Autowired
  private ISimpleTableColumnService simpleTableColumnService;

  @Autowired
  private IDataObjectService dataObjectService;

  @Autowired
  private IGenerateInfoService generateInfoService;

  @Override
  public SimpleTableDto getByDataObjId(String id) {

    SimpleTableDto table = getBaseMapper().getByDataObjId(id);
    if (ObjectUtil.isNull(table)) {
      return table;
    }

    List<SimpleTableColumnDto> columns = simpleTableColumnService.getByTableId(table.getId());
    table.setColumns(columns);

    return table;
  }

  @Transactional
  @Override
  public String create(@NonNull CreateSimpleTableVo vo) {

    SimpleTableDto checkTable = this.getByDataObjId(vo.getDataObjId());
    if (!ObjectUtil.isNull(checkTable)) {
      throw new DefaultClientException("数据库表已设置，不允许重复设置！");
    }

    SimpleTableDto table = getBaseMapper().get(vo.getTableSchema(), vo.getTableName());
    if (ObjectUtil.isNull(table)) {
      throw new DefaultClientException(
          "数据库表【" + vo.getTableSchema() + "." + vo.getTableName() + "】不存在！");
    }

    table.setId(vo.getDataObjId());

    GenSimpleTable simpleTable = new GenSimpleTable();
    simpleTable.setId(table.getId());
    simpleTable.setTableSchema(table.getTableSchema());
    simpleTable.setTableName(table.getTableName());
    simpleTable.setEngine(table.getEngine());
    simpleTable.setTableCollation(table.getTableCollation());
    simpleTable.setTableComment(table.getTableComment());
    simpleTable.setConvertType(EnumUtil.getByCode(GenConvertType.class, vo.getConvertType()));

    getBaseMapper().insert(simpleTable);

    //创建列
    simpleTableColumnService.create(simpleTable.getId(), vo);

    //设置DataObj状态
    dataObjectService.setStatus(vo.getDataObjId(), DataObjectGenStatus.SET_TABLE);

    // 设置默认的基础设置
    UpdateGenerateInfoVo updateGenerateInfoVo = new UpdateGenerateInfoVo();
    updateGenerateInfoVo.setTemplateType(GenTemplateType.LIST.getCode());
    updateGenerateInfoVo.setPackageName("com.lframework");
    updateGenerateInfoVo.setModuleName(StringPool.EMPTY_STR);
    updateGenerateInfoVo.setBizName(GenStringConverter.convertToNormalLowerCase(
        EnumUtil.getByCode(GenConvertType.class, vo.getConvertType()), simpleTable.getTableName()));
    // 强制转驼峰并且首字母大写
    String className = GenStringConverter.convertToCamelCase(
        EnumUtil.getByCode(GenConvertType.class, vo.getConvertType()), simpleTable.getTableName());
    updateGenerateInfoVo.setClassName(
        className.substring(0, 1).toUpperCase() + className.substring(1));
    updateGenerateInfoVo.setClassDescription(
        StringUtil.isEmpty(simpleTable.getTableComment()) ? StringPool.EMPTY_STR
            : simpleTable.getTableComment());
    updateGenerateInfoVo.setKeyType(GenKeyType.SNOW_FLAKE.getCode());
    updateGenerateInfoVo.setMenuCode(StringPool.EMPTY_STR);
    updateGenerateInfoVo.setMenuName(StringPool.EMPTY_STR);
    updateGenerateInfoVo.setDetailSpan(4);
    updateGenerateInfoVo.setIsCache(true);
    updateGenerateInfoVo.setHasDelete(false);

    generateInfoService.updateGenerate(vo.getDataObjId(), updateGenerateInfoVo);

    return simpleTable.getId();
  }

  @Transactional
  @Override
  public void deleteByDataObjId(String dataObjId) {

    SimpleTableDto table = this.getByDataObjId(dataObjId);
    if (ObjectUtil.isNull(table)) {
      return;
    }

    getBaseMapper().deleteById(table.getId());

    this.simpleTableColumnService.deleteByTableId(table.getId());
  }
}
