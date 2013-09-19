/*
 * $Id: TableControl.java 103519 2013-09-19 15:17:48Z nahlikm1 $
 * 
 * Copyright (c) 2010 AspectWorks, spol. s r.o.
 */
package com.pageobject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.pageobject.component.AbstractComponent;
import com.pageobject.component.Component;

/**
 * Control for simple manipulation with HTML table.
 * 
 * @author Pavel Muller
 * @version $Revision: 103519 $
 */
@Component("prototype")
public class TableControl extends AbstractComponent {
	private String previousPageButton = "xpath=//a/img[contains(@src,'prev-page.gif')]";
	private String nextPageButton = "xpath=//a/img[contains(@src,'next-page.gif')]";
	private String firstPageButton = "xpath=//a/img[contains(@src,'first-page.gif')]";
	private String lastPageButton = "xpath=//a/img[contains(@src,'last-page.gif')]";

	private String tableLocator;
	private String selectedRowAttributeName;
	private String selectedRowAttributeValue;
	
	/**
	 * Configures location of a table within a page.
	 * Set this property before use.
	 * @param tableLocator an element locator
	 */
	public void setTableLocator(String tableLocator) {
		this.tableLocator = tableLocator;
	}
	
	/**
	 * Returns a table locator on a page.
	 * @return table XPath locator
	 */
	protected String getTableLocator() {
		if (!StringUtils.hasText(tableLocator)) {
			return "//table";
		}
		return tableLocator;
	}

	/**
	 * Get the previous page button locator.
	 * 
	 * @return
	 */
	public String getPreviousPageButton() {
		return previousPageButton;
	}

	/**
	 * Set the previous page button locator.
	 * 
	 * @param previousPageButton
	 */
	public void setPreviousPageButton(String previousPageButton) {
		this.previousPageButton = previousPageButton;
	}

	/**
	 * Get the next page button locator.
	 * 
	 * @return
	 */
	public String getNextPageButton() {
		return nextPageButton;
	}

	/**
	 * Set the next page button locator.
	 * 
	 * @param nextPageButton
	 */
	public void setNextPageButton(String nextPageButton) {
		this.nextPageButton = nextPageButton;
	}

	/**
	 * Get the first page button locator.
	 * 
	 * @return
	 */
	public String getFirstPageButton() {
		return firstPageButton;
	}

	/**
	 * Set the first page button locator.
	 * 
	 * @param firstPageButton
	 */
	public void setFirstPageButton(String firstPageButton) {
		this.firstPageButton = firstPageButton;
	}

	/**
	 * Get the last page button locator.
	 * 
	 * @return
	 */
	public String getLastPageButton() {
		return lastPageButton;
	}

	/**
	 * Set the last page button locator.
	 * 
	 * @param lastPageButton
	 */
	public void setLastPageButton(String lastPageButton) {
		this.lastPageButton = lastPageButton;
	}

	/**
	 * Set attribute name to recognize selected row.
	 * Applies if the table supports row selection.
	 * @param selectedRowAttributeName name of a <code>tr</code> attribute to inspect
	 */
	public void setSelectedRowAttributeName(String selectedRowAttributeName) {
		this.selectedRowAttributeName = selectedRowAttributeName;
	}
	
	/**
	 * Returns attribute name to inspect to recognize if a table row is selected.
	 * @return the selectedRowAttributeName
	 */
	protected String getSelectedRowAttributeName() {
		return selectedRowAttributeName;
	}
	
	/**
	 * Set attribute value to recognize selected row.
	 * It can be only part of the value such as an HTML class.
	 * Applies if the table supports row selection.
	 * @param selectedRowAttributeValue attribute value indicating selected row
	 */
	public void setSelectedRowAttributeValue(String selectedRowAttributeValue) {
		this.selectedRowAttributeValue = selectedRowAttributeValue;
	}
	
	/**
	 * Returns attribute value to recognize if the row is selected.
	 * @return the selectedRowAttributeValue
	 */
	protected String getSelectedRowAttributeValue() {
		return selectedRowAttributeValue;
	}
	
	/**
	 * Returns a row locator in a table. Default is <code>/tbody/tr</code>.
	 * @return row XPath locator
	 */
	protected String getRowLocator() {
		return "/tbody/tr";
	}

