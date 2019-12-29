package com.abc.app.engine.security.service.impl;

import java.util.List;

import com.abc.app.engine.security.entity.MenuLog;
import com.abc.app.engine.security.mapper.MenuLogMapper;
import com.abc.app.engine.security.service.MenuLogService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class MenuLogServiceImpl implements MenuLogService {

	@Autowired
	private MenuLogMapper menuLogMapper;

	@Override
	public List<MenuLog> findMenuLogList(MenuLog menuLog) {
		menuLog.setSearch(StringUtils.trimToNull(menuLog.getSearch()));
		return menuLogMapper.findMenuLogList(menuLog);
	}

	@Override
	public long findMenuLogListCount(MenuLog menuLog) {
		menuLog.setSearch(StringUtils.trimToNull(menuLog.getSearch()));
		return menuLogMapper.findMenuLogListCount(menuLog);
	}

	@Override
	public MenuLog findMenuLogByLogId(long logId) {
		return menuLogMapper.findMenuLogByLogId(logId);
	}

}
