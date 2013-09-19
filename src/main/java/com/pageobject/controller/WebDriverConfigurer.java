/*
 * $Id: WebDriverConfigurer.java 102464 2013-08-21 15:35:16Z nahlikm1 $
 * 
 * Copyright (c) 2010 AspectWorks, spol. s r.o.
 */
package com.pageobject.controller;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * Configures WebDriver within set of test cases. Contains {@link #configure()}
 * methods to be run before first test case. Currently sets up Page load wait
 * timeout, implicit wait timeout (how long to search an element) and maximazes
 * the window. Reimplement or extend it if you need other configurations to be
 * done.
 * 
 * @author Pavel Muller
 * @version $Revision: 102464 $
 */
public class WebDriverConfigurer {

	protected static final long DEFAULT_PAGE_LOAD_TIMEOUT = 30000;
	protected static final long DEFAULT_IMPLICIT_TIMEOUT = 3000;
	private long timeout = DEFAULT_PAGE_LOAD_TIMEOUT;
	private long implicitTimeout = DEFAULT_IMPLICIT_TIMEOUT;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private WebDriver driver;

	
	/**
	 * Selenium WebDriver instance to configure.
	 * @param driver the selenium WebDriver to set
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * Page load timeout. Default is 30000 (ie. 30 seconds).
	 * Mapped to configuration property: selenium.timeout
	 * @param timeout the timeout to set
	 */
	@Value("${selenium.timeout:30000}")
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	
	/**
	 * Implicit timeout specifies how long should webdriver search for an
	 * element. This can seriously affect duration of the tests.
	 * 
	 * @param timeout the timeout to set
	 */
	@Value("${selenium.implicit.timeout:3000}")
	public void setImplicitTimeout(long timeout) {
		this.implicitTimeout = timeout;
	}
	
	public void configure() {
		logger.info("Page load wait set up to " + timeout + " ms");
		driver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.MILLISECONDS);
		driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.MILLISECONDS);
		driver.manage().window().maximize();
	}
	

}
