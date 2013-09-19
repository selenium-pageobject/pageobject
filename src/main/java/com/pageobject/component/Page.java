/*
 * $Id: Page.java 102464 2013-08-21 15:35:16Z nahlikm1 $
 * 
 * Copyright (c) 2010 AspectWorks, spol. s r.o.
 */
package com.pageobject.component;

import java.lang.annotation.*;

/**
 * Indicated that an annotated class is a page used for automated web test using page object pattern.
 * 
 * <p>This is an implementation of Page Objects pattern for web automated tests.
 * For more information see 
 * <a href="http://code.google.com/p/selenium/wiki/PageObjects">http://code.google.com/p/selenium/wiki/PageObjects</a>
 * 
 * <p>This annotation serves as a specialization of {@link Component} to indicate pages.
 * Classes annotated with this annotation are {@link Component}s
 * with prototype scope.
 *
 * @author Pavel Muller
 * @version $Revision: 102464 $
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component("prototype")
public @interface Page {

}
