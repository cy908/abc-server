package com.abc.app.engine.security.service.impl;

import java.util.List;

import com.abc.app.engine.security.entity.DepartmentLog;
import com.abc.app.engine.security.mapper.DepartmentLogMapper;
import com.abc.app.engine.security.service.DepartmentLogService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class DepartmentLogServiceImpl implements DepartmentLogService {

	@Autowired
	private DepartmentLogMapper departmentLogMapper;

	@Override
	public List<DepartmentLog> findDepartmentLogList(DepartmentLog departmentLog) {
		departmentLog.setSearch(StringUtils.trimToNull(departmentLog.getSearch()));
		return departmentLogMapper.findDepartmentLogList(departmentLog);
	}

	@Override
	public long findDepartmentLogListCount(DepartmentLog departmentLog) {
		departmentLog.setSearch(StringUtils.trimToNull(departmentLog.getSearch()));
		return departmentLogMapper.findDepartmentLogListCount(departmentLog);
	}

	@Override
	public DepartmentLog findDepartmentLogByLogId(long logId) {
		return departmentLogMapper.findDepartmentLogByLogId(logId);
	}

}