	/**
	 * Returns a cell locator in a row. Default is <code>/td</code>.
	 * @return cell XPath locator
	 */
	protected String getCellLocator() {
		return "/td";
	}
	
	/**
	 * Returns a header locator in a table. Default is <code>/thead/tr</code>.
	 * @return header row XPath locator
	 */
	protected String getHeaderLocator() {
		return "/thead/tr";
	}
	
	/**
	 * Returns a header cell locator in a row. Default is <code>/th</code>.
	 * @return header cell XPath locator
	 */
	protected String getHeaderCellLocator() {
		return "/th";
	}
	
	/**
	 * Finds a column based on a header cell text, returning number of the
	 * column or <code>null</code> if none was found.
	 * 
	 * @param headerCellText
	 * @return column number starting from 1 or <code>null</code> if not found
	 */
	public Integer findColumn(String headerCellText) {
		String headerCellLocator = tableLocator + getHeaderLocator() + getHeaderCellLocator();
		int columnNumber = 1;
		
		while(isElementPresent(headerCellLocator + "[" + columnNumber + "]")) {
			String thText = getText(headerCellLocator + "[" + columnNumber + "]");
			if(thText.contains(headerCellText)) {
				return columnNumber;
			}
			
			columnNumber++;
		}
		
		return null;
	}
	
	/**
	 * Returns whether a row with a given row number is selected or not.
	 * Set {@link #setSelectedRowAttributeName(String)} and {@link #setSelectedRowAttributeValue(String)}
	 * if your table supports row selection.
	 * @param rowNumber row to check, starting from 1
	 * @return whether the row is selected or not
	 * @throws UnsupportedOperationException is the table does not support row selection
	 */
	public boolean isRowSelected(int rowNumber) {
		String rowAttrName = getSelectedRowAttributeName();
		String rowAttrValue = getSelectedRowAttributeValue();
		if (rowAttrName == null || rowAttrValue == null) {
			throw new UnsupportedOperationException("Table does not support row selection. " +
					"Configure attribute name and value to check selected rows.");
		}
		
		String rowLocator = getTableLocator() + getRowLocator() + "[" + rowNumber + "]";
		String attributeValue;
		try {
			attributeValue = browser.getElementAttribute("xpath=" + rowLocator, rowAttrName);
		} catch (Exception e) {
			return false;
		}
		return attributeValue != null && attributeValue.contains(rowAttrValue);
	}

	/**
	 * Returns number of rows in the table including all pages.
	 * @return row count, 0 for empty table
	 */
	public int getRowCount() {
		int rowCount = getRowCountOnPage();
		while (isElementPresent(nextPageButton)) {
			click(nextPageButton);
			rowCount += getRowCountOnPage();
		}
		
		return rowCount;
	}
	
	/**
	 * @return
	 */
	private int getRowCountOnPage() {
		String rowInTable = getTableLocator() + getRowLocator();
		return browser.getElementCount("xpath=" + rowInTable);
	}
	

	/**
	 * @param entity
	 * @return
	 */
	public Integer findRow(TableEntity entity) {
		return findRow(entity.getSearchAttributes());
	}

	/**
	 * Finds a row in a table according to a given cell value to search. 
	 * Returns <code>null</code> if row not found.
	 * @param cellValue cell value to search. Not only exact cell content but substrings too.
	 * @return row number starting from 1 or <code>null</code> if not found
	 */
	public Integer findRow(String cellValue) {
		return findRow(new String[] {cellValue});
	}
	
	/**
	 * Finds a row in a table according to given cell values to search. 
	 * Use more cell values if the entity in the table does not have one unique key. 
	 * Returns <code>null</code> if row not found.
	 * @param cellValue cell value to search. Not only exact cell content but substrings too.
	 * @return row number starting from 1 or <code>null</code> if not found
	 */
	public Integer findRow(String[] cellValues) {
		Integer rowNumber = findRowOnPage(cellValues);
		if (rowNumber != null) {
			return rowNumber;
		}
		
		while (isElementPresent(nextPageButton)) {
			click(nextPageButton);
			rowNumber = findRowOnPage(cellValues);
			if (rowNumber != null) {
				return rowNumber;
			}
		}
		
		return null;
	}
	
