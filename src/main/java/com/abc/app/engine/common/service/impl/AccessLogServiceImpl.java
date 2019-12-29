package com.abc.app.engine.common.service.impl;

import java.util.List;

import com.abc.app.engine.common.entity.AccessLog;
import com.abc.app.engine.common.mapper.AccessLogMapper;
import com.abc.app.engine.common.service.AccessLogService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccessLogServiceImpl implements AccessLogService {

    private static final int MAX_LENGTH = 100;

    @Autowired
    private AccessLogMapper accessLogMapper;

    @Override
    public List<AccessLog> findAccessLogList(AccessLog accessLog) {
        accessLog.setSearch(StringUtils.trimToNull(accessLog.getSearch()));
        return accessLogMapper.findAccessLogList(accessLog);
    }

    @Override
    public long findAccessLogListCount(AccessLog accessLog) {
        accessLog.setSearch(StringUtils.trimToNull(accessLog.getSearch()));
        return accessLogMapper.findAccessLogListCount(accessLog);
    }

    @Override
    public AccessLog findAccessLogById(long id) {
        return accessLogMapper.findAccessLogById(id);
    }

    @Override
    public boolean saveAccessLog(AccessLog accessLog) {
        accessLog.setAccessURI(StringUtils.substring(accessLog.getAccessURI(), 0, MAX_LENGTH));
        accessLog.setAccessUA(StringUtils.substring(accessLog.getAccessUA(), 0, MAX_LENGTH));
        accessLogMapper.insertAccessLog(accessLog);
        long id = accessLog.getId();
        return id > 0 ? true : false;
    }

}