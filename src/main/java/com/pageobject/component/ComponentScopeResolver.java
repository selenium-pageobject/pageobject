/*
 * $Id: ComponentScopeResolver.java 102464 2013-08-21 15:35:16Z nahlikm1 $
 * 
 * Copyright (c) 2010 AspectWorks, spol. s r.o.
 */
package com.pageobject.component;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopeMetadataResolver;

import java.util.Map;

/**
 * Resolves bean scope according to {@link Component#value()}.
 *
 * @author Pavel Muller
 * @version $Revision: 102464 $
 */
public class ComponentScopeResolver implements ScopeMetadataResolver {

	/**
	 * @see org.springframework.context.annotation.ScopeMetadataResolver#resolveScopeMetadata(org.springframework.beans.factory.config.BeanDefinition)
	 */
	public ScopeMetadata resolveScopeMetadata(BeanDefinition definition) {
		ScopeMetadata metadata = new ScopeMetadata();
		if (definition instanceof AnnotatedBeanDefinition) {
			AnnotatedBeanDefinition annDef = (AnnotatedBeanDefinition) definition;
			Map<String, Object> attributes =
					annDef.getMetadata().getAnnotationAttributes(Component.class.getName());
			if (attributes != null) {
				metadata.setScopeName((String) attributes.get("value"));
			}
		}
		return metadata;
	}

}
