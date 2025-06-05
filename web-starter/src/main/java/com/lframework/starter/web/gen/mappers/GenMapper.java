package com.lframework.starter.web.gen.mappers;

import com.lframework.starter.web.gen.components.data.obj.DataObjectQueryObj;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface GenMapper {

    List<Map<String, Object>> findList(@Param("obj") DataObjectQueryObj obj);
}
