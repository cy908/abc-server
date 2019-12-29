package com.abc.app.engine.security.service;

import java.util.List;

import com.abc.app.engine.security.entity.RoleLog;

public interface RoleLogService {

	List<RoleLog> findRoleLogList(RoleLog roleLog);

	long findRoleLogListCount(RoleLog roleLog);

	RoleLog findRoleLogByLogId(long logId);

}
