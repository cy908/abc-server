package com.abc.app.engine.security.service;

import java.util.List;

import com.abc.app.engine.security.entity.Menu;
import com.abc.app.engine.security.entity.Role;
import com.abc.app.engine.security.entity.RoleMenu;
import com.abc.app.engine.security.entity.RoleUser;
import com.abc.app.engine.security.entity.User;

public interface RoleService {

	List<Role> findRoleList(Role role);

	long findRoleListCount(Role role);;

	Role findRoleById(long id);

	long findRoleMenuCount(long id);

	long findRoleUserCount(long id);

	List<Menu> findRoleMenuCheckList(long id);

	List<User> findRoleUserCheckList(long id);

	List<Role> findRoleListByUsername(String username);

	boolean saveRole(Role role);

	boolean deleteRoleById(long id);

	boolean saveRoleMenu(List<RoleMenu> list);

	boolean saveRoleUser(List<RoleUser> list);

}
