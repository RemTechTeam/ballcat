package com.hccake.ballcat.common.security.exception;

/**
 * 权限异常
 *
 * @author lingting 2022/10/28 15:51
 */
public class SecurityLockException extends java.lang.SecurityException {

	public SecurityLockException() {
		super("用户已被锁定!");
	}

}
