/*
 * $Id: TableRow.java 102464 2013-08-21 15:35:16Z nahlikm1 $
 * 
 * Copyright (c) 2010 AspectWorks, spol. s r.o.
 */
package com.pageobject;

import java.util.ArrayList;
import java.util.List;

/**
 * Contents of one row in HTML table.
 * Cell indexes start from 1.
 *
 * @author Pavel Muller
 * @version $Revision: 102464 $
 */
public class TableRow {
	
	private List<String> cells = new ArrayList<String>();
	
	/**
	 * Add new cell.
	 * @param index cell number starting from 1
	 * @param cellContent
	 */
	public void addCell(int index, String cellContent) {
		cells.add(index-1, cellContent);
	}
	
	/**
	 * Returns cell contents.
	 * @param index cell number starting from 1
	 * @return cell contents
	 */
	public String getCell(int index) {
		return cells.get(index-1);
	}
	
	/**
	 * Returns number of cells in a row.
	 * @return cell count
	 */
	public int getCellCount() {
		return cells.size();
	}
	
	@Override
	public String toString() {
		return cells.toString();
	}

}
