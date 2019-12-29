package com.abc.app.engine.security.controller;

import java.util.List;

import com.abc.app.engine.common.util.PageData;
import com.abc.app.engine.common.util.ResultData;
import com.abc.app.engine.common.util.SaveType;
import com.abc.app.engine.security.config.MenuManageUrl;
import com.abc.app.engine.security.entity.Menu;
import com.abc.app.engine.security.service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单管理
 * 
 * @author 陈勇
 * @date 2018年8月8日
 *
 */
@RestController
@RequestMapping(MenuManageUrl.URL_MENU)
public class MenuManageController {

	@Autowired
	private MenuService menuService;

	/**
	 * 菜单列表
	 * 
	 * @param menu
	 * @return
	 */
	@PostMapping(MenuManageUrl.URL_MENU_LIST)
	@Secured(MenuManageUrl.AUTH_MENU_LIST)
	public PageData<Menu> menuList(@RequestBody Menu menu) {
		long count = menuService.findMenuListCount(menu);
		List<Menu> list = null;
		if (count > 0) {
			list = menuService.findMenuList(menu);
		}
		return new PageData<Menu>(list, count);
	}

	/**
	 * 菜单信息
	 * 
	 * @param menu
	 * @return
	 */
	@PostMapping(MenuManageUrl.URL_MENU_INFO)
	@Secured({ MenuManageUrl.AUTH_MENU_INFO, MenuManageUrl.AUTH_MENU_ADD, MenuManageUrl.AUTH_MENU_EDIT })
	public Menu menuInfo(@RequestBody Menu menu) {
		if (menu.getId() == null) {
			menu.setId(0L);
		}
		return menuService.findMenuById(menu.getId());
	}

	/**
	 * 添加菜单
	 * 
	 * @param menu
	 * @return
	 */
	@PostMapping(MenuManageUrl.URL_MENU_ADD)
	@Secured(MenuManageUrl.AUTH_MENU_ADD)
	public ResultData<?> menuAdd(@RequestBody Menu menu) {
		return menuSave(menu, SaveType.Add);
	}

	/**
	 * 修改菜单
	 * 
	 * @param menu
	 * @return
	 */
	@PostMapping(MenuManageUrl.URL_MENU_EDIT)
	@Secured(MenuManageUrl.AUTH_MENU_EDIT)
	public ResultData<?> menuEdit(@RequestBody Menu menu) {
		return menuSave(menu, SaveType.Edit);
	}

	/**
	 * 保存菜单
	 * 
	 * @param menu
	 * @param type
	 * @return
	 */
	private ResultData<?> menuSave(Menu menu, SaveType type) {
		boolean success = true;
		String message = null;
		if (menu.getId() == null) {
			menu.setId(0L);
		}
		if (menu.getParentId() == null) {
			menu.setParentId(0L);
		}
		if (type == SaveType.Add && menu.getId().longValue() > 0) {
			success = false;
			message = "添加失败，参数错误！";
		} else if (type == SaveType.Edit && menu.getId().longValue() < 0) {
			success = false;
			message = "修改失败，参数错误！";
		}
		if (success && type == SaveType.Add) {
			Menu pmenu = null;
			if (menu.getParentId().longValue() > 0) {
				pmenu = menuService.findMenuById(menu.getParentId());
				if (pmenu == null) {
					success = false;
					message = "添加失败，父级菜单不存在！";
				} else {
					long orderLen = pmenu.getOrder().length();
					long maxOrderLen = menu.getOrderChildSize() * menu.getOrderTopSize();
					if (orderLen >= maxOrderLen) {
						success = false;
						message = String.format("父级别已达最大级数【 %d】！", menu.getOrderChildSize());
					}
				}
			}
			if (success) {
				long childrens = menuService.findMenuChildrenCount(menu.getParentId());
				if (childrens >= menu.getOrderChildSize()) {
					success = false;
					message = String.format("当前级别已达最大个数【 %d】！", menu.getOrderChildSize());
				}
			}
		}
		if (success) {
			success = menuService.saveMenu(menu);
		}
		return new ResultData<>(success, message, null);
	}

	/**
	 * 删除菜单
	 * 
	 * @param menu
	 * @return
	 */
	@PostMapping(MenuManageUrl.URL_MENU_DELETE)
	@Secured(MenuManageUrl.AUTH_MENU_DELETE)
	public ResultData<?> menuDelete(@RequestBody Menu menu) {
		boolean success = true;
		String message = null;
		if (menu.getId() == null) {
			menu.setId(0L);
		}
		if (success) {
			long childrens = menuService.findMenuChildrenCount(menu.getId());
			if (childrens > 0) {
				success = false;
				message = "删除失败，该菜单存在下级菜单！";
			}
		}
		if (success) {
			long roles = menuService.findMenuRoleCount(menu.getId());
			if (roles > 0) {
				success = false;
				message = "删除失败，该菜单存在关联角色！";
			}
		}
		if (success) {
			success = menuService.deleteMenuById(menu.getId());
		}
		return new ResultData<>(success, message, null);
	}

}
