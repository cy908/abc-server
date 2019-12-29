package com.abc.app.engine.common.mapper;

import java.util.List;

import com.abc.app.engine.common.entity.AccessLog;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccessLogMapper {

    List<AccessLog> findAccessLogList(AccessLog accessLog);

    long findAccessLogListCount(AccessLog accessLog);

    AccessLog findAccessLogById(long id);

    long insertAccessLog(AccessLog accessLog);

}
