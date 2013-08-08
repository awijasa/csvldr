/*
 * CSVPanelImageWriter.java
 *
 * --- Last Update: 3/30/2010 11:00 PM ---
 *
 * Update Notes 3/30/2010 11:00 PM by Adrian Wijasa:
 * Now also saves the "Do Not Load Column" configuration into the file.
 *
 * Created on April 12, 2007, 11:11 AM
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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author awijasa
 */
public class CSVPanelImageWriter {
    
    /** Creates a new instance of CSVPanelImageWriter */
    public CSVPanelImageWriter( Main main ) throws IOException {
        this.main = main;
        write();
    }

    /* Write configurations into a file */
    private void write() throws IOException {
        PrintWriter pWriter = new PrintWriter( new FileWriter( main.fileChooser.getFile() ) );
        ArrayList<String> sqlListArrayList;
        int sqlListSize;

        /* Iterate through all CSV columns */
        for( int i = 0; i < main.csvPanel.currentImage.columnArrayList.size(); i++ ) {

            /* Get the CSV Column - SQL Column Assignments of the column */
            sqlListArrayList = main.csvPanel.currentImage.sqlArrayList.get( i );
            sqlListSize = sqlListArrayList.size();

            /*
                Get the value of the Indicator that determine if the CSV column is going to be loaded into the
                database and write it into the configuration file
             */
            if( main.csvPanel.currentImage.includeArrayList.get( i ) )
                pWriter.print( "Don't Load" );
            else
                pWriter.print( "Load" );

            /* Write the CSV Column - SQL Column Assignments of the column into the configuration file */
            if( sqlListSize > 0 ) {
                for( int j = 0; j < sqlListArrayList.size(); j++ )
                    pWriter.print( "," + sqlListArrayList.get( j ) );
            }
            
            /* Only print the next line character the program is not iterating the last column */
            if( i < main.csvPanel.currentImage.columnArrayList.size() - 1 )
                pWriter.println( "" );
        }

        pWriter.close();
    }
    
    private Main main;
}
