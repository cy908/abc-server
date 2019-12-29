package com.abc.app.engine.common.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.StringUtils;

/**
 * 树形节点
 */
public interface TreeNode<T> extends TreeInfo {

	String getOrder();

	List<T> getChildren();

	void setChildren(List<T> list);

	@JsonIgnore
	default String getOrderAll() {
		return StringUtils.isBlank(getOrder()) ? null : StringUtils.join("%", getOrder(), "%");
	}

	@JsonIgnore
	default String getOrderLeft() {
		return StringUtils.isBlank(getOrder()) ? null : StringUtils.join("%", getOrder());
	}

	@JsonIgnore
	default String getOrderRight() {
		return StringUtils.isBlank(getOrder()) ? null : StringUtils.join(getOrder(), "%");
	}

	// 父级排序

	@JsonIgnore
	default String getParentOrder() {
		int len = StringUtils.length(getOrder()) - getOrderTopSize();
		return StringUtils.trimToNull(StringUtils.left(getOrder(), len));
	}

	@JsonIgnore
	default String getParentOrderAll() {
		return StringUtils.isBlank(getParentOrder()) ? null : StringUtils.join("%", getParentOrder(), "%");
	}

	@JsonIgnore
	default String getParentOrderLeft() {
		return StringUtils.isBlank(getParentOrder()) ? null : StringUtils.join("%", getParentOrder());
	}

	@JsonIgnore
	default String getParentOrderRight() {
		return StringUtils.isBlank(getParentOrder()) ? null : StringUtils.join(getParentOrder(), "%");
	}

	// 顶级排序

	@JsonIgnore
	default String getOrderTopMax() {
		return StringUtils.leftPad("9", getOrderTopSize(), "9");
	}

	@JsonIgnore
	default String getOrderTopMaxAll() {
		return StringUtils.join("%", getOrderTopMax(), "%");
	}

	@JsonIgnore
	default String getOrderTopMaxLeft() {
		return StringUtils.join("%", getOrderTopMax());
	}

	@JsonIgnore
	default String getOrderTopMaxRight() {
		return StringUtils.join(getOrderTopMax(), "%");
	}

}
