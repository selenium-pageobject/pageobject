/*
 * $Id: AbstractComponent.java 102464 2013-08-21 15:35:16Z nahlikm1 $
 * 
 * Copyright (c) 2010 AspectWorks, spol. s r.o.
 */
package com.pageobject.component;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import com.pageobject.DefaultFormattingService;
import com.pageobject.FormattingService;
import com.pageobject.TableControl;
import com.pageobject.controller.BrowserController;

/**
 * Base class for reusable web based automated test components managed by
 * Spring.
 * 
 * <p>
 * Contains configured web based automated test objects and other attributes.
 * Plus contains convenient operations.
 * 
 * <p>
 * Annotate components with {@link Component} annotation to make them a Spring
 * bean and autowire them in automated test cases.
 * 
 * @see Component
 * 
 * @author Pavel Muller
 * @author michal.nahlik
 * @version $Revision: 102464 $
 */
public abstract class AbstractComponent {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected static final String DEFAULT_CONTEXT = "";

	protected BrowserController browser;
	protected String url;
	protected String context = DEFAULT_CONTEXT;

	protected FormattingService formattingService = new DefaultFormattingService();

	/**
	 * The {@link ApplicationContext} that was injected into this test instance
	 * via {@link #setApplicationContext(ApplicationContext)}.
	 */
	@Autowired
	protected ApplicationContext applicationContext;

	/**
	 * Configured BrowserController tool.
	 * 
	 * @param driver
	 *            the driver to set
	 */
	@Autowired
	public void setBrowserController(BrowserController browser) {
		this.browser = browser;
	}

	/**
	 * Web application context. Default is root context. Mapped to configuration
	 * property: webapp.context
	 * 
	 * @param context
	 *            context path WITHOUT trailing slash
	 */
	@Value("${webapp.context}")
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * Base url to be used for tests. Mapped to configuration property:
	 * webapp.url
	 * 
	 * @param url
	 *            the base url to be used in tests
	 */
	@Value("${webapp.url}")
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Set optional {@link FormattingService}. Default is
	 * {@link DefaultFormattingService}.
	 * 
	 * @param formattingService
	 *            new formatting service
	 */
	@Autowired(required = false)
	public void setFormattingService(FormattingService formattingService) {
		this.formattingService = formattingService;
	}

	/**
	 * Open URL in web application context. The final URL is url + context +
	 * path.
	 * 
	 * @param path
	 *            URL to open. Context relative.
	 */
	protected void open(String path) {
		browser.open(url + context + path);
	}

	/**
	 * Open a URL in a new window and change the focus to it.Context relative.
	 * The final URL is url + context + path.
	 * 
	 * @param url
	 *            the URL of the target website
	 */
	public void openAndSelectWindow(String path) {
		browser.openAndSelectWindow(url + context + path);
	}

	/**
	 * Clicks on a link, button, checkbox or radio button using the
	 * {@link BrowserController}
	 * 
	 * @see {@link BrowserController#click(String)}
	 * 
	 * @param locator
	 *            an element locator
	 */
	protected void click(String locator) {
		browser.click(locator);
	}

	/**
	 * Add the value after actual value of an input field if the value is not
	 * <code>null</code>. Can also be used to set the value of
	 * combo boxes, check boxes, etc. In these cases, value should be the value
	 * of the option selected, not the visible text.
	 * 
	 * @see {@link BrowserController#type(String)}
	 * 
	 * @param locator
	 *            the locator of the element
	 * @param value
	 *            value to type
	 */
	protected void type(String locator, String value) {
		if(value != null) {
			browser.type(locator, value);
		}
	}

	/**
	 * Types the date formated by {@link #formattingService} into the specified
	 * input, if the date is not <code>null</code>.
	 * 
	 * @see {@link BrowserController#type(String, String)}
	 * 
	 * @param locator
	 *            the locator of the web element
	 * @param date
	 *            date to format and type in
	 */
	protected void typeDate(String locator, Date date) {
		type(locator, formattingService.formatDate(date));
	}

