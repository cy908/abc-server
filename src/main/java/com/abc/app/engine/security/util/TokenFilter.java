package com.abc.app.engine.security.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Token拦截器
 */
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenConfig tokenConfig;

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (SecurityUtil.getAuthentication() == null) {
            // 取HEADER中的TOKEN
            String token = request.getHeader(tokenConfig.getHeaderName());
            // 取URL中的TOKEN
            if (token == null) {
                token = request.getParameter(tokenConfig.getParamName());
            }
            if (token != null && tokenUtil.verify(token)) {
                String username = tokenUtil.getUsername(token);
                Long expiry = tokenUtil.getExpiry(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                        userDetails.getPassword(), userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityUtil.setAuthentication(authentication);
                String refreshToken = tokenUtil.create(username, expiry);
                response.setHeader(tokenConfig.getHeaderName(), refreshToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
