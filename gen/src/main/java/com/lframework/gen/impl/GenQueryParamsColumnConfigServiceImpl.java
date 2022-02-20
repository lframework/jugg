package com.lframework.gen.impl;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.gen.dto.dataobj.GenDataObjectColumnDto;
import com.lframework.gen.dto.dataobj.GenQueryParamsColumnConfigDto;
import com.lframework.gen.entity.GenQueryParamsColumnConfig;
import com.lframework.gen.enums.GenQueryType;
import com.lframework.gen.mappers.GenQueryParamsColumnConfigMapper;
import com.lframework.gen.service.IDataObjectColumnService;
import com.lframework.gen.service.IGenQueryParamsColumnConfigService;
import com.lframework.gen.vo.dataobj.UpdateQueryParamsColumnConfigVo;
import com.lframework.starter.web.utils.EnumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenQueryParamsColumnConfigServiceImpl implements IGenQueryParamsColumnConfigService {

    @Autowired
    private IDataObjectColumnService dataObjectColumnService;

    @Autowired
    private GenQueryParamsColumnConfigMapper genQueryParamsColumnConfigMapper;

    @Override
    public List<GenQueryParamsColumnConfigDto> getByDataObjId(String dataObjId) {

        List<GenDataObjectColumnDto> columns = dataObjectColumnService.getByDataObjId(dataObjId);
        if (CollectionUtil.isEmpty(columns)) {
            return Collections.EMPTY_LIST;
        }

        return genQueryParamsColumnConfigMapper
                .getByIds(columns.stream().map(GenDataObjectColumnDto::getId).collect(Collectors.toList()));
    }

    @Transactional
    @Override
    public void updateGenerate(String dataObjId, List<UpdateQueryParamsColumnConfigVo> vo) {

        List<GenDataObjectColumnDto> columns = dataObjectColumnService.getByDataObjId(dataObjId);
        if (!CollectionUtil.isEmpty(columns)) {
            genQueryParamsColumnConfigMapper
                    .deleteBatchIds(columns.stream().map(GenDataObjectColumnDto::getId).collect(Collectors.toList()));
        }

        if (!CollectionUtil.isEmpty(vo)) {
            int orderNo = 1;
            for (UpdateQueryParamsColumnConfigVo updateQueryParamsColumnConfigVo : vo) {
                GenQueryParamsColumnConfig record = new GenQueryParamsColumnConfig();
                record.setId(updateQueryParamsColumnConfigVo.getId());
                record.setQueryType(EnumUtil.getByCode(GenQueryType.class, updateQueryParamsColumnConfigVo.getQueryType()));
                record.setOrderNo(orderNo++);

                genQueryParamsColumnConfigMapper.insert(record);
            }
        }
    }

    @Override
    public GenQueryParamsColumnConfigDto getById(String id) {

        return genQueryParamsColumnConfigMapper.getById(id);
    }

    @Transactional
    @Override
    public void deleteById(String id) {

        genQueryParamsColumnConfigMapper.deleteById(id);
    }
}
