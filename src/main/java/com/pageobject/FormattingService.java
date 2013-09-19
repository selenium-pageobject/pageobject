/*
 * $Id: FormattingService.java 102464 2013-08-21 15:35:16Z nahlikm1 $
 * 
 * Copyright (c) 2010 AspectWorks, spol. s r.o.
 */
package com.pageobject;

import java.util.Date;

/**
 * Service for formatting and type conversion.
 *
 * @author Pavel Muller
 * @version $Revision: 102464 $
 */
public interface FormattingService {
	
	/**
	 * @param date
	 * @return
	 */
	public String formatDate(Date date);

	/**
	 * @param str
	 * @return
	 */
	public Date parseDate(String str);

	/**
	 * @param number
	 * @return
	 */
	public String formatNumber(Number number);

}
