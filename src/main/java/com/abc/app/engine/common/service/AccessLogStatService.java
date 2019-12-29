package com.abc.app.engine.common.service;

import java.util.List;

import com.abc.app.engine.common.entity.AccessLogStat;

public interface AccessLogStatService {

    List<AccessLogStat> findAccessLogStatList(AccessLogStat accessLogStat);

}