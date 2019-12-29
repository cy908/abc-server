package com.abc.app.engine.common.controller;

import java.util.List;

import com.abc.app.engine.common.config.AccessLogUrl;
import com.abc.app.engine.common.entity.AccessLog;
import com.abc.app.engine.common.entity.AccessLogStat;
import com.abc.app.engine.common.service.AccessLogService;
import com.abc.app.engine.common.service.AccessLogStatService;
import com.abc.app.engine.common.util.PageData;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 访问日志
 * 
 * @author 陈勇
 * @date 2018年8月8日
 *
 */
@RestController
@RequestMapping(AccessLogUrl.URL_ACCESS_LOG)
public class AccessLogController {

    @Autowired
    private AccessLogService accessLogService;

    @Autowired
    private AccessLogStatService accessLogStatService;

    /**
     * 访问日志列表
     * 
     * @param accessLog
     * @return
     */
    @PostMapping(value = AccessLogUrl.URL_ACCESS_LOG_LIST)
    @Secured(AccessLogUrl.AUTH_ACCESS_LOG)
    public PageData<AccessLog> accessLogList(@RequestBody AccessLog accessLog) {
        if (accessLog != null && accessLog.getEndTime() != null) {
            accessLog.setEndTime(DateUtils.addDays(accessLog.getEndTime(), 1));
        }
        long count = accessLogService.findAccessLogListCount(accessLog);
        List<AccessLog> list = null;
        if (count > 0) {
            list = accessLogService.findAccessLogList(accessLog);
        }
        return new PageData<AccessLog>(list, count);
    }

    /**
     * 访问日志信息
     * 
     * @param accessLog
     * @return
     */
    @PostMapping(value = AccessLogUrl.URL_ACCESS_LOG_INFO)
    @Secured(AccessLogUrl.AUTH_ACCESS_LOG)
    public AccessLog accessLogInfo(@RequestBody AccessLog accessLog) {
        if (accessLog.getId() == null) {
            accessLog.setId(0L);
        }
        return accessLogService.findAccessLogById(accessLog.getId());
    }

    /**
     * 访问日志统计列表
     * 
     * @param accessLogStat
     * @return
     */
    @PostMapping(value = AccessLogUrl.URL_ACCESS_LOG_STAT)
    @Secured(AccessLogUrl.AUTH_ACCESS_LOG)
    public List<AccessLogStat> accessLogStatList(@RequestBody AccessLogStat accessLogStat) {
        return accessLogStatService.findAccessLogStatList(accessLogStat);
    }

}