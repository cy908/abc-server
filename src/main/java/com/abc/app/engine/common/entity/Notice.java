package com.abc.app.engine.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.abc.app.engine.common.util.PageInfo;
import com.abc.app.engine.common.util.SearchInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 通知
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Notice implements PageInfo, SearchInfo, Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String title;
	private String content;
	private Date startTime;
	private Date endTime;

	private Date createTime;
	private Date updateTime;

	// 扩展
	private Long pageIndex;
	private Long pageSize;
	private List<NoticeDepartment> departments;

	// 参数
	private Long departmentId;
	private String search;

}
