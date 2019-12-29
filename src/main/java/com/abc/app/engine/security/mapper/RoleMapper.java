package com.abc.app.engine.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.abc.app.engine.security.entity.Menu;
import com.abc.app.engine.security.entity.Role;
import com.abc.app.engine.security.entity.RoleMenu;
import com.abc.app.engine.security.entity.RoleUser;
import com.abc.app.engine.security.entity.User;

@Mapper
public interface RoleMapper {

	List<Role> findRoleList(Role role);

	long findRoleListCount(Role role);

	Role findRoleById(long id);

	long findRoleMenuCount(long id);

	long findRoleUserCount(long id);

	List<Menu> findRoleMenuCheckList(long id);

	List<User> findRoleUserCheckList(long id);

	List<Role> findRoleListByUsername(String username);

	long insertRole(Role role);

	long updateRoleById(Role role);

	long deleteRoleById(long id);

	long deleteRoleMenuById(long id);

	long deleteRoleUserById(long id);

	long insertRoleMenu(List<RoleMenu> list);

	long insertRoleUser(List<RoleUser> list);
}
