package com.abc.app.engine.security.service.impl;

import java.util.Date;
import java.util.List;

import com.abc.app.engine.common.util.LogType;
import com.abc.app.engine.security.entity.Password;
import com.abc.app.engine.security.entity.User;
import com.abc.app.engine.security.entity.UserLog;
import com.abc.app.engine.security.mapper.UserLogMapper;
import com.abc.app.engine.security.mapper.UserMapper;
import com.abc.app.engine.security.service.UserService;
import com.abc.app.engine.security.util.PasswordUtil;
import com.abc.app.engine.security.util.SecurityUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private PasswordUtil passwordUtil;

	@Autowired
	private UserLogMapper userLogMapper;

	@Override
	public List<User> findUserList(User user) {
		user.setSearch(StringUtils.trimToNull(user.getSearch()));
		return userMapper.findUserList(user);
	}

	@Override
	public long findUserListCount(User user) {
		user.setSearch(StringUtils.trimToNull(user.getSearch()));
		return userMapper.findUserListCount(user);
	}

	@Override
	public User findUserById(long id) {
		return userMapper.findUserById(id);
	}

	@Override
	public long findUserRoleCount(long id) {
		return userMapper.findUserRoleCount(id);
	}

	@Override
	public User findUserByUsername(String username) {
		return userMapper.findUserByUsername(username);
	}

	@Override
	public boolean saveUser(User user) {
		if (user.getId() == null) {
			user.setId(0L);
		}
		if (user.getGender() == null) {
			user.setGender(0);
		}
		Date now = new Date();
		boolean success = false;
		if (user.getId() <= 0) {
			user.setCreateTime(now);
			user.setPassword(passwordEncoder.encode(passwordUtil.password(user)));
			userMapper.insertUser(user);
			success = user.getId() > 0 ? true : false;
			// 插入添加日志-START
			if (success) {
				String username = SecurityUtil.getUsername();
				UserLog userLog = new UserLog();
				userLog.setId(user.getId());
				userLog.setLogType(LogType.INSERT);
				userLog.setLogTime(now);
				userLog.setLogUser(username);
				userLogMapper.insertUserLogs(userLog);
			}
			// 插入添加日志-END
		} else {
			user.setUpdateTime(now);
			long rows = userMapper.updateUserById(user);
			success = rows > 0 ? true : false;
			// 插入更新日志-START
			if (success) {
				String username = SecurityUtil.getUsername();
				UserLog userLog = new UserLog();
				userLog.setId(user.getId());
				userLog.setLogType(LogType.UPDATE);
				userLog.setLogTime(now);
				userLog.setLogUser(username);
				userLogMapper.insertUserLogs(userLog);
			}
			// 插入更新日志-END
		}
		return success;
	}

	@Override
	public boolean deleteUserById(long id) {
		// 插入删除日志-START
		Date now = new Date();
		String username = SecurityUtil.getUsername();
		UserLog userLog = new UserLog();
		userLog.setId(id);
		userLog.setLogType(LogType.DELETE);
		userLog.setLogTime(now);
		userLog.setLogUser(username);
		userLogMapper.insertUserLogs(userLog);
		// 插入删除日志-END
		long rows = userMapper.deleteUserById(id);
		boolean success = rows > 0 ? true : false;
		return success;
	}

	@Override
	public boolean updatePasswordById(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		long rows = userMapper.updatePasswordById(user);
		boolean success = rows > 0 ? true : false;
		// 插入更新日志-START
		if (success) {
			Date now = new Date();
			String username = SecurityUtil.getUsername();
			UserLog userLog = new UserLog();
			userLog.setId(user.getId());
			userLog.setLogType(LogType.UPDATE);
			userLog.setLogTime(now);
			userLog.setLogUser(username);
			userLogMapper.insertUserLogs(userLog);
		}
		// 插入更新日志-END
		return success;
	}

	@Override
	public boolean updatePasswordByIds(Password password) {
		password.setNow(passwordEncoder.encode(password.getNow()));
		long rows = userMapper.updatePasswordByIds(password);
		boolean success = rows > 0 ? true : false;
		// 插入更新日志-START
		if (success) {
			Date now = new Date();
			String username = SecurityUtil.getUsername();
			UserLog userLog = new UserLog();
			userLog.setIds(password.getIds());
			userLog.setLogType(LogType.UPDATE);
			userLog.setLogTime(now);
			userLog.setLogUser(username);
			userLogMapper.insertUserLogs(userLog);
		}
		// 插入更新日志-END
		return success;
	}

}
