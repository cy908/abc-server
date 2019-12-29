package com.abc.app.engine.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形工具类
 */
public class TreeUtil {

	/**
	 * 列表必须从顶级开始
	 * 
	 * @param list
	 * @return
	 */
	public static <T extends TreeNode<T>> List<T> generate(List<T> list) {
		int len = 0, current = 0;
		T root = null;
		List<T> ts = null;
		if (list != null && list.size() > 0) {
			ts = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				T t = list.get(i);
				if (t == null || t.getOrder() == null) {
					ts = null;
					break;
				}
				current = t.getOrder().length();
				if (i == 0) {
					len = current;
				}
				if (current == len) {
					ts.add(t);
					root = t;
				} else {
					addT(root, t);
				}
			}
		}
		return ts;
	}

	private static <T extends TreeNode<T>> void addT(T root, T current) {
		int rootLenth = root.getOrder().length();
		int currentLenth = current.getOrder().length();
		int level = currentLenth / rootLenth;
		T t = root;
		while (level > 2) {
			if (t.getChildren() == null) {
				break;
			}
			for (T _t : t.getChildren()) {
				if (current.getOrder().startsWith(_t.getOrder())) {
					t = _t;
					break;
				}
			}
			level--;
		}
		int parentLength = t.getOrder().length();
		if (currentLenth - parentLength == rootLenth) {
			List<T> list = t.getChildren();
			if (list == null) {
				list = new ArrayList<>();
				t.setChildren(list);
			}
			list.add(current);
		}
	}

}
