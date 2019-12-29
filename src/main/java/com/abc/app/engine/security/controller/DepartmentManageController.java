package com.abc.app.engine.security.controller;

import java.util.List;

import com.abc.app.engine.common.util.PageData;
import com.abc.app.engine.common.util.ResultData;
import com.abc.app.engine.common.util.SaveType;
import com.abc.app.engine.security.config.DepartmentManageUrl;
import com.abc.app.engine.security.entity.Department;
import com.abc.app.engine.security.service.DepartmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 部门管理
 * 
 * @author 陈勇
 * @date 2018年8月8日
 *
 */
@RestController
@RequestMapping(DepartmentManageUrl.URL_DEPARTMENT)
public class DepartmentManageController {

	@Autowired
	private DepartmentService departmentService;

	/**
	 * 部门列表
	 * 
	 * @param department
	 * @return
	 */
	@PostMapping(DepartmentManageUrl.URL_DEPARTMENT_LIST)
	@Secured(DepartmentManageUrl.AUTH_DEPARTMENT_LIST)
	public PageData<Department> departmentList(@RequestBody Department department) {
		long count = departmentService.findDepartmentListCount(department);
		List<Department> list = null;
		if (count > 0) {
			list = departmentService.findDepartmentList(department);
		}
		return new PageData<Department>(list, count);
	}

	/**
	 * 部门信息
	 * 
	 * @param department
	 * @return
	 */
	@PostMapping(DepartmentManageUrl.URL_DEPARTMENT_INFO)
	@Secured({ DepartmentManageUrl.AUTH_DEPARTMENT_INFO, DepartmentManageUrl.AUTH_DEPARTMENT_ADD,
			DepartmentManageUrl.AUTH_DEPARTMENT_EDIT })
	public Department departmentInfo(@RequestBody Department department) {
		if (department.getId() == null) {
			department.setId(0L);
		}
		return departmentService.findDepartmentById(department.getId());
	}

	/**
	 * 添加部门
	 * 
	 * @param department
	 * @return
	 */
	@PostMapping(DepartmentManageUrl.URL_DEPARTMENT_ADD)
	@Secured(DepartmentManageUrl.AUTH_DEPARTMENT_ADD)
	public ResultData<?> departmentAdd(@RequestBody Department department) {
		return departmentSave(department, SaveType.Add);
	}

	/**
	 * 修改部门
	 * 
	 * @param department
	 * @return
	 */
	@PostMapping(DepartmentManageUrl.URL_DEPARTMENT_EDIT)
	@Secured(DepartmentManageUrl.AUTH_DEPARTMENT_EDIT)
	public ResultData<?> departmentEdit(@RequestBody Department department) {
		return departmentSave(department, SaveType.Edit);
	}

	/**
	 * 保存部门
	 * 
	 * @param department
	 * @param type
	 * @return
	 */
	private ResultData<?> departmentSave(Department department, SaveType type) {
		boolean success = true;
		String message = null;
		if (department.getId() == null) {
			department.setId(0L);
		}
		if (department.getParentId() == null) {
			department.setParentId(0L);
		}
		if (type == SaveType.Add && department.getId().longValue() > 0) {
			success = false;
			message = "添加失败，参数错误！";
		} else if (type == SaveType.Edit && department.getId().longValue() < 0) {
			success = false;
			message = "修改失败，参数错误！";
		}
		if (success && type == SaveType.Add) {
			Department pdpt = null;
			if (department.getParentId().longValue() > 0) {
				pdpt = departmentService.findDepartmentById(department.getParentId());
				if (pdpt == null) {
					success = false;
					message = "添加失败，父级部门不存在！";
				} else {
					long orderLen = pdpt.getOrder().length();
					long maxOrderLength = department.getOrderChildSize() * department.getOrderTopSize();
					if (orderLen >= maxOrderLength) {
						success = false;
						message = String.format("父级别已达最大级数【%d】！", department.getOrderChildSize());
					}
				}
			}
			if (success) {
				long childrens = departmentService.findDepartmentChildrenCount(department.getParentId());
				if (childrens >= department.getOrderChildSize()) {
					success = false;
					message = String.format("当前级别已达最大个数【%d】！", department.getOrderChildSize());
				}
			}
		}
		if (success) {
			success = departmentService.saveDepartment(department);
		}
		return new ResultData<>(success, message, null);
	}

	/**
	 * 删除部门
	 * 
	 * @param department
	 * @return
	 */
	@PostMapping(DepartmentManageUrl.URL_DEPARTMENT_DELETE)
	@Secured(DepartmentManageUrl.AUTH_DEPARTMENT_DELETE)
	public ResultData<?> departmentDelete(@RequestBody Department department) {
		boolean success = true;
		String message = null;
		if (department.getId() == null) {
			department.setId(0L);
		}
		if (success) {
			long childrens = departmentService.findDepartmentChildrenCount(department.getId());
			if (childrens > 0) {
				success = false;
				message = "删除失败，该部门存在下级部门！";
			}
		}
		if (success) {
			long users = departmentService.findDepartmentUserCount(department.getId());
			if (users > 0) {
				success = false;
				message = "删除失败，该部门存在关联用户！";
			}
		}
		if (success) {
			success = departmentService.deleteDepartmentById(department.getId());
		}
		return new ResultData<>(success, message, null);
	}

}
