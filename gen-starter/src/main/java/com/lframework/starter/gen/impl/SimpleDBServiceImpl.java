package com.lframework.starter.gen.impl;

import com.lframework.starter.gen.dto.simpledb.SimpleDBDto;
import com.lframework.starter.gen.mappers.DBMapper;
import com.lframework.starter.gen.mappers.SimpleDBMapper;
import com.lframework.starter.gen.service.ISimpleDBService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
