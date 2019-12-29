package com.abc.app.engine.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.abc.app.engine.security.entity.RoleLog;

@Mapper
public interface RoleLogMapper {

	List<RoleLog> findRoleLogList(RoleLog roleLog);

	long findRoleLogListCount(RoleLog roleLog);

	RoleLog findRoleLogByLogId(long logId);

	long insertRoleLogs(RoleLog roleLog);

}
