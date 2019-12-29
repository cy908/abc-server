package com.abc.app.engine.security.entity;

import java.io.Serializable;
import java.util.List;

import com.abc.app.engine.common.util.EnableInfo;
import com.abc.app.engine.common.util.PageInfo;
import com.abc.app.engine.common.util.SearchInfo;
import com.abc.app.engine.common.util.TreeNode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 部门
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Department implements PageInfo, EnableInfo, SearchInfo, TreeNode<Department>, Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long parentId;
	private String name;
	private String code;
	private String phone;
	private String address;
	private String order;
	private Boolean enable;
	private String note;

	// 扩展
	private Long pageIndex;
	private Long pageSize;
	private List<Department> children;

	// 参数
	private String search;

}
