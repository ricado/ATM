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
		System.out.println("===========����ع�============");
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		System.out.println("===========�ع��ɹ�============");
	}
	
	@Before("execution(* com.atm.dao.impl.*.*(..))")
	public void before(){
		System.out.println("before==================");
	}
	//@After("execution(* com.atm.dao.impl.*.*(..))")
	public void after(){
		System.out.println("===========����ع�============");
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		System.out.println("===========�ع��ɹ�============");
	}
}
