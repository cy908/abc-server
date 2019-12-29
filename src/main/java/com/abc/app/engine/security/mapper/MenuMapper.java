package com.abc.app.engine.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.abc.app.engine.security.entity.Menu;

@Mapper
public interface MenuMapper {

	List<Menu> findMenuList(Menu menu);

	long findMenuListCount(Menu menu);

	Menu findMenuById(long id);

	long findMenuChildrenCount(long parentId);

	long findMenuRoleCount(long id);

	String findMenuChildrenMaxOrder(Menu menu);

	List<Menu> findMenuListByUsername(String username);

	long insertMenu(Menu menu);

	long updateMenuById(Menu menu);

	long deleteMenuById(long id);

	List<Menu> findMenuListByOrder(Menu menu);

	long updateMenuOrderById(List<List<Menu>> list);

}
