/*
 * CSVWriter.java
 *
 * --- Last Update: 5/20/2010 12:56 PM ---
 *
 * Update Notes 5/20/2010 12:56 PM by Adrian Wijasa:
 * Now is able to save a CSV file from any source of AbstractList data (ArrayLists and LinkedLists).  New
 * parameters are added, so we can designate the ArrayLists/LinkedLists that contain the data and column titles,
 * instead of always saving the data and titles from the CSV Data Snapshot.
 *
 * Update Notes 4/8/2010 8:30 PM by Adrian Wijasa:
 * Added comments.
 * Condensed write() function.  The last column is now written into the file using the same for loop as the
 * other columns.
 *
 * Created on March 30, 2007, 4:24 PM
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

import forms.Main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.AbstractList;

/**
 *
 * @author awijasa
 */
public class CSVWriter {
    
    /** Creates a new instance of CSVWriter */
    public CSVWriter( Main main, boolean writeTitles, AbstractList data, AbstractList colTitles ) throws IOException {
        this.main = main;
        this.writeTitles = writeTitles;
        csvFile = main.fileChooser.getFile();
        rowList = data;
        titleList = colTitles;
        write();
    }

    /* Write the content of CSV Data Snapshot into a CSV file */
    private void write() throws IOException {
        PrintWriter pWriter = new PrintWriter( new FileWriter( csvFile ) );

        /* Write tiles to the first line of file if the user wants to */
        if( writeTitles ) {
            line = "";

            for( int i = 0; i < titleList.size() - 1; i++ )
                line += titleList.get( i ).trim() + ",";

            String lastTitle = titleList.get( titleList.size() - 1 );
            
            if( lastTitle != null )
                line += lastTitle.trim();
            else
                line += lastTitle;

            pWriter.println( line );
        }

        /* Write the content of the CSV Data Snapshot into a CSV file, column per column */
        for( int i = 0; i < rowList.size(); i++ ) {
            AbstractList<String> colList = rowList.get( i );

            String column;
            line = "";

            /* Iterate through each column */
            for( int j = 0; j < colList.size(); j++ ) {
                column = colList.get( j );
                
                if( column != null ) {
                    column = column.trim();

                    /* If the iterated item contains comma, enclose it with double quotes */
                    if( column.indexOf( "," ) != -1 )
                        column = "\"" + column + "\"";
                }
                else
                    column = "";

                line += column;

                /* If this is not the last column yet, append comma to indicate the start of the next column */
                if( j < colList.size() - 1 )
                    line += ",";

                /* Print the line to file if this is the last column of the row iterated */
                if( j == colList.size() - 1 )
                    pWriter.println( line );
            }
        }

        pWriter.close();
    }
    
    private boolean writeTitles;                            /* Whether titles should be written into file */
    private File csvFile;                                   /* The output CSV file */
    private Main main;                                      /* The Main class of CSV Loader */
    private String line;                                    /* Line String buffer */
    private AbstractList<AbstractList<String>> rowList;     /* CSV Data Snapshot or Data Comparison */
    private AbstractList<String> titleList;                 /* The titles of CSV Data Snapshot or Data Comparison */
}
