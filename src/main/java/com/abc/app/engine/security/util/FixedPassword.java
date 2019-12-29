package com.abc.app.engine.security.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 固定密码（默认：123456）
 */
public class FixedPassword implements PasswordUtil {

	public static final String DEFAULT_FIXED = "123456";

	private String fixed = DEFAULT_FIXED;

	public FixedPassword() {
	}

	public FixedPassword(final String fixed) {
		if (StringUtils.isNotBlank(fixed)) {
			this.fixed = fixed;
		}
	}

	@Override
	public String password(final Object o) {
		return fixed;
	}

}
