package com.abc.app.engine.common.config;

import com.abc.app.engine.config.EngineUrl;

/**
 * 字典管理URL
 */
public class DictManageUrl {

    public static final String URL_DICT = EngineUrl.URL_ENGINE + "/dict-manage";
    public static final String URL_DICT_LIST = "/list";
    public static final String URL_DICT_INFO = "/info";
    public static final String URL_DICT_ADD = "/add";
    public static final String URL_DICT_EDIT = "/edit";
    public static final String URL_DICT_DELETE = "/delete";
    public static final String URL_DICT_TYPES = "/types";
    public static final String URL_DICT_TYPE = "/type";

    public static final String URL_DICT_OPTION_LIST = "/option/list";
    public static final String URL_DICT_OPTION_INFO = "/option/info";
    public static final String URL_DICT_OPTION_ADD = "/option/add";
    public static final String URL_DICT_OPTION_EDIT = "/option/edit";
    public static final String URL_DICT_OPTION_DELETE = "/option/delete";

    public static final String AUTH_DICT = EngineUrl.AUTH_ENGINE + "DICT_MANAGE";
    public static final String AUTH_DICT_LIST = AUTH_DICT + "_LIST";
    public static final String AUTH_DICT_INFO = AUTH_DICT + "_INFO";
    public static final String AUTH_DICT_ADD = AUTH_DICT + "_ADD";
    public static final String AUTH_DICT_EDIT = AUTH_DICT + "_EDIT";
    public static final String AUTH_DICT_DELETE = AUTH_DICT + "_DELETE";

}