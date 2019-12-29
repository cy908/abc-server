package com.abc.app.engine.security.controller;

import com.abc.app.engine.common.util.ResultData;
import com.abc.app.engine.security.config.UserUrl;
import com.abc.app.engine.security.entity.Password;
import com.abc.app.engine.security.entity.User;
import com.abc.app.engine.security.service.UserService;
import com.abc.app.engine.security.util.SecurityUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户
 * 
 * @author 陈勇
 * @date 2018年8月8日
 *
 */
@RestController
@RequestMapping(UserUrl.URL_USER)
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 用户信息
	 * 
	 * @return
	 */
	@GetMapping(UserUrl.URL_USER_INFO)
	public User userInfo() {
		String username = SecurityUtil.getUsername();
		User user = userService.findUserByUsername(username);
		return user;
	}

	/**
	 * 修改密码
	 * 
	 * @param password
	 * @return
	 */
	@PostMapping(UserUrl.URL_USER_EDIT_PASSWORD)
	public ResultData<?> editPassword(@RequestBody Password password) {
		boolean success = true;
		String message = null;
		if (StringUtils.isBlank(password.getOld())) {
			success = false;
			message = "修改失败，请输入旧密码！";
		} else if (StringUtils.isBlank(password.getNow())) {
			success = false;
			message = "修改失败，请输入新密码！";
		}
		User user = null;
		if (success) {
			String username = SecurityUtil.getUsername();
			user = userService.findUserByUsername(username);
			if (user == null) {
				success = false;
				message = "修改失败，用户不存在！";
			} else if (!passwordEncoder.matches(password.getOld(), user.getPassword())) {
				success = false;
				message = "修改失败，旧密码不正确！";
			}
		}
		if (success) {
			user.setPassword(password.getNow());
			success = userService.updatePasswordById(user);
		}
		return new ResultData<>(success, message, null);
	}

}
