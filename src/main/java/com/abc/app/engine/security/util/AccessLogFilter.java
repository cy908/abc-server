package com.abc.app.engine.security.util;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.app.engine.common.entity.AccessLog;
import com.abc.app.engine.common.service.AccessLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 访问日志拦截器
 */
public class AccessLogFilter extends OncePerRequestFilter {

    @Autowired
    private AccessLogService accessLogService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 保存访问日志-START
        Date now = new Date();
        AccessLog accessLog = new AccessLog();
        accessLog.setAccessTime(now);
        accessLog.setAccessIP(RequestUtil.remoteIP());
        accessLog.setAccessUA(RequestUtil.userAgent());
        accessLog.setAccessURI(request.getRequestURI());
        accessLog.setAccessUser(SecurityUtil.getUsername());
        CompletableFuture.runAsync(() -> accessLogService.saveAccessLog(accessLog));
        // 保存访问日志-END
        filterChain.doFilter(request, response);
    }

}
