/*
 * $Id: Component.java 102464 2013-08-21 15:35:16Z nahlikm1 $
 * 
 * Copyright (c) 2010 AspectWorks, spol. s r.o.
 */
package com.pageobject.component;

import org.springframework.beans.factory.config.BeanDefinition;

import java.lang.annotation.*;

/**
 * Indicates that an annotated class is a reusable component.
 * The component will be a Spring bean and it can be autowired in automated web page unit tests.
 * 
 * <p>Use the following setup to configure Spring component scan.
 * <pre>
 * &lt;context:component-scan base-package="com.aspectworks.project" use-default-filters="false"
 * 	scope-resolver="com.aspectworks.awf.test.selenium.SeleniumComponentScopeResolver"&gt;
 *     &lt;context:include-filter type="annotation" expression="com.aspectworks.awf.test.selenium.SeleniumComponent"/&gt;
 * &lt;/context:component-scan&gt;
 * </pre>
 *
 * @see ComponentScopeResolver
 *
 * @author Pavel Muller
 * @version $Revision: 102464 $
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
	
	/**
	 * Specifies the scope to use for the annotated Spring component/bean.
	 * Default is singleton. See bean scopes in Spring docs.
	 * @return the specified scope
	 */
	public String value() default BeanDefinition.SCOPE_SINGLETON;

}
