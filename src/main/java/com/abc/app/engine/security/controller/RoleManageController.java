package com.abc.app.engine.security.controller;

import java.util.List;

import com.abc.app.engine.common.util.PageData;
import com.abc.app.engine.common.util.ResultData;
import com.abc.app.engine.common.util.SaveType;
import com.abc.app.engine.common.util.TreeUtil;
import com.abc.app.engine.security.config.RoleManageUrl;
import com.abc.app.engine.security.entity.Department;
import com.abc.app.engine.security.entity.Menu;
import com.abc.app.engine.security.entity.Role;
import com.abc.app.engine.security.entity.RoleMenu;
import com.abc.app.engine.security.entity.RoleUser;
import com.abc.app.engine.security.entity.User;
import com.abc.app.engine.security.service.DepartmentService;
import com.abc.app.engine.security.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色管理
 * 
 * @author 陈勇
 * @date 2018年8月8日
 *
 */
@RestController
@RequestMapping(RoleManageUrl.URL_ROLE)
public class RoleManageController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private DepartmentService departmentService;

	/**
	 * 角色列表
	 * 
	 * @param role
	 * @return
	 */
	@PostMapping(RoleManageUrl.URL_ROLE_LIST)
	@Secured(RoleManageUrl.AUTH_ROLE_LIST)
	public PageData<Role> roleList(@RequestBody Role role) {
		long count = roleService.findRoleListCount(role);
		List<Role> list = null;
		if (count > 0) {
			list = roleService.findRoleList(role);
		}
		return new PageData<Role>(list, count);
	}

	/**
	 * 角色信息
	 * 
	 * @param role
	 * @return
	 */
	@PostMapping(RoleManageUrl.URL_ROLE_INFO)
	@Secured({ RoleManageUrl.AUTH_ROLE_INFO, RoleManageUrl.AUTH_ROLE_EDIT })
	public Role roleInfo(@RequestBody Role role) {
		if (role.getId() == null) {
			role.setId(0L);
		}
		return roleService.findRoleById(role.getId());
	}

	/**
	 * 新增角色
	 * 
	 * @param role
	 * @return
	 */
	@PostMapping(RoleManageUrl.URL_ROLE_ADD)
	@Secured(RoleManageUrl.AUTH_ROLE_ADD)
	public ResultData<?> roleAdd(@RequestBody Role role) {
		return roleSave(role, SaveType.Add);
	}

	/**
	 * 修改角色
	 * 
	 * @param role
	 * @return
	 */
	@PostMapping(RoleManageUrl.URL_ROLE_EDIT)
	@Secured(RoleManageUrl.AUTH_ROLE_EDIT)
	public ResultData<?> roleEdit(@RequestBody Role role) {
		return roleSave(role, SaveType.Edit);
	}

	/**
	 * 保存角色
	 * 
	 * @param role
	 * @param type
	 * @return
	 */
	private ResultData<?> roleSave(Role role, SaveType type) {
		boolean success = true;
		String message = null;
		if (role.getId() == null) {
			role.setId(0L);
		}
		if (type == SaveType.Add && role.getId().longValue() > 0) {
			success = false;
			message = "添加失败，参数错误！";
		} else if (type == SaveType.Edit && role.getId().longValue() < 0) {
			success = false;
			message = "修改失败，参数错误！";
		}
		if (success && type == SaveType.Edit) {
			Role old = roleService.findRoleById(role.getId());
			if (old == null) {
				success = false;
				message = "修改失败，该角色不存在！";
			}
		}
		if (success) {
			success = roleService.saveRole(role);
		}
		return new ResultData<>(success, message, null);
	}

	/**
	 * 删除角色
	 * 
	 * @param role
	 * @return
	 */
	@PostMapping(RoleManageUrl.URL_ROLE_DELETE)
	@Secured(RoleManageUrl.AUTH_ROLE_DELETE)
	public ResultData<?> roleDelete(@RequestBody Role role) {
		boolean success = true;
		String message = null;
		if (role.getId() == null) {
			role.setId(0L);
		}
		Role old = roleService.findRoleById(role.getId());
		if (old == null) {
			success = false;
			message = "删除失败，该角色不存在！";
		}
		if (success) {
			long menus = roleService.findRoleMenuCount(role.getId());
			long users = roleService.findRoleUserCount(role.getId());
			if (menus > 0 || users > 0) {
				success = false;
				message = "删除失败，该角色存在关联菜单或用户！";
			}
		}
		if (success) {
			success = roleService.deleteRoleById(role.getId());
		}
		return new ResultData<>(success, message, null);
	}

	/**
	 * 菜单树
	 * 
	 * @param role
	 * @return
	 */
	@PostMapping(RoleManageUrl.URL_ROLE_MENUS)
	@Secured({ RoleManageUrl.AUTH_ROLE_ADD, RoleManageUrl.AUTH_ROLE_EDIT })
	public List<Menu> menuTree(@RequestBody Role role) {
		if (role.getId() == null) {
			role.setId(0L);
		}
		List<Menu> list = roleService.findRoleMenuCheckList(role.getId());
		return TreeUtil.generate(list);
	}

	/**
	 * 保存角色菜单
	 * 
	 * @param list
	 * @return
	 */
	@PostMapping(RoleManageUrl.URL_ROLE_MENUS_SAVE)
	@Secured({ RoleManageUrl.AUTH_ROLE_ADD, RoleManageUrl.AUTH_ROLE_EDIT })
	public ResultData<?> saveRoleMenu(@RequestBody List<RoleMenu> list) {
		boolean success = true;
		String message = null;
		if (list == null || list.size() <= 0) {
			success = false;
			message = "保存失败，参数错误！";
		}
		if (success) {
			success = roleService.saveRoleMenu(list);
		}
		return new ResultData<>(success, message, null);
	}

	/**
	 * 部门树
	 * 
	 * @return
	 */
	@GetMapping(RoleManageUrl.URL_ROLE_DEPARTMENTS)
	@Secured({ RoleManageUrl.AUTH_ROLE_ADD, RoleManageUrl.AUTH_ROLE_EDIT })
	public List<Department> departmentTree() {
		Department department = new Department();
		List<Department> list = departmentService.findDepartmentList(department);
		return TreeUtil.generate(list);
	}

	/**
	 * 用户列表
	 * 
	 * @param role
	 * @return
	 */
	@PostMapping(RoleManageUrl.URL_ROLE_USERS)
	@Secured({ RoleManageUrl.AUTH_ROLE_ADD, RoleManageUrl.AUTH_ROLE_EDIT })
	public List<User> userList(@RequestBody Role role) {
		if (role.getId() == null) {
			role.setId(0L);
		}
		return roleService.findRoleUserCheckList(role.getId());
	}

	/**
	 * 保存角色用户
	 * 
	 * @param list
	 * @return
	 */
	@PostMapping(RoleManageUrl.URL_ROLE_USERS_SAVE)
	@Secured({ RoleManageUrl.AUTH_ROLE_ADD, RoleManageUrl.AUTH_ROLE_EDIT })
	public ResultData<?> saveRoleUser(@RequestBody List<RoleUser> list) {
		boolean success = true;
		String message = null;
		if (list == null || list.size() <= 0) {
			success = false;
			message = "保存失败，参数错误！";
		}
		if (success) {
			success = roleService.saveRoleUser(list);
		}
		return new ResultData<>(success, message, null);
	}

}
