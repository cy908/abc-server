package com.abc.app.engine.security.controller;

import java.util.List;

import com.abc.app.engine.common.util.TreeUtil;
import com.abc.app.engine.security.config.MenuUrl;
import com.abc.app.engine.security.entity.Menu;
import com.abc.app.engine.security.service.MenuService;
import com.abc.app.engine.security.util.SecurityUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单
 * 
 * @author 陈勇
 * @date 2018年8月8日
 *
 */
@RestController
@RequestMapping(MenuUrl.URL_MENU)
public class MenuController {

	@Autowired
	private MenuService menuService;

	/**
	 * 菜单列表
	 * 
	 * @return
	 */
	@GetMapping(MenuUrl.URL_MENU_LIST)
	public List<Menu> menuList() {
		String username = SecurityUtil.getUsername();
		List<Menu> list = menuService.findMenuListByUsername(username);
		return TreeUtil.generate(list);
	}

}
