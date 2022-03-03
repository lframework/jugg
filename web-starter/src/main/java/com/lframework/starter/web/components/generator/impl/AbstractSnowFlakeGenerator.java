package com.lframework.starter.web.components.generator.impl;

import com.lframework.common.utils.IdWorker;
import com.lframework.starter.web.components.generator.Generator;
import com.lframework.starter.web.utils.ApplicationUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 单号生成器
 *
 * @author zmj
 */
public abstract class AbstractSnowFlakeGenerator extends AbstractGenerator implements Generator {

    @Autowired
    private IdWorker idWorker;

    @Override
    public String generate() {

        return getPreffix() + idWorker.nextIdStr();
    }

    protected String getPreffix() {

        return "";
    }
}
