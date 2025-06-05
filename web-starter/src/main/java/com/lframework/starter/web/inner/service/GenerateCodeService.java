package com.lframework.starter.web.inner.service;

import com.lframework.starter.web.core.components.generator.rule.GenerateCodeRule;
import com.lframework.starter.web.core.dto.GenerateCodeDto;
import com.lframework.starter.web.core.service.BaseService;
import java.util.List;

/**
 * 生成单号Service
 *
 * @author zmj
 */
public interface GenerateCodeService extends BaseService {

  /**
   * 生成code
   *
   * @param id
   * @return
   */
  String generate(Integer id);

  /**
   * 根据实体类获取编号规则
   *
   * @param entity
   * @return
   */
  List<GenerateCodeRule> getRules(GenerateCodeDto entity);
}
