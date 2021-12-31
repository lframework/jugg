package com.lframework.starter.security.bo.system.oplog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.mybatis.dto.DefaultOpLogsDto;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetOpLogBo extends BaseBo<DefaultOpLogsDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 日志名称
     */
    private String name;

    /**
     * 类别
     */
    private Integer logType;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 补充信息
     */
    private String extra;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    public GetOpLogBo() {

    }

    public GetOpLogBo(DefaultOpLogsDto dto) {

        super(dto);
    }

    @Override
    public BaseBo<DefaultOpLogsDto> convert(DefaultOpLogsDto dto) {

        return super.convert(dto, GetOpLogBo::getLogType);
    }

    @Override
    protected void afterInit(DefaultOpLogsDto dto) {

        this.logType = dto.getLogType().getCode();

        IUserService userService = ApplicationUtil.getBean(IUserService.class);
        UserDto createBy = userService.getById(dto.getCreateBy());
        this.createBy = createBy.getName();
    }
}
