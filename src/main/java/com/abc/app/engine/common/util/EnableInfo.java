package com.abc.app.engine.common.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.BooleanUtils;

/**
 * 启用信息
 */
public interface EnableInfo {

    Boolean getEnable();

    @JsonIgnore
    default Integer getEnableInt() {
        return BooleanUtils.toIntegerObject(getEnable());
    }

}