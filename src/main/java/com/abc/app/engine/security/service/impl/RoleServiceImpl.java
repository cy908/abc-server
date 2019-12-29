package com.abc.app.engine.security.service.impl;

import java.util.Date;
import java.util.List;

import com.abc.app.engine.common.util.LogType;
import com.abc.app.engine.security.entity.Menu;
import com.abc.app.engine.security.entity.Role;
import com.abc.app.engine.security.entity.RoleLog;
import com.abc.app.engine.security.entity.RoleMenu;
import com.abc.app.engine.security.entity.RoleUser;
import com.abc.app.engine.security.entity.User;
import com.abc.app.engine.security.mapper.RoleLogMapper;
import com.abc.app.engine.security.mapper.RoleMapper;
import com.abc.app.engine.security.service.RoleService;
import com.abc.app.engine.security.util.SecurityUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private RoleLogMapper roleLogMapper;

	@Override
	public List<Role> findRoleList(Role role) {
		role.setSearch(StringUtils.trimToNull(role.getSearch()));
		return roleMapper.findRoleList(role);
	}

	@Override
	public long findRoleListCount(Role role) {
		role.setSearch(StringUtils.trimToNull(role.getSearch()));
		return roleMapper.findRoleListCount(role);
	}

	@Override
	public Role findRoleById(long id) {
		return roleMapper.findRoleById(id);
	}

	@Override
	public long findRoleMenuCount(long id) {
		return roleMapper.findRoleMenuCount(id);
	}

	@Override
	public long findRoleUserCount(long id) {
		return roleMapper.findRoleMenuCount(id);
	}

	@Override
	public List<Menu> findRoleMenuCheckList(long id) {
		return roleMapper.findRoleMenuCheckList(id);
	}

	@Override
	public List<User> findRoleUserCheckList(long id) {
		return roleMapper.findRoleUserCheckList(id);
	}

	@Override
	public List<Role> findRoleListByUsername(String username) {
		return roleMapper.findRoleListByUsername(username);
	}

	@Override
	public boolean saveRole(Role role) {
		if (role.getId() == null) {
			role.setId(0L);
		}
		if (role.getOrder() == null) {
			role.setOrder(0L);
		}
		boolean success = false;
		if (role.getId() <= 0) {
			roleMapper.insertRole(role);
			success = role.getId() > 0 ? true : false;
			// 插入添加日志-START
			if (success) {
				Date now = new Date();
				String username = SecurityUtil.getUsername();
				RoleLog roleLog = new RoleLog();
				roleLog.setId(role.getId());
				roleLog.setLogType(LogType.INSERT);
				roleLog.setLogTime(now);
				roleLog.setLogUser(username);
				roleLogMapper.insertRoleLogs(roleLog);
			}
			// 插入添加日志-END
		} else {
			long rows = roleMapper.updateRoleById(role);
			success = rows > 0 ? true : false;
			// 插入更新日志-START
			if (success) {
				Date now = new Date();
				String username = SecurityUtil.getUsername();
				RoleLog roleLog = new RoleLog();
				roleLog.setId(role.getId());
				roleLog.setLogType(LogType.UPDATE);
				roleLog.setLogTime(now);
				roleLog.setLogUser(username);
				roleLogMapper.insertRoleLogs(roleLog);
			}
			// 插入更新日志-END
		}
		return success;
	}

	@Override
	public boolean deleteRoleById(long id) {
		// 插入删除日志-START
		Date now = new Date();
		String username = SecurityUtil.getUsername();
		RoleLog roleLog = new RoleLog();
		roleLog.setId(id);
		roleLog.setLogType(LogType.DELETE);
		roleLog.setLogTime(now);
		roleLog.setLogUser(username);
		roleLogMapper.insertRoleLogs(roleLog);
		// 插入删除日志-END
		long rows = roleMapper.deleteRoleById(id);
		boolean success = rows > 0 ? true : false;
		return success;
	}

	@Override
	public boolean saveRoleMenu(List<RoleMenu> list) {
		boolean success = false;
		if (list != null && list.size() > 0) {
			RoleMenu roleMenu = list.get(0);
			roleMapper.deleteRoleMenuById(roleMenu.getRoleId());
			if (roleMenu.getMenuId() != null && roleMenu.getMenuId() != 0) {
				long rows = roleMapper.insertRoleMenu(list);
				success = rows > 0 ? true : false;
			}
		}
		return success;
	}

	@Override
	public boolean saveRoleUser(List<RoleUser> list) {
		boolean success = false;
		if (list != null && list.size() > 0) {
			RoleUser roleUser = list.get(0);
			roleMapper.deleteRoleUserById(roleUser.getRoleId());
			if (roleUser.getUserId() != null && roleUser.getUserId() != 0) {
				long rows = roleMapper.insertRoleUser(list);
				success = rows > 0 ? true : false;
			}
		}
		return success;
	}

}
