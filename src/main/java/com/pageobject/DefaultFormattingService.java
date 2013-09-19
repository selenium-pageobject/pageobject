/*
 * $Id: DefaultFormattingService.java 102464 2013-08-21 15:35:16Z nahlikm1 $
 * 
 * Copyright (c) 2010 AspectWorks, spol. s r.o.
 */
package com.pageobject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Default formatting service using Czech date and number formats.
 * 
 * <p>Date format: dd.MM.yyyy and number format using <code>1 234,56</code> number format.
 *
 * @author Pavel Muller
 * @version $Revision: 102464 $
 */
public class DefaultFormattingService implements FormattingService {
	private static final String DEFAULT_NUMBER_FORMAT = ".00";
	private static final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";

	/**
	 * @see cz.cmhb.olin.selenium.FormattingService#formatDate(java.util.Date)
	 */
	public String formatDate(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(getDateFormat());
		return dateFormat.format(date);
	}
	
	/**
	 * @see cz.cmhb.olin.selenium.FormattingService#parseDate(String)
	 */
	public Date parseDate(String str) {
		if (str == null) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(getDateFormat());
		try {
			return dateFormat.parse(str);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Error parsing date: " + str);
		}
	}

	/**
	 * @see cz.cmhb.olin.selenium.FormattingService#formatNumber(Number)
	 */
	public String formatNumber(Number number) {
		if (number == null) {
			return null;
		}
		DecimalFormat numberFormat = new DecimalFormat(getNumberFormat());
		DecimalFormatSymbols decimalFormatSymbols = numberFormat.getDecimalFormatSymbols();
		decimalFormatSymbols.setDecimalSeparator(getDecimalSeparator());
		numberFormat.setDecimalFormatSymbols(decimalFormatSymbols);
		return numberFormat.format(number);
	}
	
	/**
	 * Returns decimal separator. This implementation returns comma.
	 * @return decimal separator
	 */
	protected char getDecimalSeparator() {
		return ',';	
	}

	/**
	 * Returns static date format.
	 * This implementation returns dd.MM.yyyy.
	 * @return date format
	 */
	protected String getDateFormat() {
		return DEFAULT_DATE_FORMAT;
	}
	
	/**
	 * Returns static number format.
	 * This implementation returns <i>.00</i>.
	 * @return number format
	 */
	protected String getNumberFormat() {
		return DEFAULT_NUMBER_FORMAT;
	}

}
