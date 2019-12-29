package com.abc.app.engine.security.service;

import java.util.List;

import com.abc.app.engine.security.entity.UserLog;

public interface UserLogService {

	List<UserLog> findUserLogList(UserLog userLog);

	long findUserLogListCount(UserLog userLog);

	UserLog findUserLogByLogId(long logId);

}
