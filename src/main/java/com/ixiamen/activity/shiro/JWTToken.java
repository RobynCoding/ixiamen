package com.ixiamen.activity.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author luoyongbin
 * @since 2018-05-03
 */
public class JWTToken implements AuthenticationToken {

    // 密钥
    private final String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
