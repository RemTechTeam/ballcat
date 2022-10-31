package com.hccake.ballcat.common.security.exception;

/**
 * 权限异常
 *
 * @author lingting 2022/10/28 15:51
 */
public class SecurityExpireException extends java.lang.SecurityException {

	public SecurityExpireException() {
		super("用户已过期!");
	}

}
