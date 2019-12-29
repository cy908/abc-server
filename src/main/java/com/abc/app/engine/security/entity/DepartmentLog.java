package com.abc.app.engine.security.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.abc.app.engine.common.util.LogInfo;
import com.abc.app.engine.common.util.PageInfo;
import com.abc.app.engine.common.util.SearchInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 部门日志
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentLog implements LogInfo, PageInfo, SearchInfo, Serializable {

	private static final long serialVersionUID = 1L;

	private Long logId;
	private String logType;
	private Date logTime;
	private String logUser;

	private Long id;
	private List<List<Long>> ids;
	private Department department;

	// 扩展
	private Long pageIndex;
	private Long pageSize;

	// 参数
	private String search;

}
