package com.lframework.gen.impl;

import com.lframework.gen.dto.simpledb.SimpleDBDto;
import com.lframework.gen.mappers.DBMapper;
import com.lframework.gen.mappers.SimpleDBMapper;
import com.lframework.gen.service.ISimpleDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleDBServiceImpl implements ISimpleDBService {

    @Autowired
    private SimpleDBMapper simpleDBMapper;

    @Autowired
    private DBMapper dbMapper;

    @Override
    public List<SimpleDBDto> getTables(String dbName) {

        return simpleDBMapper.getTables(dbName);
    }

    @Override
    public List<SimpleDBDto> getCurrentTables() {

        return this.getTables(dbMapper.getCurrentDBName());
    }
}
