package com.abc.app.engine.security.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色菜单
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long roleId;
	private Long menuId;

}
