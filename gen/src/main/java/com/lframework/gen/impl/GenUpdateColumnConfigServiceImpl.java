package com.lframework.gen.impl;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.gen.dto.dataobj.GenDataObjectColumnDto;
import com.lframework.gen.dto.dataobj.GenUpdateColumnConfigDto;
import com.lframework.gen.entity.GenUpdateColumnConfig;
import com.lframework.gen.mappers.GenUpdateColumnConfigMapper;
import com.lframework.gen.service.IDataObjectColumnService;
import com.lframework.gen.service.IGenUpdateColumnConfigService;
import com.lframework.gen.vo.dataobj.UpdateUpdateColumnConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenUpdateColumnConfigServiceImpl implements IGenUpdateColumnConfigService {

    @Autowired
    private IDataObjectColumnService dataObjectColumnService;

    @Autowired
    private GenUpdateColumnConfigMapper genUpdateColumnConfigMapper;

    @Override
    public List<GenUpdateColumnConfigDto> getByDataObjId(String dataObjId) {

        List<GenDataObjectColumnDto> columns = dataObjectColumnService.getByDataObjId(dataObjId);
        if (CollectionUtil.isEmpty(columns)) {
            return Collections.EMPTY_LIST;
        }

        return genUpdateColumnConfigMapper
                .getByIds(columns.stream().map(GenDataObjectColumnDto::getId).collect(Collectors.toList()));
    }

    @Transactional
    @Override
    public void updateGenerate(String dataObjId, List<UpdateUpdateColumnConfigVo> vo) {

        List<GenDataObjectColumnDto> columns = dataObjectColumnService.getByDataObjId(dataObjId);
        if (!CollectionUtil.isEmpty(columns)) {
            genUpdateColumnConfigMapper
                    .deleteBatchIds(columns.stream().map(GenDataObjectColumnDto::getId).collect(Collectors.toList()));
        }

        if (!CollectionUtil.isEmpty(vo)) {
            int orderNo = 1;
            for (UpdateUpdateColumnConfigVo updateUpdateColumnConfigVo : vo) {
                GenUpdateColumnConfig record = new GenUpdateColumnConfig();
                record.setId(updateUpdateColumnConfigVo.getId());
                record.setRequired(updateUpdateColumnConfigVo.getRequired());
                record.setOrderNo(orderNo++);

                genUpdateColumnConfigMapper.insert(record);
            }
        }
    }

    @Override
    public GenUpdateColumnConfigDto getById(String id) {

        return genUpdateColumnConfigMapper.getById(id);
    }

    @Transactional
    @Override
    public void deleteById(String id) {

        genUpdateColumnConfigMapper.deleteById(id);
    }
}
