package com.abc.app.engine.security.service;

import java.util.List;

import com.abc.app.engine.security.entity.Department;

public interface DepartmentService {

	List<Department> findDepartmentList(Department department);

	long findDepartmentListCount(Department department);

	Department findDepartmentById(long id);

	long findDepartmentChildrenCount(long id);

	long findDepartmentUserCount(long id);

	boolean saveDepartment(Department department);

	boolean deleteDepartmentById(long id);

}
