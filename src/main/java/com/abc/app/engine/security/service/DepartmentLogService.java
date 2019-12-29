package com.abc.app.engine.security.service;

import java.util.List;

import com.abc.app.engine.security.entity.DepartmentLog;

public interface DepartmentLogService {

	List<DepartmentLog> findDepartmentLogList(DepartmentLog departmentLog);

	long findDepartmentLogListCount(DepartmentLog departmentLog);

	DepartmentLog findDepartmentLogByLogId(long logId);

}
