package com.abc.app.engine.common.service.impl;

import java.util.List;

import com.abc.app.engine.common.entity.AccessLogStat;
import com.abc.app.engine.common.mapper.AccessLogStatMapper;
import com.abc.app.engine.common.service.AccessLogStatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccessLogStatServiceImpl implements AccessLogStatService {

    @Autowired
    private AccessLogStatMapper accessLogStatMapper;

    @Override
    public List<AccessLogStat> findAccessLogStatList(AccessLogStat accessLogStat) {
        return accessLogStatMapper.findAccessLogStatList(accessLogStat);
    }

}