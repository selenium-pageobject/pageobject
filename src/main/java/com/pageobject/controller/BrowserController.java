package com.pageobject.controller;

import java.io.IOException;

/**
 * Interface for browser com.pageobject.controller implementations. Containing the base methods
 * needed to interact with browser.
 * 
 * @author michal.nahlik
 * 
 */
public interface BrowserController {

	/**
	 * Adds the value after actual value of an input field if the value is not
	 * <code>null</code> or empty string. Can also be used to set the value of
	 * combo boxes, check boxes, etc. In these cases, value should be the value
	 * of the option selected, not the visible text.
	 * 
	 * @param locator
	 *            the locator of an web element. The locator type can be
	 *            specified by prefix ('id=', 'name=', 'css=', 'xpath=',
	 *            'text='). If the identifier type is not specified, it will be
	 *            taken as it's 'id='.
	 * @param value
	 *            value to type
	 */
	public void type(String locator, String value);

	/**
	 * Removes the value of the specified element (i.e. clears the text in an
	 * input value).
	 * 
	 * @param locator
	 *            the locator of an web element. The locator type can be
	 *            specified by prefix ('id=', 'name=', 'css=', 'xpath=',
	 *            'text='). If the identifier type is not specified, it will be
	 *            taken as it's 'id='.
	 */
	public void clear(String locator);

	/**
	 * Clicks on a link, button, check box or radio button.
	 * 
	 * @param locator
	 *            the locator of an web element. The locator type can be
	 *            specified by prefix ('id=', 'name=', 'css=', 'xpath=',
	 *            'text='). If the identifier type is not specified, it will be
	 *            taken as it's 'id='.
	 */
	public void click(String locator);

	/**
	 * Select an option from a drop-down using an option locator only if the
	 * value is not <code>null</code> or empty string. This allows you to
	 * specify the type of locator and value.
	 * 
	 * @param locator
	 *            the locator of an web element. The locator type can be
	 *            specified by prefix ('id=', 'name=', 'css=', 'xpath=',
	 *            'text='). If the identifier type is not specified, it will be
	 *            taken as it's 'id='.
	 * 
	 * @param option
	 *            the value to be selected, the value can be specified by prefix
	 *            ('label=' - the visible text, 'value=' - the option value,
	 *            'index=' - index of the option). If the type is not specified
	 *            it will taken as 'value='.
	 */
	public void select(String locator, String option);

	/**
	 * Opens a URL in a new window. The new web page has to be selected if you
	 * want to interact with it.
	 * 
	 * @param url
	 *            the URL of the target website
	 */
	public void open(String url);

	/**
	 * Opens a URL in a new window and change the focus to it.
	 * 
	 * @param url
	 *            the URL of the target website
	 */
	public void openAndSelectWindow(String url);

	/**
	 * Closes the current web page.
	 */
	public void closePage();

	/**
	 * Closes all opened windows except the specified.
	 * 
	 * @param windowIdentifier
	 *            the window identifier can be a webpage title or a name
	 *            specified by prefix ('name=', 'title='). Doesn't have to be
	 *            the full text, a partial text is enough.
	 */
	public void closeAllBut(String windowIdentifier);

	/**
	 * Returns the title of the current web page.
	 * 
	 * @return The current web page title.
	 */
	public String getTitle();

	/**
	 * Return an array with all opened windows titles.
	 * 
	 * @return Array of Strings with all opened windows titles.
	 */
	public String[] getAllWindowTitles();

	/**
	 * Return an array of all opend windows names.
	 * 
	 * @return Array of Strings with all opened windows names.
	 */
	public String[] getAllWindowNames();

	/**
	 * Selects a window by window identifier, giving a focus to it.
	 * 
	 * @param windowIdentifier
	 *            the window identifier can be a web page title or a name
	 *            specified by prefix ('name=', 'title='). Doesn't have to be
	 *            the whole text a partial is enough. If the type is not
	 *            specified it will be taken as 'title='.
	 */
	public void selectWindow(String windowIdentifier);

	/**
	 * Selects a window by it's title, giving a focus to it.
	 * 
	 * @param title
	 *            the title a of the web page that should be selected. The part
	 *            of the window title is enough.
	 */
	public void selectWindowByTitle(String title);

	/**
	 * Selects a windows by it's name, giving a focus to it.
	 * 
	 * @param name
	 *            the window document name of a web page that should be
	 *            selected. The part of the name is enough.
	 */
	public void selectWindowByName(String name);

	/**
	 * Returns a value of the specified web element.
	 * 
	 * @param locator
	 *            the locator of a web element. The locator type can be
	 *            specified by prefix ('id=', 'name=', 'css=', 'xpath=',
	 *            'text=').
	 * 
	 * @return a String representation of the element value.
	 */
	public String getElementValue(String locator);

