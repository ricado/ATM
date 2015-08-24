package com.atm.service;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

//@Aspect
public class AspectjTest {
	
	@AfterThrowing(throwing="ex",pointcut="execution(* com.atm.test.*.*(..))")
	public void doRecoveyAction(Throwable ex){
		System.out.println("===========事务回滚============");
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		System.out.println("===========回滚成功============");
	}
	
	@Before("execution(* com.atm.dao.impl.*.*(..))")
	public void before(){
		System.out.println("before==================");
	}
	//@After("execution(* com.atm.dao.impl.*.*(..))")
	public void after(){
		System.out.println("===========事务回滚============");
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		System.out.println("===========回滚成功============");
	}
}
