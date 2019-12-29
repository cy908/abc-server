package com.abc.app.engine.security.config;

import com.abc.app.engine.config.EngineUrl;

/**
 * 菜单管理URL
 */
public class MenuManageUrl {

    public static final String URL_MENU = EngineUrl.URL_ENGINE + "/menu-manage";

    public static final String URL_MENU_LIST = "/list";
    public static final String URL_MENU_INFO = "/info";
    public static final String URL_MENU_ADD = "/add";
    public static final String URL_MENU_EDIT = "/edit";
    public static final String URL_MENU_DELETE = "/delete";

    public static final String AUTH_MENU = EngineUrl.AUTH_ENGINE + "MENU_MANAGE";

    public static final String AUTH_MENU_LIST = AUTH_MENU + "_LIST";
    public static final String AUTH_MENU_INFO = AUTH_MENU + "_INFO";
    public static final String AUTH_MENU_ADD = AUTH_MENU + "_ADD";
    public static final String AUTH_MENU_EDIT = AUTH_MENU + "_EDIT";
    public static final String AUTH_MENU_DELETE = AUTH_MENU + "_DELETE";

}