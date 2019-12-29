package com.abc.app.engine.security.service.impl;

import java.util.List;

import com.abc.app.engine.security.entity.UserLog;
import com.abc.app.engine.security.mapper.UserLogMapper;
import com.abc.app.engine.security.service.UserLogService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserLogServiceImpl implements UserLogService {

	@Autowired
	private UserLogMapper userLogMapper;

	@Override
	public List<UserLog> findUserLogList(UserLog userLog) {
		userLog.setSearch(StringUtils.trimToNull(userLog.getSearch()));
		return userLogMapper.findUserLogList(userLog);
	}

	@Override
	public long findUserLogListCount(UserLog userLog) {
		userLog.setSearch(StringUtils.trimToNull(userLog.getSearch()));
		return userLogMapper.findUserLogListCount(userLog);
	}

	@Override
	public UserLog findUserLogByLogId(long logId) {
		return userLogMapper.findUserLogByLogId(logId);
	}

}
