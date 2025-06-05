package com.lframework.starter.web.inner.impl;

import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.EnumUtil;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.inner.entity.SecurityUploadRecord;
import com.lframework.starter.web.inner.enums.system.UploadType;
import com.lframework.starter.web.inner.mappers.SecurityUploadRecordMapper;
import com.lframework.starter.web.inner.service.SecurityUploadRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityUploadRecordServiceImpl extends
    BaseMpServiceImpl<SecurityUploadRecordMapper, SecurityUploadRecord> implements
    SecurityUploadRecordService {

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(String uploadType, String filePath) {
    SecurityUploadRecord uploadRecord = new SecurityUploadRecord();
    uploadRecord.setId(IdUtil.getId());
    uploadRecord.setUploadType(EnumUtil.getByCode(UploadType.class, uploadType));
    uploadRecord.setFilePath(filePath);

    this.save(uploadRecord);

    return uploadRecord.getId();
  }
}
