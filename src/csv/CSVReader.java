/*
 * CSVReader.java
 *
 * Created on March 14, 2007, 3:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *
 * --- Last Update: 2/18/2009 5:54 PM ---
 *
 * Update Notes 2/18/2009 5:54 PM:
 * Added '.replaceAll( "\"", "" )' into 'ArrayList.add( cols[i].replaceAll( "<two-double-quotes>", "\"" ) );'.
 * This is to make sure that there will be no double quote (") enclosure added into CSV Panel.
 *
 * Update Notes 2/18/2009 5:44 PM:
 * Added '&& cols[i].indexOf( "\"" ) == cols[i].lastIndexOf( "\"" )' into 'if( cols[i].indexOf( "\"" ) != -1 ) in digestLine()'.
 * A CSV column will also be enclosed by double quotes (") if its content contains at least a double quote ("), not only comma (,).  Therefore,
 * the program needs to check the reason for the addition of double quote enclosure.  If it is caused by a double quote in a content, there will
 * be no need to append the col[++1] into appendedCol variable.
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Read a CSV File, display the data in CSVPanel's JTable, and save the data in CSVPanelImage of CSVPanel
 *
 * @author awijasa
 */
public class CSVReader {
    
    /** Creates a new instance of CSVReader */
    public CSVReader(
        Main main
        , boolean colTitleSource // whether or not to read the first line of CSV as column titles
    ) throws FileNotFoundException, IOException {
        csvFile = main.fileChooser.fileChooser.getSelectedFile();
        this.main = main;
        this.colTitleSource = colTitleSource;
        rowArrayList = new ArrayList<ArrayList<String>>();
        titleArrayList = new ArrayList<String>();
        read();
    }
    
    /* Read each line of CSV File */
    private void digestLine(
        ArrayList<String> ArrayList // A ArrayList to store the columns extracted from a CSV File line
    ) {
        
        /*
            Differenciate between "" and ". In a CSV File, "" represents a
            single ", while " is an enclosing character of a String that
            contains a comma character.
         */
        line = line.replaceAll( "\"\"", "<two-double-quotes>" );
        cols = line.split( ",", -1 );

        for( int i = 0; i < cols.length; i++ ) {
            
            /*
                When a single double-quote is found, append with the next
                columns continuously until a closing single double-quote is
                found
             */
            try {
            if( cols[i].indexOf( "\"" ) != -1 && cols[i].indexOf( "\"" ) == cols[i].lastIndexOf( "\"" ) ) {
                appendedCol = cols[i].replace( "\"", "" );

                do {
                    appendedCol += "," + cols[++i].replace( "\"", "" );
                }
                while( cols[i].indexOf( "\"" ) == -1 );

                ArrayList.add( appendedCol.replaceAll( "<two-double-quotes>", "\"" ) ); // Replace <two-double-quotes> with a single double-quote (")
            }
            else
                ArrayList.add( cols[i].replaceAll( "\"", "" ).replaceAll( "<two-double-quotes>", "\"" ) ); // Replace <two-double-quotes> with a single double-quote (")
            }
            catch( ArrayIndexOutOfBoundsException e ) {
                System.out.println( line );
                System.out.println( cols[i] );
            }
        }
    }
    
    /* Read File */
    private void read() throws FileNotFoundException, IOException {
        BufferedReader bReader = new BufferedReader( new FileReader( csvFile ) );
        ArrayList<String> colArrayList = new ArrayList<String>();
        
        line = bReader.readLine();
        
        if( colTitleSource == CSV )
            digestLine( titleArrayList ); // Get the title from a CSV File (first line)
        else {
            digestLine( colArrayList ); // Treat the first line of CSV as the first line of data
            
            for( int i = 0; i < colArrayList.size(); i++ ) // Empty Title
                titleArrayList.add( null );
        }
        
        line = bReader.readLine();
        
        /* Read through each line of CSV File */
        while( true ) {
            if( line == null )
                break;
            
            colArrayList = new ArrayList<String>();
            digestLine( colArrayList );
            rowArrayList.add( colArrayList );
            
            line = bReader.readLine();
        }
        
        bReader.close();
        
        /* Send the data back to CSVPanel */
        main.csvPanel.setCSVData( rowArrayList, titleArrayList );
    }
    
    private boolean colTitleSource;
    private File csvFile;
    private Main main;
    private String appendedCol;
    private String cols[];
    private String line;
    private ArrayList<ArrayList<String>> rowArrayList;
    private ArrayList<String> titleArrayList;
    public static final boolean CSV = false;
    public static final boolean MANUAL = true;
}
