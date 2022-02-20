package com.lframework.gen.impl;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.gen.dto.dataobj.GenDataObjectColumnDto;
import com.lframework.gen.dto.dataobj.GenDetailColumnConfigDto;
import com.lframework.gen.entity.GenDetailColumnConfig;
import com.lframework.gen.mappers.GenDetailColumnConfigMapper;
import com.lframework.gen.service.IDataObjectColumnService;
import com.lframework.gen.service.IGenDetailColumnConfigService;
import com.lframework.gen.vo.dataobj.UpdateDetailColumnConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenDetailColumnConfigServiceImpl implements IGenDetailColumnConfigService {

    @Autowired
    private IDataObjectColumnService dataObjectColumnService;

    @Autowired
    private GenDetailColumnConfigMapper genDetailColumnConfigMapper;

    @Override
    public List<GenDetailColumnConfigDto> getByDataObjId(String dataObjId) {

        List<GenDataObjectColumnDto> columns = dataObjectColumnService.getByDataObjId(dataObjId);
        if (CollectionUtil.isEmpty(columns)) {
            return Collections.EMPTY_LIST;
        }

        return genDetailColumnConfigMapper
                .getByIds(columns.stream().map(GenDataObjectColumnDto::getId).collect(Collectors.toList()));
    }

    @Transactional
    @Override
    public void updateGenerate(String dataObjId, List<UpdateDetailColumnConfigVo> vo) {

        List<GenDataObjectColumnDto> columns = dataObjectColumnService.getByDataObjId(dataObjId);
        if (!CollectionUtil.isEmpty(columns)) {
            genDetailColumnConfigMapper
                    .deleteBatchIds(columns.stream().map(GenDataObjectColumnDto::getId).collect(Collectors.toList()));
        }

        if (!CollectionUtil.isEmpty(vo)) {
            int orderNo = 1;
            for (UpdateDetailColumnConfigVo updateDetailColumnConfigVo : vo) {
                GenDetailColumnConfig record = new GenDetailColumnConfig();
                record.setId(updateDetailColumnConfigVo.getId());
                record.setSpan(updateDetailColumnConfigVo.getSpan());
                record.setOrderNo(orderNo++);

                genDetailColumnConfigMapper.insert(record);
            }
        }
    }

    @Override
    public GenDetailColumnConfigDto getById(String id) {

        return genDetailColumnConfigMapper.getById(id);
    }

    @Transactional
    @Override
    public void deleteById(String id) {

        genDetailColumnConfigMapper.deleteById(id);
    }
}
