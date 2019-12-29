package com.abc.app.engine.security.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.abc.app.engine.security.entity.Menu;
import com.abc.app.engine.security.entity.Role;
import com.abc.app.engine.security.entity.User;
import com.abc.app.engine.security.service.MenuService;
import com.abc.app.engine.security.service.RoleService;
import com.abc.app.engine.security.service.UserService;
import com.abc.app.engine.security.util.SecurityUser;

/**
 * 安全用户服务
 */
public class SecurityUserService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private MenuService menuService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findUserByUsername(username);
		if (user == null || BooleanUtils.isNotTrue(user.getEnable())) {
			throw new UsernameNotFoundException(null);
		}
		List<Role> roles = roleService.findRoleListByUsername(username);
		List<Menu> menus = menuService.findMenuListByUsername(username);
		List<SimpleGrantedAuthority> auths = new ArrayList<>();
		addRoles(roles, auths);
		addMenus(menus, auths);
		return new SecurityUser(username, user.getPassword(), auths);
	}

	/**
	 * 添加权限
	 * 
	 * @param roles
	 * @param auths
	 */
	private void addRoles(List<Role> roles, List<SimpleGrantedAuthority> auths) {
		if (roles != null && roles.size() > 0 && auths != null) {
			for (Role role : roles) {
				if (StringUtils.isNotBlank(role.getCode())) {
					String auth = format(role.getCode());
					if (!contains(auths, auth)) {
						auths.add(new SimpleGrantedAuthority(auth));
					}
				}
			}
		}
	}

	/**
	 * 添加权限
	 * 
	 * @param menus
	 * @param auths
	 */
	private void addMenus(List<Menu> menus, List<SimpleGrantedAuthority> auths) {
		if (menus != null && menus.size() > 0 && auths != null) {
			for (Menu menu : menus) {
				if (StringUtils.isNotBlank(menu.getUrl())) {
					String auth = format(menu.getUrl());
					if (!contains(auths, auth)) {
						auths.add(new SimpleGrantedAuthority(auth));
					}
				}
			}
		}
	}

	/**
	 * 是否包含
	 * 
	 * @param auths
	 * @param auth
	 * @return
	 */
	private boolean contains(List<SimpleGrantedAuthority> auths, String auth) {
		return auths.stream().anyMatch(item -> item.getAuthority().equals(auth));
	}

	/**
	 * 权限格式 <code>ROLE_XX</code>
	 * 
	 * @param auth
	 * @return
	 */
	private String format(String auth) {
		String _auth = auth.replaceAll("[/-]", "_").toUpperCase();
		String _format = _auth.startsWith("_") ? "ROLE%s" : "ROLE_%s";
		return String.format(_format, _auth);
	}

}
