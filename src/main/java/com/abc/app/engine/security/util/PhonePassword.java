package com.abc.app.engine.security.util;

import com.abc.app.engine.security.entity.User;

import org.apache.commons.lang3.StringUtils;

/**
 * 手机号做为密码（默认后6位）
 * 
 * <p>
 * 手机号为空时取默认固定密码（123456）
 * </p>
 */
public class PhonePassword implements PasswordUtil {

	public static final int DEFAULT_LENGTH = 6;

	private int length = DEFAULT_LENGTH;

	public PhonePassword() {
	}

	public PhonePassword(int length) {
		this.length = length;
	}

	@Override
	public String password(Object o) {
		if (o instanceof User) {
			String phone = ((User) o).getPhone();
			if (StringUtils.isNotBlank(phone) && phone.length() > length) {
				return phone.substring(phone.length() - length);
			}
		}
		return FixedPassword.DEFAULT_FIXED;
	}

}
