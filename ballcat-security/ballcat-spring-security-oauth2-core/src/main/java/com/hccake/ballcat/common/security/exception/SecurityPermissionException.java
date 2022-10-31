package com.hccake.ballcat.common.security.exception;

/**
 * 权限异常
 *
 * @author lingting 2022/10/28 15:51
 */
public class SecurityPermissionException extends java.lang.SecurityException {

	public SecurityPermissionException() {
		super("权限校验异常!");
	}

}
