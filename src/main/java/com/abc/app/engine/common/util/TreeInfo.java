package com.abc.app.engine.common.util;

/**
 * 树形信息
 */
public interface TreeInfo {

	/** 顶级字符个数 */
	default int getOrderTopSize() {
		return Constant.ORDER_TOP_SIZE;
	}

	/** 每级子级个数 */
	default int getOrderChildSize() {
		return Constant.ORDER_CHILD_SIZE;
	}

}
