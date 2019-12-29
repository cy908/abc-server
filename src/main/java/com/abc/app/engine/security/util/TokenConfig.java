package com.abc.app.engine.security.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Token配置
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TokenConfig {

    private String issuer = "auth0";
    private String secret = "mySecret";
    private Long expiry = 7200L;

    private String headerName = "X-Token";
    private String paramName = "token";

}