package com.abc.app.engine.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.abc.app.engine.security.entity.DepartmentLog;

@Mapper
public interface DepartmentLogMapper {

	List<DepartmentLog> findDepartmentLogList(DepartmentLog departmentLog);

	long findDepartmentLogListCount(DepartmentLog departmentLog);

	DepartmentLog findDepartmentLogByLogId(long logId);

	long insertDepartmentLogs(DepartmentLog departmentLog);

}
