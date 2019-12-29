package com.abc.app.engine.security.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 请求工具类
 */
public class RequestUtil {

    public static RequestAttributes requestAttributes() {
        return RequestContextHolder.getRequestAttributes();
    }

    public static ServletRequestAttributes servletRequestAttributes() {
        return (ServletRequestAttributes) requestAttributes();
    }

    public static HttpServletRequest request() {
        return servletRequestAttributes().getRequest();
    }

    public static HttpServletResponse response() {
        return servletRequestAttributes().getResponse();
    }

    public static HttpSession session() {
        return request().getSession();
    }

    public static HttpSession session(boolean create) {
        return request().getSession(create);
    }

    public static Cookie[] cookies() {
        return request().getCookies();
    }

    public static Cookie cookie(String name) {
        Cookie[] cookies = cookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

    /**
     * 用户 IP
     */
    public static String remoteIP() {
        HttpServletRequest request = request();
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.contains(",") ? ip.split(",")[0] : ip;
    }

    /**
     * 用户代理 UA
     */
    public static String userAgent() {
        HttpServletRequest request = request();
        return request.getHeader("User-Agent");
    }

}