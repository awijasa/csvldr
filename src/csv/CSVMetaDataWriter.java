/*
 * CSVMetaDataWriter.java
 *
 * Created on July 17, 2009, 3:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package csv;

import forms.Main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Write a Data Type & Length Report into a file
 *
 * --- Last Update: 7/17/2009 3:22 PM ---
 *
 * @author awijasa
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
public class CSVMetaDataWriter {
    
    /** Creates a new instance of CSVMetaDataWriter */
    public CSVMetaDataWriter( Main main ) throws IOException {
        this.main = main;
        metaDataFile = main.fileChooser.getFile();
        titleArrayList = main.csvPanel.currentImage.columnArrayList;
        dataTypeArrayList = main.csvPanel.currentImage.dataTypeArrayList;
        write();
    }

    /* Write the Titles and Data Types & Length into a file */
    private void write() throws IOException {
        PrintWriter pWriter = new PrintWriter( new FileWriter( metaDataFile ) );

        for( int i = 0; i < titleArrayList.size(); i++ ) {
            pWriter.println( "\"" + titleArrayList.get( i ) + "\",\"" + dataTypeArrayList.get( i ) + "\"" );
        }

        pWriter.close();
    }
    
    private File metaDataFile;
    private Main main;
    private ArrayList<String> titleArrayList;
    private ArrayList<String> dataTypeArrayList;
}
