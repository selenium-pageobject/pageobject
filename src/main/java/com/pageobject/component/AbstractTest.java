/*
 * $Id: AbstractTest.java 102464 2013-08-21 15:35:16Z nahlikm1 $
 * 
 * Copyright (c) 2009 AspectWorks, spol. s r.o.
 */
package com.pageobject.component;

import org.junit.Rule;
import org.junit.rules.ExternalResource;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

/**
 * Base class for automated web page tests.
 * (You should reimplement the testRule if you want to use @After to tear down something.)
 * 
 * @author Zdenek Jonas
 * @author Pavel Muller
 * @author Lubos Racansky
 * @version $Revision: 102464 $
 */
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractTest extends AbstractComponent {
	protected static final String DEFAULT_SCREENSHOT_DIR = "/tmp/";

	protected String screenshotDir = DEFAULT_SCREENSHOT_DIR;
	
	/**
	 * Screenshot directory to put images after test failures.
	 * Default is <code>/tmp/</code>
	 * <p>Mapped to configuration property: selenium.screenshot.dir
	 * @param screenshotDir absolute path WITH trailing slash
	 */
	@Value("${selenium.screenshot.dir}")
	public void setScreenshotDir(String screenshotDir) {
		this.screenshotDir = screenshotDir;
	}
	
	/**
	 * Capture a screen shot if test fails. 
	 * !Be careful, failed is done after the @After method, so if you want
	 *  to use something to clean the driver using the After (e.g. logout, delete cookies ...)
	 *  you should create a TestRule around this rule and setup @After in it.
	 */
	public TestRule screenshotRule = new TestWatcher() {

        @Override
        public void failed(Throwable t, Description description) {
        	// Get test method name

            String className = description.getClassName();
			String methodName = description.getMethodName();
			String screenShotPath = screenshotDir + className + "." + methodName + ".png";

            captureScreenshot(screenShotPath);
            logger.info("Test " + className + "." + methodName + " failed.");
            logger.info("Created a screenshot [{}]", screenShotPath);
        }

    };
    
    /**
     * Method to capture and save screen shot to desired path.
     * @param screenshotPath full path where the screen shot should be saved (with the exact file name).
     */
    protected void captureScreenshot(String screenshotPath) { 
		try {
			browser.captureScreenshot(screenshotPath);
		} catch (Exception e) {
			logger.error("Unable to capture screenshot", e) ;
		}
	}
    
    /**
	 * Reimplement this rule if you want to use After method to tear down
	 * something. <b>If you use @After it's done before the screenshot rule!</b>
	 */
    @Rule
	public TestRule testRule = RuleChain.outerRule(new ExternalResource() {
		
		@Override
		protected void before() throws Throwable {
			logger.info("Starting a test.");
		}

		@Override
		protected void after() {
			logger.info("Current test is done.");
		}
	}).around(screenshotRule);
	
}
