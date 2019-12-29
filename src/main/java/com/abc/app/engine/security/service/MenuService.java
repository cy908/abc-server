package com.abc.app.engine.security.service;

import java.util.List;

import com.abc.app.engine.security.entity.Menu;

public interface MenuService {

	List<Menu> findMenuList(Menu menu);

	long findMenuListCount(Menu menu);

	Menu findMenuById(long id);

	long findMenuChildrenCount(long id);

	long findMenuRoleCount(long id);

	List<Menu> findMenuListByUsername(String username);

	boolean saveMenu(Menu menu);

	boolean deleteMenuById(long id);

}
