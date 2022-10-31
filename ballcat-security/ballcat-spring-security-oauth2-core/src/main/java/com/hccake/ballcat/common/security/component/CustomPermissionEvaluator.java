package com.hccake.ballcat.common.security.component;

import com.hccake.ballcat.common.security.SecurityEvaluator;
import com.hccake.ballcat.common.security.userdetails.User;
import com.hccake.ballcat.common.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author
 */
@Slf4j
public class CustomPermissionEvaluator implements SecurityEvaluator {

	@Override
	public User getUser() {
		return SecurityUtils.getUser();
	}

}
