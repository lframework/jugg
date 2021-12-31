package com.lframework.starter.web.impl;

import com.lframework.starter.web.components.code.GenerateCodeType;
import com.lframework.starter.web.components.generator.GenerateCodeFactory;
import com.lframework.starter.web.service.IGenerateCodeService;
import org.springframework.stereotype.Service;

@Service
public class GenerateCodeServiceImpl implements IGenerateCodeService {

    @Override
    public String generate(GenerateCodeType type) {

        return GenerateCodeFactory.getInstance(type).generate();
    }
}
