package com.abc.app.engine.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.abc.app.engine.common.util.LogInfo;
import com.abc.app.engine.common.util.PageInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 通知日志
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeLog implements LogInfo, PageInfo, Serializable {

	private static final long serialVersionUID = 1L;

	private Long logId;
	private String logType;
	private Date logTime;
	private String logUser;

	private Notice notice;

	// 扩展
	private Long pageIndex;
	private Long pageSize;

	// 内部参数
	@JsonIgnore
	private Long id;
	@JsonIgnore
	private List<Long> ids;

}
