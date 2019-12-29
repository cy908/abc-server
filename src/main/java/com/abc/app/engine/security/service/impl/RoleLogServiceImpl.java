package com.abc.app.engine.security.service.impl;

import java.util.List;

import com.abc.app.engine.security.entity.RoleLog;
import com.abc.app.engine.security.mapper.RoleLogMapper;
import com.abc.app.engine.security.service.RoleLogService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class RoleLogServiceImpl implements RoleLogService {

	@Autowired
	private RoleLogMapper roleLogMapper;

	@Override
	public List<RoleLog> findRoleLogList(RoleLog roleLog) {
		roleLog.setSearch(StringUtils.trimToNull(roleLog.getSearch()));
		return roleLogMapper.findRoleLogList(roleLog);
	}

	@Override
	public long findRoleLogListCount(RoleLog roleLog) {
		roleLog.setSearch(StringUtils.trimToNull(roleLog.getSearch()));
		return roleLogMapper.findRoleLogListCount(roleLog);
	}

	@Override
	public RoleLog findRoleLogByLogId(long logId) {
		return roleLogMapper.findRoleLogByLogId(logId);
	}

}
