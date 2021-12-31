package com.lframework.starter.web.components.security;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 默认UserDetails
 * 如果无需增加属性 可直接使用此类
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DefaultUserDetails extends AbstractUserDetails {

    private static final long serialVersionUID = 1L;

}
