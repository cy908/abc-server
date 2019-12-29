package com.abc.app.engine.common.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 分页信息
 */
public interface PageInfo {

	Long getPageIndex();

	Long getPageSize();

	@JsonIgnore
	default Long getStartIndex() {
		if (getPageIndex() == null || getPageSize() == null) {
			return null;
		}
		return (getPageIndex() - 1) * getPageSize();
	}

	@JsonIgnore
	default Long getEndIndex() {
		if (getPageIndex() == null || getPageSize() == null) {
			return null;
		}
		return getPageIndex() * getPageSize();
	}

}
