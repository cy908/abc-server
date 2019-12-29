package com.abc.app.engine.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.abc.app.engine.security.entity.UserLog;

@Mapper
public interface UserLogMapper {

	List<UserLog> findUserLogList(UserLog userLog);

	long findUserLogListCount(UserLog userLog);

	UserLog findUserLogByLogId(long logId);

	long insertUserLogs(UserLog userLog);

}
