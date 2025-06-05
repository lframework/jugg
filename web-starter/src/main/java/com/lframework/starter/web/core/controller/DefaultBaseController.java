package com.lframework.starter.web.core.controller;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.inner.vo.openapi.OpenApiReqVo;

/**
 * 具有Security能力的BaseController
 *
 * @author zmj
 */
public abstract class DefaultBaseController extends BaseController {

  public <T extends BaseVo> T getOpenApiVo(OpenApiReqVo vo, Class<T> clazz) {
    if (StringUtil.isBlank(vo.getParams())) {
      return null;
    }
    return JsonUtil.parseObject(vo.getParams(), clazz);
  }
}
