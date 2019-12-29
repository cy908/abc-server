package com.abc.app.engine.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.abc.app.engine.security.entity.Department;

@Mapper
public interface DepartmentMapper {

	List<Department> findDepartmentList(Department department);

	long findDepartmentListCount(Department department);

	Department findDepartmentById(long id);

	long findDepartmentChildrenCount(long id);

	long findDepartmentUserCount(long id);

	String findDepartmentChildrenMaxOrder(long id);

	long insertDepartment(Department department);

	long updateDepartmentById(Department department);

	long deleteDepartmentById(long id);

	List<Department> findDepartmentListByOrder(Department department);

	long updateDepartmentOrderByIds(List<List<Department>> list);

}
