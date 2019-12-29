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
 * 菜单
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Menu implements PageInfo, EnableInfo, SearchInfo, TreeNode<Menu>, Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long parentId;
	private String name;
	private String url;
	private String matIcon;
	private String antdIcon;
	private String antdIconTheme;
	private String antdIconTwotone;
	private String order;
	private Boolean enable;
	private String note;

	// 扩展
	private Long pageIndex;
	private Long pageSize;
	private List<Menu> children;
	private Boolean checked;

	// 参数
	private String search;

}
