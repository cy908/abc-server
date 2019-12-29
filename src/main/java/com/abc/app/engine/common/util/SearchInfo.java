package com.abc.app.engine.common.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.StringUtils;

/**
 * 搜索信息
 */
public interface SearchInfo {

    String getSearch();

    @JsonIgnore
    default String getSearchAll() {
        return StringUtils.isBlank(getSearch()) ? null : StringUtils.join("%", getSearch(), "%");
    }

    @JsonIgnore
    default String getSearchLeft() {
        return StringUtils.isBlank(getSearch()) ? null : StringUtils.join("%", getSearch());
    }

    @JsonIgnore
    default String getSearchRight() {
        return StringUtils.isBlank(getSearch()) ? null : StringUtils.join(getSearch(), "%");
    }

}