package com.lframework.starter.web.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 生成验证码Dto
 *
 * @author zmj
 */
@Data
public class GenerateCaptchaDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 序列号
     */
    private String sn;

    /**
     * 图片文件Base64
     */
    private String image;
}
