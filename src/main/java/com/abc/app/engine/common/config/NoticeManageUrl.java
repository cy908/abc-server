package com.abc.app.engine.common.config;

import com.abc.app.engine.config.EngineUrl;

/**
 * 通知管理URL
 */
public class NoticeManageUrl {

    public static final String URL_NOTICE = EngineUrl.URL_ENGINE + "/notice-manage";

    public static final String URL_NOTICE_LIST = "/list";
    public static final String URL_NOTICE_INFO = "/info";
    public static final String URL_NOTICE_ADD = "/add";
    public static final String URL_NOTICE_EDIT = "/edit";
    public static final String URL_NOTICE_DELETE = "/delete";
    public static final String URL_NOTICE_DEPARTMENTS = "/dpts";

    public static final String AUTH_NOTICE = EngineUrl.AUTH_ENGINE + "NOTICE_MANAGE";

    public static final String AUTH_NOTICE_LIST = AUTH_NOTICE + "_LIST";
    public static final String AUTH_NOTICE_INFO = AUTH_NOTICE + "_INFO";
    public static final String AUTH_NOTICE_ADD = AUTH_NOTICE + "_ADD";
    public static final String AUTH_NOTICE_EDIT = AUTH_NOTICE + "_EDIT";
    public static final String AUTH_NOTICE_DELETE = AUTH_NOTICE + "_DELETE";

}