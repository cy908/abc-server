package com.abc.app.engine.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.abc.app.engine.security.entity.MenuLog;

@Mapper
public interface MenuLogMapper {

	List<MenuLog> findMenuLogList(MenuLog menuLog);

	long findMenuLogListCount(MenuLog menuLog);

	MenuLog findMenuLogByLogId(long logId);

	long insertMenuLogs(MenuLog menuLog);

}
