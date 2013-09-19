/*
 * $Id: TableEntity.java 102464 2013-08-21 15:35:16Z nahlikm1 $
 * 
 * Copyright (c) 2009 AspectWorks, spol. s r.o.
 */
package com.pageobject;

import com.pageobject.component.AbstractTest;

/**
 * Interface for table searching. 
 * Entity must implemented this interface be able to use table search.
 * 
 * @see AbstractTest#findRow(TableEntity)
 * 
 * @author Zdenek Jonas
 * @version $Revision: 102464 $
 */
public interface TableEntity {

	/**
	 * Returns a business key for an entity.
	 * List of attributes uniquely identifying an entity in a table row.
	 * @return array of string displayed in the table.
	 */
	public String[] getSearchAttributes();

}
