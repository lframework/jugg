package com.lframework.starter.web.inner.mappers.system;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.starter.web.core.annotations.sort.Sort;
import com.lframework.starter.web.core.annotations.sort.Sorts;
import com.lframework.starter.web.inner.entity.SysGenerateCode;
import com.lframework.starter.web.inner.vo.system.generate.QuerySysGenerateCodeVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysGenerateCodeMapper extends
    BaseMapper<SysGenerateCode> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "id", alias = "tb", autoParse = true),
      @Sort(value = "name", alias = "tb", autoParse = true),
  })
  List<SysGenerateCode> query(@Param("vo") QuerySysGenerateCodeVo vo);
}
