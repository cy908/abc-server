package com.abc.app.engine.security.controller;

import java.util.List;

import com.abc.app.engine.common.util.Constant;
import com.abc.app.engine.common.util.PageData;
import com.abc.app.engine.common.util.ResultData;
import com.abc.app.engine.common.util.SaveType;
import com.abc.app.engine.security.config.UserManageUrl;
import com.abc.app.engine.security.entity.Department;
import com.abc.app.engine.security.entity.Password;
import com.abc.app.engine.security.entity.User;
import com.abc.app.engine.security.service.DepartmentService;
import com.abc.app.engine.security.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理
 * 
 * @author 陈勇
 * @date 2018年8月8日
 *
 */
@RestController
@RequestMapping(UserManageUrl.URL_USER)
public class UserManageController {

	@Autowired
	private UserService userService;

	@Autowired
	private DepartmentService departmentService;

	/**
	 * 用户列表
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping(UserManageUrl.URL_USER_LIST)
	@Secured(UserManageUrl.AUTH_USER_LIST)
	public PageData<User> userList(@RequestBody User user) {
		long count = userService.findUserListCount(user);
		List<User> list = null;
		if (count > 0) {
			list = userService.findUserList(user);
		}
		return new PageData<User>(list, count);
	}

	/**
	 * 用户信息
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping(UserManageUrl.URL_USER_INFO)
	@Secured({ UserManageUrl.AUTH_USER_INFO, UserManageUrl.AUTH_USER_EDIT })
	public User userInfo(@RequestBody User user) {
		if (user.getId() == null) {
			user.setId(0L);
		}
		return userService.findUserById(user.getId());
	}

	/**
	 * 新增用户
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping(UserManageUrl.URL_USER_ADD)
	@Secured(UserManageUrl.AUTH_USER_ADD)
	public ResultData<?> userAdd(@RequestBody User user) {
		return userSave(user, SaveType.Add);
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping(UserManageUrl.URL_USER_EDIT)
	@Secured(UserManageUrl.AUTH_USER_EDIT)
	public ResultData<?> userEdit(@RequestBody User user) {
		return userSave(user, SaveType.Edit);
	}

	/**
	 * 保存用户
	 * 
	 * @param user
	 * @param type
	 * @return
	 */
	private ResultData<?> userSave(User user, SaveType type) {
		boolean success = true;
		String message = null;
		if (user.getId() == null) {
			user.setId(0L);
		}
		if (type == SaveType.Add && (user.getId().longValue() > 0 || StringUtils.isBlank(user.getUsername()))) {
			success = false;
			message = "添加失败，参数错误！";
		} else if (type == SaveType.Edit && (user.getId().longValue() < 0 || StringUtils.isBlank(user.getUsername()))) {
			success = false;
			message = "修改失败，参数错误！";
		}
		if (success) {
			User temp = userService.findUserByUsername(user.getUsername());
			if (type == SaveType.Add) {
				if (temp != null) {
					success = false;
					message = "添加失败，该用户名已存在！";
				}
			} else if (type == SaveType.Edit) {
				User old = userService.findUserById(user.getId());
				if (old == null) {
					success = false;
					message = "修改失败，该用户不存在！";
				} else {
					if (!user.getUsername().equals(old.getUsername()) && temp != null) {
						success = false;
						message = "修改失败，该用户名已存在！";
					}
				}
			}
		}
		if (success) {
			success = userService.saveUser(user);
		}
		return new ResultData<>(success, message, null);
	}

	/**
	 * 删除用户
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping(UserManageUrl.URL_USER_DELETE)
	@Secured(UserManageUrl.AUTH_USER_DELETE)
	public ResultData<?> userDelete(@RequestBody User user) {
		boolean success = true;
		String message = null;
		if (user.getId() == null) {
			user.setId(0L);
		}
		long roles = userService.findUserRoleCount(user.getId());
		if (roles > 0) {
			success = false;
			message = "删除失败，该用户存在关联角色！";
		}
		if (success) {
			success = userService.deleteUserById(user.getId());
		}
		return new ResultData<>(success, message, null);
	}

	/**
	 * 部门列表
	 * 
	 * @return
	 */
	@GetMapping(UserManageUrl.URL_USER_DEPARTMENTS)
	@Secured({ UserManageUrl.AUTH_USER_LIST, UserManageUrl.AUTH_USER_ADD, UserManageUrl.AUTH_USER_EDIT })
	public List<Department> departmentList() {
		Department department = new Department();
		department.setEnable(true);// 查询启用的部门
		return departmentService.findDepartmentList(department);
	}

	/**
	 * 重设密码
	 * 
	 * @param password
	 * @return
	 */
	@PostMapping(UserManageUrl.URL_USER_RESET_PASSWORD)
	@Secured(UserManageUrl.AUTH_USER_EDIT)
	public ResultData<?> resetPassword(@RequestBody Password password) {
		boolean success = true;
		String message = null;
		if (password.getId() == null && (password.getIds() == null || password.getIds().size() == 0)) {
			success = false;
			message = "重设失败，参数不正确！";
		}
		if (success && password.getIds() != null && password.getIds().size() > Constant.SQL_IN_MAX) {
			success = false;
			message = String.format("批量重设最大数：%d！", Constant.SQL_IN_MAX);
		}
		if (success && StringUtils.isBlank(password.getNow())) {
			success = false;
			message = "重设失败，请输入新密码！";
		}
		// 单个重置
		if (success && password.getId() != null) {
			User user = null;
			if (success) {
				user = userService.findUserById(password.getId());
				if (user == null) {
					success = false;
					message = "重设失败，用户不存在！";
				}
			}
			if (success) {
				user.setPassword(password.getNow());
				success = userService.updatePasswordById(user);
			}
		}
		// 批量重置
		if (success && password.getIds() != null && password.getIds().size() > 0) {
			success = userService.updatePasswordByIds(password);
		}
		return new ResultData<>(success, message, null);
	}

}
