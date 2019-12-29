package com.abc.app.engine.security.config;

import com.abc.app.engine.config.EngineUrl;

/**
 * 用户管理URL
 */
public class UserManageUrl {

    public static final String URL_USER = EngineUrl.URL_ENGINE + "/user-manage";

    public static final String URL_USER_LIST = "/list";
    public static final String URL_USER_INFO = "/info";
    public static final String URL_USER_ADD = "/add";
    public static final String URL_USER_EDIT = "/edit";
    public static final String URL_USER_DELETE = "/delete";
    public static final String URL_USER_DEPARTMENTS = "/dpts";
    public static final String URL_USER_RESET_PASSWORD = "/resetpwd";

    public static final String AUTH_USER = EngineUrl.AUTH_ENGINE + "USER_MANAGE";

    public static final String AUTH_USER_LIST = AUTH_USER + "_LIST";
    public static final String AUTH_USER_INFO = AUTH_USER + "_INFO";
    public static final String AUTH_USER_ADD = AUTH_USER + "_ADD";
    public static final String AUTH_USER_EDIT = AUTH_USER + "_EDIT";
    public static final String AUTH_USER_DELETE = AUTH_USER + "_DELETE";

}