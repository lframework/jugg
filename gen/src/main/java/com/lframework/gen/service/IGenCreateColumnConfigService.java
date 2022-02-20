package com.lframework.gen.service;

import com.lframework.gen.dto.dataobj.GenCreateColumnConfigDto;
import com.lframework.gen.vo.dataobj.UpdateCreateColumnConfigVo;
import com.lframework.starter.web.service.BaseService;

import java.util.List;

public interface IGenCreateColumnConfigService extends BaseService {

    /**
     * 根据数据对象ID查询
     * @param dataObjId
     * @return
     */
    List<GenCreateColumnConfigDto> getByDataObjId(String dataObjId);

    /**
     * 修改生成器配置信息
     * @param vo
     */
    void updateGenerate(String dataObjId, List<UpdateCreateColumnConfigVo> vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    GenCreateColumnConfigDto getById(String id);

    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}
