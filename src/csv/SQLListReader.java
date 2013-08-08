/*
 * SQLListReader.java
 *
 * --- Last Update: 5/18/2010 9:57 AM ---
 *
 * Update Notes 5/18/2010 9:57 AM by Adrian Wijasa:
 * This class can now work with MySQL DB, which does not have any schema.
 *
 * Created on April 2, 2007, 2:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *
 * CSV Loader
 * Copyright 2007, 2009, 2010 Adrian Wijasa
 *
 * This file is part of CSV Loader.
 *
 * CSV Loader is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CSV Loader is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CSV Loader.  If not, see <http://www.gnu.org/licenses/>.
 */

package csv;

/**
 *
 * @author awijasa
 */
public class SQLListReader {
    
    /** Creates a new instance of SQLListReader */
    public SQLListReader( String sqlListElement ) {
        this.sqlListElement = sqlListElement;
        read();
    }
    
    String getColumn() {
        return splittedElement[splittedElement.length - 1];
    }
    
    String getSchema() {
        if( splittedElement.length == 3 )
            return splittedElement[0];
        else
            return null;
    }
    
    String getTable() {
        return splittedElement[splittedElement.length - 2];
    }
    
    private void read() {
        splittedElement = sqlListElement.split( " > " );
    }
    
    String[] splittedElement;
    private String sqlListElement;
}
