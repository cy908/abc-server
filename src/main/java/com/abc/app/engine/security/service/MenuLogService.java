package com.abc.app.engine.security.service;

import java.util.List;

import com.abc.app.engine.security.entity.MenuLog;

public interface MenuLogService {

	List<MenuLog> findMenuLogList(MenuLog menuLog);

	long findMenuLogListCount(MenuLog menuLog);

	MenuLog findMenuLogByLogId(long logId);

}
