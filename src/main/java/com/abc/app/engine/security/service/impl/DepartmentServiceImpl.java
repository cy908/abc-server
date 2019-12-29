package com.abc.app.engine.security.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abc.app.engine.common.util.Constant;
import com.abc.app.engine.common.util.LogType;
import com.abc.app.engine.security.entity.Department;
import com.abc.app.engine.security.entity.DepartmentLog;
import com.abc.app.engine.security.mapper.DepartmentLogMapper;
import com.abc.app.engine.security.mapper.DepartmentMapper;
import com.abc.app.engine.security.service.DepartmentService;
import com.abc.app.engine.security.util.SecurityUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentMapper departmentMapper;

	@Autowired
	private DepartmentLogMapper departmentLogMapper;

	@Override
	public List<Department> findDepartmentList(Department department) {
		department.setSearch(StringUtils.trimToNull(department.getSearch()));
		return departmentMapper.findDepartmentList(department);
	}

	@Override
	public long findDepartmentListCount(Department department) {
		department.setSearch(StringUtils.trimToNull(department.getSearch()));
		return departmentMapper.findDepartmentListCount(department);
	}

	@Override
	public Department findDepartmentById(long id) {
		return departmentMapper.findDepartmentById(id);
	}

	@Override
	public long findDepartmentChildrenCount(long id) {
		return departmentMapper.findDepartmentChildrenCount(id);
	}

	@Override
	public long findDepartmentUserCount(long id) {
		return departmentMapper.findDepartmentUserCount(id);
	}

	@Override
	public boolean saveDepartment(Department department) {
		if (department.getId() == null) {
			department.setId(0L);
		}
		if (department.getParentId() == null) {
			department.setParentId(0L);
		}
		boolean success = false;
		if (department.getId() <= 0) {
			// 生成排序-START
			int topSize = department.getOrderTopSize();
			String order = null;
			String maxOrder = departmentMapper.findDepartmentChildrenMaxOrder(department.getParentId());
			if (maxOrder != null) {
				// 父级下有子级
				String prefix = StringUtils.left(maxOrder, maxOrder.length() - topSize);
				String suffix = StringUtils.right(maxOrder, topSize);
				suffix = String.valueOf(Integer.valueOf(suffix).intValue() + 1);
				suffix = StringUtils.leftPad(suffix, topSize, "0");
				order = StringUtils.join(prefix, suffix);
			} else {
				Department temp = departmentMapper.findDepartmentById(department.getParentId());
				if (temp != null) {
					// 父级下无子级
					String prefix = temp.getOrder();
					String suffix = StringUtils.leftPad("1", topSize, "0");
					order = StringUtils.join(prefix, suffix);
				} else {
					// 顶级
					order = StringUtils.leftPad("1", topSize, "0");
				}
			}
			department.setOrder(order);
			// 生成排序-END
			departmentMapper.insertDepartment(department);
			success = department.getId() > 0 ? true : false;
			// 插入添加日志-START
			if (success) {
				Date now = new Date();
				String username = SecurityUtil.getUsername();
				DepartmentLog departmentLog = new DepartmentLog();
				departmentLog.setId(department.getId());
				departmentLog.setLogType(LogType.INSERT);
				departmentLog.setLogTime(now);
				departmentLog.setLogUser(username);
				departmentLogMapper.insertDepartmentLogs(departmentLog);
			}
			// 插入添加日志-END
		} else {
			long rows = departmentMapper.updateDepartmentById(department);
			success = rows > 0 ? true : false;
			// 插入更新日志-START
			if (success) {
				Date now = new Date();
				String username = SecurityUtil.getUsername();
				DepartmentLog departmentLog = new DepartmentLog();
				departmentLog.setId(department.getId());
				departmentLog.setLogType(LogType.UPDATE);
				departmentLog.setLogTime(now);
				departmentLog.setLogUser(username);
				departmentLogMapper.insertDepartmentLogs(departmentLog);
			}
			// 插入更新日志-END
		}
		return success;
	}

	@Override
	public boolean deleteDepartmentById(long id) {
		// 删除时将自己之后的同级的排序的当前级别减1
		boolean success = false;
		Department department = departmentMapper.findDepartmentById(id);
		List<Department> list = departmentMapper.findDepartmentListByOrder(department);
		int topSize = department.getOrderTopSize();
		String currentOrder = department.getOrder();
		int currentSize = currentOrder.length();
		List<List<Department>> all = null;
		List<List<Long>> _all = null;
		if (list != null && list.size() > 0) {
			all = new ArrayList<>();
			_all = new ArrayList<>();
			List<Department> sub = null;
			List<Long> _sub = null;
			int count = 0;
			int size = Constant.SQL_IN_MAX;
			for (int i = 0; i < list.size(); i++) {
				Department temp = list.get(i);
				// 排序的当前级别减1-START
				String order = temp.getOrder();
				String _old = order;
				String prefix = StringUtils.left(order, currentSize);
				String center = StringUtils.right(prefix, topSize);
				prefix = StringUtils.left(order, currentSize - topSize);
				center = String.valueOf(Integer.valueOf(center).intValue() - 1);
				center = StringUtils.leftPad(center, topSize, "0");
				String suffix = StringUtils.right(order, order.length() - currentSize);
				order = StringUtils.join(prefix, center, suffix);
				String _now = order;
				temp.setOrder(order);
				log.debug("{} : {} --> {}", temp.getName(), _old, _now);
				// 排序的当前级别减1-END
				if (count == 0) {
					sub = new ArrayList<>();
					_sub = new ArrayList<>();
				}
				sub.add(temp);
				_sub.add(temp.getId());
				count = count + 1;
				if (count == size) {
					all.add(sub);
					_all.add(_sub);
					count = 0;
				}
			}
			if (count > 0) {
				all.add(sub);
				_all.add(_sub);
			}
		}
		// 插入删除日志-START
		Date now = new Date();
		String username = SecurityUtil.getUsername();
		DepartmentLog departmentLog = new DepartmentLog();
		departmentLog.setId(id);
		departmentLog.setLogType(LogType.DELETE);
		departmentLog.setLogTime(now);
		departmentLog.setLogUser(username);
		departmentLogMapper.insertDepartmentLogs(departmentLog);
		// 插入删除日志-END
		long rows = departmentMapper.deleteDepartmentById(id);
		if (all != null && all.size() > 0) {
			departmentMapper.updateDepartmentOrderByIds(all);
			// 插入更新日志-START
			departmentLog.setLogType(LogType.UPDATE);
			departmentLog.setIds(_all);
			departmentLogMapper.insertDepartmentLogs(departmentLog);
			// 插入更新日志-END
		}
		success = rows > 0 ? true : false;
		return success;
	}

}
