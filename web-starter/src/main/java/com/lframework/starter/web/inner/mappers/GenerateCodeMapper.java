package com.lframework.starter.web.inner.mappers;

import com.lframework.starter.web.core.dto.GenerateCodeDto;

public interface GenerateCodeMapper {

  GenerateCodeDto findById(Integer id);
}
