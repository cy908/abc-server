package com.abc.app.engine.common.util;

import java.util.Date;

/**
 * 日志信息
 */
public interface LogInfo {

    Long getLogId();

    String getLogType();

    Date getLogTime();

    String getLogUser();

}