	/**
	 * Returns a value of the specified web element attribute.
	 * 
	 * @param locator
	 *            the locator of an web element. The locator type can be
	 *            specified by prefix ('id=', 'name=', 'css=', 'xpath=',
	 *            'text='). If the identifier type is not specified, it will be
	 *            taken as it's 'id='.
	 * 
	 * @param attributeName
	 *            name of the attribute it's value should be returned.
	 * 
	 * @return String representation of the attribute value.
	 */
	public String getElementAttribute(String locator, String attributeName);

	/**
	 * Returns the visible innerText of specified element, including
	 * sub-elements.
	 * 
	 * @param locator
	 *            the locator of an web element. The locator type can be
	 *            specified by prefix ('id=', 'name=', 'css=', 'xpath=',
	 *            'text='). If the identifier type is not specified, it will be
	 *            taken as it's 'id='.
	 * 
	 * @return The visible text of the element.
	 */
	public String getText(String locator);

	/**
	 * Checks whether the web element is present on current web page.
	 * 
	 * @param locator
	 *            the locator of an web element. The locator type can be
	 *            specified by prefix ('id=', 'name=', 'css=', 'xpath=',
	 *            'text='). If the identifier type is not specified, it will be
	 *            taken as it's 'id='.
	 * 
	 * @return true if the web element was found on current web page, false
	 *         otherwise.
	 */
	public boolean isElementPresent(String locator);

	/**
	 * Checks whether the web element is enabled on current web page.
	 * 
	 * @param locator
	 *            the locator of an web element. The locator type can be
	 *            specified by prefix ('id=', 'name=', 'css=', 'xpath=',
	 *            'text='). If the identifier type is not specified, it will be
	 *            taken as it's 'id='.
	 * 
	 * @return true if the web element is enabled, false otherwise.
	 */
	public boolean isElementEnabled(String locator);

	/**
	 * Finds all occurrences of the specified web element and returns the count
	 * of it.
	 * 
	 * @param locator
	 *            the locator of an web element. The locator type can be
	 *            specified by prefix ('id=', 'name=', 'css=', 'xpath=',
	 *            'text='). If the identifier type is not specified, it will be
	 *            taken as it's 'id='.
	 * 
	 * @return the count of occurrences of the specified web element.
	 */
	public int getElementCount(String locator);

	/**
	 * Captures the screen shot and saves a file to specified location.
	 * 
	 * @param screenshotPath
	 *            target path where the screen shot file should be saved.
	 * 
	 * @throws IOException
	 *             can throw IOException if there are any troubles with the file
	 *             manipulation.
	 */
	public void captureScreenshot(String screenshotPath) throws IOException;

	/**
	 * Returns the current web page state.
	 * 
	 * @return String representation of that state.
	 */
	public String getPageState();

	/**
	 * Checks where the page is fully loaded.
	 * 
	 * @return true if the page is fully loaded, false otherwise.
	 */
	public boolean isPageLoaded();

	/**
	 * Waits for the page to be fully loaded.
	 * 
	 * @param timeout
	 *            the amount of time that should be waited at top, expressed in
	 *            milliseconds.
	 */
	public void waitForPageToLoad(long timeout);

	/**
	 * Waits until the specified web element is present.
	 * 
	 * @param locator
	 *            the locator of an web element. The locator type can be
	 *            specified by prefix ('id=', 'name=', 'css=', 'xpath=',
	 *            'text='). If the identifier type is not specified, it will be
	 *            taken as it's 'id='.
	 * 
	 * @param timeout
	 *            the amount of time that should be waited at top, expressed in
	 *            milliseconds.
	 */
	public void waitForElementPresent(String locator, long timeout);

	/**
	 * Waits until the condition is true. Can be used for example as:
	 * waitUntil(isElementEnabled("login"), 5000);
	 * 
	 * @param condition
	 *            the condition that is supposed to be fulfilled.
	 * 
	 * @param timeout
	 *            the amount of time that should be waited at top, expressed in
	 *            milliseconds.
	 */
	public void waitUntil(String script, long timeout);

	/**
	 * Waits for a given amount of time, causing the thread to sleep.
	 * 
	 * @param time
	 *            the amount of time that should be waited.
	 */
	public void waitFor(long time);
	
	/**
	 * Executes javascript in current window.
	 * 
	 * @param script script
	 * @return an object containing data returned by javascript 
	 */
	public Object executeScript(String script);

	/**
	 * Get a source of the current page.
	 * 
	 * @return
	 */
	public String getPageSource();
	
}
