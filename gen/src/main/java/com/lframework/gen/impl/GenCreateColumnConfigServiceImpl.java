package com.lframework.gen.impl;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.gen.dto.dataobj.GenCreateColumnConfigDto;
import com.lframework.gen.dto.dataobj.GenDataObjectColumnDto;
import com.lframework.gen.entity.GenCreateColumnConfig;
import com.lframework.gen.mappers.GenCreateColumnConfigMapper;
import com.lframework.gen.service.IDataObjectColumnService;
import com.lframework.gen.service.IGenCreateColumnConfigService;
import com.lframework.gen.vo.dataobj.UpdateCreateColumnConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenCreateColumnConfigServiceImpl implements IGenCreateColumnConfigService {

    @Autowired
    private IDataObjectColumnService dataObjectColumnService;

    @Autowired
    private GenCreateColumnConfigMapper genCreateColumnConfigMapper;

    @Override
    public List<GenCreateColumnConfigDto> getByDataObjId(String dataObjId) {

        List<GenDataObjectColumnDto> columns = dataObjectColumnService.getByDataObjId(dataObjId);
        if (CollectionUtil.isEmpty(columns)) {
            return Collections.EMPTY_LIST;
        }

        return genCreateColumnConfigMapper
                .getByIds(columns.stream().map(GenDataObjectColumnDto::getId).collect(Collectors.toList()));
    }

    @Transactional
    @Override
    public void updateGenerate(String dataObjId, List<UpdateCreateColumnConfigVo> vo) {

        List<GenDataObjectColumnDto> columns = dataObjectColumnService.getByDataObjId(dataObjId);
        if (!CollectionUtil.isEmpty(columns)) {
            genCreateColumnConfigMapper
                    .deleteBatchIds(columns.stream().map(GenDataObjectColumnDto::getId).collect(Collectors.toList()));
        }

        if (!CollectionUtil.isEmpty(vo)) {
            int orderNo = 1;
            for (UpdateCreateColumnConfigVo updateCreateColumnConfigVo : vo) {
                GenCreateColumnConfig record = new GenCreateColumnConfig();
                record.setId(updateCreateColumnConfigVo.getId());
                record.setRequired(updateCreateColumnConfigVo.getRequired());
                record.setOrderNo(orderNo++);

                genCreateColumnConfigMapper.insert(record);
            }
        }
    }

    @Override
    public GenCreateColumnConfigDto getById(String id) {

        return genCreateColumnConfigMapper.getById(id);
    }

    @Transactional
    @Override
    public void deleteById(String id) {

        genCreateColumnConfigMapper.deleteById(id);
    }
}