	private Integer findRowOnPage(String[] cellValues) {
		String rowLocator = getTableLocator() + getRowLocator(); 

		int rowNumber = 1;
		while (isElementPresent("xpath=" + rowLocator + "[" + rowNumber + "]")) {
			String rowContent = browser.getElementValue("xpath=" + rowLocator + "[" + rowNumber + "]");
			boolean result = true;
			for (String cell : cellValues) {
				if (rowContent.indexOf(cell) == -1) {
					result = false;
				}
			}
			if (result == true) {
				return rowNumber;
			}
			rowNumber++;
		}
		
		return null;
	}

	/**
	 * @param entity
	 * @return
	 */
	public TableRow getRow(TableEntity entity) {
		return getRow(entity.getSearchAttributes());
	}

	/**
	 * Returns a row content in a table according to given cell value to search. 
	 * Returns <code>null</code> if row not found.
	 * @param cellValues cell values to search in one row. Not only exact cell contents but substrings too.
	 * @return row contents or <code>null</code> if not found
	 */
	public TableRow getRow(String cellValue) {
		return getRow(new String[] {cellValue});
	}

	/**
	 * Returns a row content in a table according to given cell values to search. 
	 * Use more cell values if the entity in the table does not have one unique key. 
	 * Returns <code>null</code> if row not found.
	 * @param cellValues cell values to search in one row. Not only exact cell contents but substrings too.
	 * @return row contents or <code>null</code> if not found
	 */
	public TableRow getRow(String[] cellValues) {
		Integer rowNumber = findRow(cellValues);
		if (rowNumber == null) {
			return null;
		}
		
		return getRowOnPage(rowNumber);
	}

	/**
	 * Returns a row content of a given row.
	 * @param rowNumber number of row starting from 1
	 * @return row control
	 */
	public TableRow getRowOnPage(int rowNumber) {
		TableRow row = new TableRow();
		
		String cellLocator = getTableLocator() + getRowLocator() + "[" + rowNumber + "]" + getCellLocator();
		int cellNumber = 1;
		while (isElementPresent("xpath=" + cellLocator + "[" + cellNumber + "]")) {
			String cellContent = browser.getElementValue("xpath=" + cellLocator + "[" + cellNumber + "]");
			row.addCell(cellNumber, cellContent);
			cellNumber++;
		}
		
		return row;
	}

	/**
	 * Returns all rows in a table.
	 * @return all row controls, empty list if the table is empty
	 */
	public List<TableRow> getAllRows() {
		List<TableRow> table = new ArrayList<TableRow>();
		
		int rowCount = getRowCountOnPage();
		for (int i = rowCount; i > 0 ; i--) {
			TableRow row = getRowOnPage(i);
			table.add(0, row);
		}
		while (isElementPresent(nextPageButton)) {
			click(nextPageButton);
			rowCount = getRowCountOnPage();
			for (int i = rowCount; i > 0 ; i--) {
				TableRow row = getRowOnPage(i);
				table.add(0, row);
			}
		}
		
		return table;
	}
	
	/**
	 * Clicks on a specific row in the table. Does not handle the reloading
	 * of current page. If it should cause a page refresh, you should use 
	 * {@link TableControl#clickOnRowAndWait(int)}.
	 *  
	 * @param rowNumber - number of the row you want to click on
	 */
	public void clickOnRow(int rowNumber) {
		click("xpath=" + getTableLocator() + getRowLocator() + "[" + rowNumber + "]");
	}

	/**
	 * Display the last page of the table.
	 */
	public void gotoLastPage() {
		if (isElementPresent(lastPageButton)) {
			click(lastPageButton);
		}
	}
	
	/**
	 * Display the first page of the table.
	 */
	public void gotoFirstPage() {
		if (isElementPresent(firstPageButton)) {
			click(firstPageButton);
		}
	}
	
	/**
	 * Display the next page of the table.
	 */
	public void gotoNextPage() {
		if (isElementPresent(nextPageButton)) {
			click(nextPageButton);
		}
	}
	
	/**
	 * Display the previous page of the table.
	 */
	public void gotoPreviousPage() {
		if (isElementPresent(previousPageButton)) {
			click(previousPageButton);
		}
	}

}
