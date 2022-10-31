package com.hccake.ballcat.common.security.exception;

/**
 * 权限异常
 *
 * @author lingting 2022/10/28 15:51
 */
public class SecurityLoginException extends java.lang.SecurityException {

	public SecurityLoginException() {
		super("用户未登录或其他状态异常!");
	}

}
