/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.browser.detail.attributes;

import org.gjt.jclasslib.structures.*;
import org.gjt.jclasslib.browser.detail.ListDetailPane.ColumnCache;

import javax.swing.table.*;

/**
    Base class for all table models for attributes diaplayed by a
    <tt>AbstractAttributeListDetailPane</tt>.
    
    @author <a href="mailto:jclasslib@gmx.net">Ingo Kegel</a>
    @version $Revision: 1.1.1.1 $ $Date: 2001-05-14 16:49:24 $
*/
public abstract class AbstractAttributeTableModel extends AbstractTableModel {

    /** Number of default columns */
    protected static final int BASE_COLUMN_COUNT = 1;

    /** The associated attribute */
    protected AttributeInfo attribute;
    
    private static String[] rowNumberStrings = new String[0];
    
    private ColumnCache columnCache;
    private TableColumnModel tableColumnModel;

    public AbstractAttributeTableModel(AttributeInfo attribute) {
        this.attribute = attribute;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public String getColumnName(int column) {
        if (column == 0) {
            return "Nr.";
        } else {
            return doGetColumnName(column);
        }
    }

    public Class getColumnClass(int column) {
        if (column == 0) {
            return Number.class;
        } else {
            return doGetColumnClass(column);
        }
    }
    
    public Object getValueAt(int row, int column) {

        if  (column == 0) {
            return rowNumberAsString(row);
        } else {
            if (columnCache == null) {
                columnCache = new ColumnCache(getRowCount(), getColumnCount() - 1);
            }
            Object value = columnCache.getValueAt(row, column - 1);
            if (value == null) {
                value = doGetValueAt(row, column);
                columnCache.setValueAt(row, column - 1, value);
            }
                
            return value;
        }
    }
    
    /**
        Get the associated table column model.
        @return the model
     */
    public TableColumnModel getTableColumnModel() {
        return tableColumnModel;
    }

    /**
        Set the associated table column model.
        @param tableColumnModel the model
     */
    public void setTableColumnModel(TableColumnModel tableColumnModel) {
        this.tableColumnModel = tableColumnModel;
    }
    
    /**
        Get the width of a specified column in pixels.
        @param column the index ofthe column in the table model
        @return the width
     */
    public abstract int getColumnWidth(int column);

    /**
        Attribute specific <tt>getValueAt()</tt>.
        @param row the row number
        @param column the column number
        @return the value
     */
    protected abstract Object doGetValueAt(int row, int column);

    /**
        Attribute specific <tt>getColumnName()</tt>.
        @param column the column number
        @return the name
     */
    protected abstract String doGetColumnName(int column);

    /**
        Attribute specific <tt>getColumnClass()</tt>.
        @param column the column number
        @return the class
     */
    protected abstract Class doGetColumnClass(int column);


    /**
        Link to the destination described by the target of the hyperlink
        contained in a specific cell.
        @param row the row number of the hyperlink
        @param column the column number of the hyperlink
     */
    public void link(int row, int column) {
    }

    /**
        Initialize the cache for row number strings. Has to be called from
        constructors of subclasses.
     */
    protected void initRowNumberStrings() {
        
        int maxRowNumber = getRowCount();
        
        if (rowNumberStrings.length - 1 < maxRowNumber) {
            String[] newRowNumberStrings = new String[maxRowNumber];
            System.arraycopy(rowNumberStrings,
                             0,
                             newRowNumberStrings,
                             0,
                             rowNumberStrings.length);
            
            for (int i = rowNumberStrings.length; i < newRowNumberStrings.length; i++) {
                newRowNumberStrings[i] = String.valueOf(i);
            }
            
            rowNumberStrings = newRowNumberStrings;
        }
    }
    
    private String rowNumberAsString(int row) {
        if (rowNumberStrings == null || row >= rowNumberStrings.length) {
            return "null";
        }
        return rowNumberStrings[row];
    }

}