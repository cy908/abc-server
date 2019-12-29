package com.abc.app.engine.common.config;

import com.abc.app.engine.config.EngineUrl;

/**
 * 访问日志URL
 */
public class AccessLogUrl {

    public static final String URL_ACCESS_LOG = EngineUrl.URL_ENGINE + "/access-log";

    public static final String URL_ACCESS_LOG_LIST = "/list";
    public static final String URL_ACCESS_LOG_INFO = "/info";
    public static final String URL_ACCESS_LOG_STAT = "/stat";

    public static final String AUTH_ACCESS_LOG = EngineUrl.AUTH_ENGINE + "ACCESS_LOG";

}