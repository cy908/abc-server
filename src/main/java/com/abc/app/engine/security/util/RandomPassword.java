package com.abc.app.engine.security.util;

import java.util.UUID;

/**
 * 随机密码
 */
public class RandomPassword implements PasswordUtil {

	@Override
	public String password(Object o) {
		return UUID.randomUUID().toString();
	}

}