	/**
	 * Types the number value formated by {@link #formattingService} into the
	 * specified input field if the number is not <code>null</code>.
	 * 
	 * @see {@link BrowserController#type(String, String)}
	 * 
	 * @param locator
	 *            the locator of the web element
	 * @param number
	 *            number to format and type in
	 */
	protected void typeNumber(String locator, Number number) {
		type(locator, formattingService.formatNumber(number));
	}

	/**
	 * Clear the value of the specified input.
	 * 
	 * @see {@link BrowserController#clear(String)}
	 * 
	 * @param locator
	 *            the locator of the element
	 */
	protected void clear(String locator) {
		browser.clear(locator);
	}

	/**
	 * Select an option from a drop-down using an option locator only if the
	 * value is not <code>null</code>. The value can be the visible text or the
	 * option label.
	 * 
	 * @see {@link BrowserController#select(String, String)}
	 * 
	 * @param locator
	 *            input field name (e.g. username)
	 * @param option
	 *            value to be selected, can be specified as label, value or the
	 *            index
	 */
	protected void select(String locator, String option) {
		if(option != null) {
			browser.select(locator, option);
		}
	}

	/**
	 * Tries to find an element on a current page and returns true if it is,
	 * false otherwise.
	 * 
	 * @see {@link BrowserController#isElementPresent(String)}
	 * 
	 * @param locator
	 *            element's locator.
	 * @return true if elements is present, false otherwise.
	 */
	public boolean isElementPresent(String locator) {
		return browser.isElementPresent(locator);
	}

	/**
	 * Checks whether the web element is enabled on current web page.
	 * 
	 * @see {@link BrowserController#isElementEnabled(String)}
	 * 
	 * @param locator
	 *            element's locator
	 * @return
	 */
	public boolean isElementEnabled(String locator) {
		return browser.isElementEnabled(locator);
	}

	/**
	 * Tries to find all occurrences of an element using the given locator and
	 * returns its count.
	 * 
	 * @param locator
	 *            element's locator.
	 * @return number of element occurrences found on current page.
	 */
	public Integer getElementCount(String locator) {
		return browser.getElementCount(locator);
	}

	/**
	 * Returns the value of the specified element.
	 * 
	 * @see {@link BrowserController#getElementValue(String)}
	 * 
	 * @param locator
	 *            element's locator
	 * @return String representation of the element's value
	 */
	public String getElementValue(String locator) {
		return browser.getElementValue(locator);
	}

	/**
	 * Returns the value of specified web element attribute.
	 * 
	 * @see {@link BrowserController#getElementAttribute(String, String)}
	 * 
	 * @param locator
	 *            element's locator
	 * @param attributeName
	 *            name of the attribute
	 * @return String representation of the attribute's value
	 */
	public String getElementAttribute(String locator, String attributeName) {
		return browser.getElementAttribute(locator, attributeName);
	}

	/**
	 * Returns the visible text of the element (including its subelements).
	 * 
	 * @see {@link BrowserController#getText(String)}
	 * 
	 * @param locator
	 *            element's locator
	 * @return visible text of the element and its sub-elements
	 */
	public String getText(String locator) {
		return browser.getText(locator);
	}

	/**
	 * Waits for an element to be present on the current page. You can use this
	 * method if the BrowserControll have not recognized correctly that the page
	 * is not loaded yet.
	 * 
	 * @see {@link BrowserController#waitForElementPresent(String, long)}
	 * 
	 * @param locator
	 *            element's locator (f.e. an XPath expression).
	 * @param waitSeconds
	 *            number of seconds to wait.
	 */
	public void waitForElementPresent(String locator, long waitSeconds) {
		browser.waitForElementPresent(locator, waitSeconds);
	}

	/**
	 * Waits for a given amount of time in milliseconds.
	 * 
	 * @see {@link BrowserController#waitFor(long)}
	 * 
	 * @param time
	 *            amount of time in milliseconds.
	 */
	public void waitFor(long time) {
		browser.waitFor(time);
	}

	/**
	 * Returns {@link TableControl} for specified table locator. Creates new
	 * table control for each call.
	 * 
	 * @return configured table control
	 */
	public TableControl getTableControl() {
		return applicationContext.getBean(TableControl.class);
	}

}
