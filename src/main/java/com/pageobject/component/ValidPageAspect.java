/*
 * $Id: ValidPageAspect.java 102464 2013-08-21 15:35:16Z nahlikm1 $
 * 
 * Copyright (c) 2010 AspectWorks, spol. s r.o.
 */
package com.pageobject.component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pageobject.controller.BrowserController;


/**
 * Aspect checking page validity before every method call on a page.
 *
 * @author Pavel Muller
 * @version $Revision: 102464 $
 */
@Aspect
public class ValidPageAspect {
	private static Logger logger = LoggerFactory.getLogger(ValidPageAspect.class);
	
	private BrowserController browser;
	
	/**
	 * Browser.
	 * @param browser BrowserController that should be used.
	 */
	@Autowired
	public void setBrowserController(BrowserController browser) {
		this.browser = browser;
	}
	
	/**
	 * Check if the browser is on page which method is being called.
	 * 
	 * @param joinPoint current method call
	 * @param page target page being called
	 */
	@Before("@target(component.Page) && target(page))")
	public void validatePage(JoinPoint joinPoint, AbstractPage page) {
		LoggerFactory.getLogger(page.getClass()).debug("Invocation of page method '{}'", joinPoint.getSignature().getName());
		
		if (!page.isValidPage()) {
			String pageMethod = page.getClass().getSimpleName() + "." + joinPoint.getSignature().getName();
			String msg = "Browser state is invalid while calling page method '" + pageMethod +
					"'. Current window title is: " + browser.getTitle();
			logger.error(msg);
			throw new IllegalStateException(msg);
		}
	}

}
