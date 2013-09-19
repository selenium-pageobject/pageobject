/*
 * $Id: AbstractPage.java 102464 2013-08-21 15:35:16Z nahlikm1 $
 * 
 * Copyright (c) 2010 AspectWorks, spol. s r.o.
 */
package com.pageobject.component;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.pageobject.TableControl;
import com.pageobject.controller.BrowserController;


/**
 * Convenience base class for all Pages that are used for tests.
 * Don't forget to annotate the page with {@link Page} annotation.
 * 
 * <p>This class offers lifecycle methods {@link #isValidPage()} and {@link #init(Object...)}
 * as well as logic for navigation: {@link #navigateTo(Class, Object...)}.
 *
 * @author Pavel Muller
 * @version $Revision: 102464 $
 */
public abstract class AbstractPage extends AbstractComponent {
	
	/**
	 * Navigates to a given page.
	 * Optionally you may specify init parameter to pass to the page.
	 * <p>This method creates a prototype page bean, call {@link #assertValidPage()} and
	 * {@link #init(Object...)} with optioanal init parameters.
	 * @param pageClass class with {@link Page} annotation
	 * @param params optional parameters to pass to the page
	 * @return configured page
	 * @throws IllegalArgumentException if the page class is not annotated with {@link Page}
	 */
	protected <T extends AbstractPage> T navigateTo(Class<T> pageClass, Object... params) {
		logger.info("Navigating to page '{}'", pageClass.getSimpleName());		
		T page;
		
		// get page prototype bean from Spring context
		try {
			page = applicationContext.getBean(pageClass);
		} catch (NoSuchBeanDefinitionException e) {
			IllegalArgumentException ex = new IllegalArgumentException("Page '" + pageClass.getSimpleName() + "' not found. " +
					"Is it configured properly? Use @Page annotation.", e);
			logger.error(ex.getMessage(), ex);
			throw ex;
		} catch (BeansException e) {
			IllegalArgumentException ex = new IllegalArgumentException("Page '" + pageClass.getSimpleName() + "' configuration problem.", e);
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
		
		// initialize page
		page.init(params);
		
		// check if the browser is on this page
		if (!page.isValidPage()) {
			throw new IllegalStateException("Browser state is invalid when trying to navigate to " + getClass().getSimpleName() +
					". Current window title is: " + browser.getTitle());
		}
		
		return page;
	}
	
	/**
	 * Callback to initialize the page.
	 * @param params optional parameters, may be <code>null</code>
	 */
	protected void init(Object... params) {
	}
	
	/**
	 * Checks whether the browser is on the current page.
	 * Override this method if you need this check.
	 * @see ValidPageAspect
	 */
	public abstract boolean isValidPage();

	/**
	 * Close current browser window and select the main one.
	 */
	public void closePage() {
		browser.closePage();
		browser.selectWindow("null");
	}
	
	/**
	 * Return the title of current page.
	 * 
	 * @see {@link BrowserController#getTitle()}
	 * 
	 * @return The title of current page.
	 */
	public String getTitle() {
		return browser.getTitle();
	}
	
	/**
	 * Returns the current web page state.
	 * 
	 * @see {@link BrowserController#getPageState()}
	 * 
	 * @return String representation of that state.
	 */
	public String getPageState() {
		return browser.getPageState();
	}
	
	/**
	 * Checks if the page is fully loaded.
	 * 
	 * @see {@link BrowserController#isPageLoaded()}
	 * 
	 * @return true if the page is fully loaded, false otherwise.
	 */
	public boolean isPageLoaded() {
		return browser.isPageLoaded();
	}
	
	/**
	 * Waits for the page to be fully loaded. 
	 * 
	 * @see {@link BrowserController#waitForPageToLoad(long)}
	 * 
	 * @param timeout
	 *            the amount of time that should be waited at top, expressed in
	 *            milliseconds.
	 */
	public void waitForPageToLoad(long timeout) {
		browser.waitForPageToLoad(timeout);
	}
	
	/**
	 * Returns {@link TableControl} for specified table locator.
	 * Creates new table control for each call.
	 * @return configured table control
	 */
	public TableControl getTableControl() {
		return applicationContext.getBean(TableControl.class);
	}

}
