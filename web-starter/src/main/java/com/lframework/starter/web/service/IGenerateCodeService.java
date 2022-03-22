package com.lframework.starter.web.service;

import com.lframework.starter.web.components.code.GenerateCodeType;

/**
 * 生成单号Service
 *
 * @author zmj
 */
public interface IGenerateCodeService extends BaseService {

  /**
   * 生成code
   *
   * @param type
   * @return
   */
  String generate(GenerateCodeType type);
}
