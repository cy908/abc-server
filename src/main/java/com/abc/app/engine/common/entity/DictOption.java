package com.abc.app.engine.common.entity;

import java.io.Serializable;

import com.abc.app.engine.common.util.EnableInfo;
import com.abc.app.engine.common.util.PageInfo;
import com.abc.app.engine.common.util.SearchInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 字典项
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DictOption implements PageInfo, EnableInfo, SearchInfo, Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long dictId;
	private String name;
	private String code;
	private Long order;
	private Boolean enable;
	private String note;

	// 扩展
	private Long pageIndex;
	private Long pageSize;
	private Dict dict;

	// 参数
	private Long oldId;
	private String search;

}
