package com.hccake.ballcat.common.security;

import com.hccake.ballcat.common.security.annotation.AllowPermission;
import com.hccake.ballcat.common.security.annotation.AllowRole;
import com.hccake.ballcat.common.security.constant.SecurityConstants;
import com.hccake.ballcat.common.security.exception.SecurityPermissionException;
import com.hccake.ballcat.common.security.exception.SecurityRoleException;
import com.hccake.ballcat.common.util.ArrayUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;

/**
 * @author lingting 2022/10/28 13:53
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
@Order(SecurityConstants.ORDER_ASPECT_PERMISSION)
public class SecurityPermissionAspect {

	private final SecurityEvaluator evaluator;

	@Pointcut("execution(@(@live.lingting.tools.security.annotation.MetaCacheAnnotation *) * *(..))")
	public void pointcut() {
		//
	}

	public <T extends Annotation> T getAnnotation(ProceedingJoinPoint point, Class<T> cls) {
		T t = null;

		Signature signature = point.getSignature();
		if (signature instanceof MethodSignature) {
			t = ((MethodSignature) signature).getMethod().getAnnotation(cls);
		}

		if (t == null) {
			t = point.getTarget().getClass().getAnnotation(cls);
		}

		return t;
	}

	@Around("pointcut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		AllowPermission allowPermission = getAnnotation(point, AllowPermission.class);

		if (!valid(evaluator::hasPermission, allowPermission.serialCode(), allowPermission.value(),
				allowPermission.parallelCode())) {
			throw new SecurityPermissionException();
		}

		AllowRole allowRole = getAnnotation(point, AllowRole.class);

		if (!valid(evaluator::hasRole, allowRole.serialCode(), allowRole.value(), allowRole.parallelCode())) {
			throw new SecurityRoleException();
		}

		return point.proceed();
	}

	/**
	 * @param has
	 * @param serial
	 * @param parallelArray
	 * @return boolean
	 */
	protected boolean valid(Predicate<String> has, String[] serial, String[]... parallelArray) {
		// 串行权限已设置
		if (!ArrayUtils.isEmpty(serial)) {
			for (String code : serial) {
				// 串行权限中存在任一权限不满足, 则未通过校验
				if (!has.test(code)) {
					return false;
				}
			}
		}

		// 对并行权限进行判断
		for (String[] parallel : parallelArray) {
			// 未设置 跳过
			if (ArrayUtils.isEmpty(parallel)) {
				continue;
			}

			for (String code : parallel) {
				// 拥有任意一个权限则通过校验
				if (has.test(code)) {
					return true;
				}
			}
		}

		return true;
	}

}
