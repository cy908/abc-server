package com.abc.app.engine.security.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abc.app.engine.common.util.Constant;
import com.abc.app.engine.common.util.LogType;
import com.abc.app.engine.security.entity.Menu;
import com.abc.app.engine.security.entity.MenuLog;
import com.abc.app.engine.security.mapper.MenuLogMapper;
import com.abc.app.engine.security.mapper.MenuMapper;
import com.abc.app.engine.security.service.MenuService;
import com.abc.app.engine.security.util.SecurityUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuMapper menuMapper;

	@Autowired
	private MenuLogMapper menuLogMapper;

	@Override
	public List<Menu> findMenuList(Menu menu) {
		menu.setSearch(StringUtils.trimToNull(menu.getSearch()));
		return menuMapper.findMenuList(menu);
	}

	@Override
	public long findMenuListCount(Menu menu) {
		menu.setSearch(StringUtils.trimToNull(menu.getSearch()));
		return menuMapper.findMenuListCount(menu);
	}

	@Override
	public Menu findMenuById(long id) {
		return menuMapper.findMenuById(id);
	}

	@Override
	public long findMenuChildrenCount(long parentId) {
		return menuMapper.findMenuChildrenCount(parentId);
	}

	@Override
	public long findMenuRoleCount(long id) {
		return menuMapper.findMenuRoleCount(id);
	}

	@Override
	public List<Menu> findMenuListByUsername(String username) {
		return menuMapper.findMenuListByUsername(username);
	}

	@Override
	public boolean saveMenu(Menu menu) {
		if (menu.getId() == null) {
			menu.setId(0L);
		}
		if (menu.getParentId() == null) {
			menu.setParentId(0L);
		}
		boolean success = false;
		if (menu.getId() <= 0) {
			// 生成排序-START
			int topSize = menu.getOrderTopSize();
			String order = null;
			String maxOrder = menuMapper.findMenuChildrenMaxOrder(menu);
			if (maxOrder != null) {
				// 父级下有子级
				String prefix = StringUtils.left(maxOrder, maxOrder.length() - topSize);
				String suffix = StringUtils.right(maxOrder, topSize);
				suffix = String.valueOf(Integer.valueOf(suffix).intValue() + 1);
				suffix = StringUtils.leftPad(suffix, topSize, "0");
				order = StringUtils.join(prefix, suffix);
			} else {
				Menu temp = menuMapper.findMenuById(menu.getParentId());
				if (temp != null) {
					// 父级下无子级
					String prefix = temp.getOrder();
					String suffix = StringUtils.leftPad("1", topSize, "0");
					order = StringUtils.join(prefix, suffix);
				} else {
					// 顶级
					order = StringUtils.leftPad("1", topSize, "0");
				}
			}
			menu.setOrder(order);
			// 生成排序-END
			menuMapper.insertMenu(menu);
			success = menu.getId() > 0 ? true : false;
			// 插入添加日志-START
			if (success) {
				Date now = new Date();
				String username = SecurityUtil.getUsername();
				MenuLog menuLog = new MenuLog();
				menuLog.setId(menu.getId());
				menuLog.setLogType(LogType.INSERT);
				menuLog.setLogTime(now);
				menuLog.setLogUser(username);
				menuLogMapper.insertMenuLogs(menuLog);
			}
			// 插入添加日志-END
		} else {
			long rows = menuMapper.updateMenuById(menu);
			success = rows > 0 ? true : false;
			// 插入更新日志-START
			if (success) {
				Date now = new Date();
				String username = SecurityUtil.getUsername();
				MenuLog menuLog = new MenuLog();
				menuLog.setId(menu.getId());
				menuLog.setLogType(LogType.UPDATE);
				menuLog.setLogTime(now);
				menuLog.setLogUser(username);
				menuLogMapper.insertMenuLogs(menuLog);
			}
			// 插入更新日志-END
		}
		return success;
	}

	@Override
	public boolean deleteMenuById(long id) {
		// 删除时将自己之后的同级的排序的当前级别减1
		boolean success = false;
		Menu menu = menuMapper.findMenuById(id);
		List<Menu> list = menuMapper.findMenuListByOrder(menu);
		int topSize = menu.getOrderTopSize();
		String currentOrder = menu.getOrder();
		int currentSize = currentOrder.length();
		List<List<Menu>> all = null;
		List<List<Long>> _all = null;
		if (list != null && list.size() > 0) {
			all = new ArrayList<>();
			_all = new ArrayList<>();
			List<Menu> sub = null;
			List<Long> _sub = null;
			int count = 0;
			int size = Constant.SQL_IN_MAX;
			for (int i = 0; i < list.size(); i++) {
				Menu temp = list.get(i);
				// 排序的当前级别减1-START
				String order = temp.getOrder();
				String _old = order;
				String prefix = StringUtils.left(order, currentSize);
				String center = StringUtils.right(prefix, topSize);
				prefix = StringUtils.left(order, currentSize - topSize);
				center = String.valueOf(Integer.valueOf(center).intValue() - 1);
				center = StringUtils.leftPad(center, topSize, "0");
				String suffix = StringUtils.right(order, order.length() - currentSize);
				order = StringUtils.join(prefix, center, suffix);
				String _now = order;
				temp.setOrder(order);
				log.debug("{} : {} --> {}", temp.getName(), _old, _now);
				// 排序的当前级别减1-END
				if (count == 0) {
					sub = new ArrayList<>();
					_sub = new ArrayList<>();
				}
				sub.add(temp);
				_sub.add(temp.getId());
				count = count + 1;
				if (count == size) {
					all.add(sub);
					_all.add(_sub);
					count = 0;
				}
			}
			if (count > 0) {
				all.add(sub);
				_all.add(_sub);
			}
		}
		// 插入删除日志-START
		Date now = new Date();
		String username = SecurityUtil.getUsername();
		MenuLog menuLog = new MenuLog();
		menuLog.setId(id);
		menuLog.setLogType(LogType.DELETE);
		menuLog.setLogTime(now);
		menuLog.setLogUser(username);
		menuLogMapper.insertMenuLogs(menuLog);
		// 插入删除日志-END
		long rows = menuMapper.deleteMenuById(id);
		if (all != null && all.size() > 0) {
			menuMapper.updateMenuOrderById(all);
			// 插入更新日志-START
			menuLog.setLogType(LogType.UPDATE);
			menuLog.setIds(_all);
			menuLogMapper.insertMenuLogs(menuLog);
			// 插入更新日志-END
		}
		success = rows > 0 ? true : false;
		return success;
	}

}
