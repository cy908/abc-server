package com.abc.app.engine.security.config;

import com.abc.app.engine.config.EngineUrl;

/**
 * 角色管理URL
 */
public class RoleManageUrl {

    public static final String URL_ROLE = EngineUrl.URL_ENGINE + "/role-manage";

    public static final String URL_ROLE_LIST = "/list";
    public static final String URL_ROLE_INFO = "/info";
    public static final String URL_ROLE_ADD = "/add";
    public static final String URL_ROLE_EDIT = "/edit";
    public static final String URL_ROLE_DELETE = "/delete";
    public static final String URL_ROLE_MENUS = "/menus";
    public static final String URL_ROLE_DEPARTMENTS = "/dpts";
    public static final String URL_ROLE_USERS = "/users";
    public static final String URL_ROLE_MENUS_SAVE = "/menus/save";
    public static final String URL_ROLE_USERS_SAVE = "/users/save";

    public static final String AUTH_ROLE = EngineUrl.AUTH_ENGINE + "ROLE_MANAGE";

    public static final String AUTH_ROLE_LIST = AUTH_ROLE + "_LIST";
    public static final String AUTH_ROLE_INFO = AUTH_ROLE + "_INFO";
    public static final String AUTH_ROLE_ADD = AUTH_ROLE + "_ADD";
    public static final String AUTH_ROLE_EDIT = AUTH_ROLE + "_EDIT";
    public static final String AUTH_ROLE_DELETE = AUTH_ROLE + "_DELETE";

}