package com.abc.app.engine.common.mapper;

import java.util.List;

import com.abc.app.engine.common.entity.AccessLogStat;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccessLogStatMapper {

    List<AccessLogStat> findAccessLogStatList(AccessLogStat accessLogStat);

}
