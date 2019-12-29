package com.abc.app.engine.security.controller;

import java.util.ArrayList;
import java.util.List;

import com.abc.app.engine.security.config.LoginUrl;
import com.abc.app.engine.security.model.LoginRequest;
import com.abc.app.engine.security.model.LoginResponse;
import com.abc.app.engine.security.util.SecurityUtil;
import com.abc.app.engine.security.util.TokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录
 * 
 * @author 陈勇
 * @date 2018年8月8日
 * 
 */
@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtil tokenUtil;

    /**
     * 登录
     * 
     * @param loginRequest
     * @return
     */
    @PostMapping(LoginUrl.URL_LOGIN)
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        Authentication upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = authenticationManager.authenticate(upToken);
        SecurityUtil.setAuthentication(auth);
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String token = tokenUtil.create(username);
        List<String> auths = new ArrayList<>();
        userDetails.getAuthorities().forEach(item -> auths.add(item.getAuthority()));
        return new LoginResponse(token, auths);
    }

}