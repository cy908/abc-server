package com.abc.app.engine.common.service;

import java.util.List;

import com.abc.app.engine.common.entity.AccessLog;

public interface AccessLogService {

    List<AccessLog> findAccessLogList(AccessLog accessLog);

    long findAccessLogListCount(AccessLog accessLog);

    AccessLog findAccessLogById(long id);

    boolean saveAccessLog(AccessLog accessLog);

}