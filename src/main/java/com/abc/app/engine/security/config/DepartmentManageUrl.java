package com.abc.app.engine.security.config;

import com.abc.app.engine.config.EngineUrl;

/**
 * 部门管理URL
 */
public class DepartmentManageUrl {

    public static final String URL_DEPARTMENT = EngineUrl.URL_ENGINE + "/dpt-manage";

    public static final String URL_DEPARTMENT_LIST = "/list";
    public static final String URL_DEPARTMENT_INFO = "/info";
    public static final String URL_DEPARTMENT_ADD = "/add";
    public static final String URL_DEPARTMENT_EDIT = "/edit";
    public static final String URL_DEPARTMENT_DELETE = "/delete";

    public static final String AUTH_DEPARTMENT = EngineUrl.AUTH_ENGINE + "DPT_MANAGE";

    public static final String AUTH_DEPARTMENT_LIST = AUTH_DEPARTMENT + "_LIST";
    public static final String AUTH_DEPARTMENT_INFO = AUTH_DEPARTMENT + "_INFO";
    public static final String AUTH_DEPARTMENT_ADD = AUTH_DEPARTMENT + "_ADD";
    public static final String AUTH_DEPARTMENT_EDIT = AUTH_DEPARTMENT + "_EDIT";
    public static final String AUTH_DEPARTMENT_DELETE = AUTH_DEPARTMENT + "_DELETE";

}