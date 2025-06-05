package com.lframework.starter.web.inner.handlers.exception;


import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.web.core.handlers.exception.WebExceptionConverter;
import org.apache.tomcat.util.http.fileupload.FileUploadException;

public class FileUploadExceptionConverter implements WebExceptionConverter {

  @Override
  public boolean isMatch(Throwable e) {
    return e instanceof FileUploadException;
  }

  @Override
  public BaseException convert(Throwable e) {
    return new DefaultClientException("文件上传失败！");
  }

  @Override
  public int getOrder() {
    return THIRD_ORDER;
  }
}
