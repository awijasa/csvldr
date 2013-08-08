/*
 * SQLWriter.java
 *
 * --- Last Update: 6/20/2010 6:30 PM ---
 *
 * Update Notes 6/20/2010 6:30 PM by Adrian Wijasa:
 * Added a logic to add the SQL script header for PostgreSQL.
 *
 * Update Notes 6/17/2010 9:08 PM by Adrian Wijasa:
 * Added a logic to add the SQL script header for MySQL.
 *
 * Update Notes 6/6/2010 11:05 AM by Adrian Wijasa:
 * Now works with more than three table levels.
 *
 * Update Notes 5/18/2010 1:34 AM by Adrian Wijasa:
 * Now also throws ColumnNotConfiguredException.
 *
 * Update Notes 4/23/2010 12:13 PM by Adrian Wijasa:
 * Write the number of SQL Statements that have been written so user can see the progress.
 *
 * Update Notes 1/31/2010 9:30 PM by Adrian Wijasa:
 * Added a task parameter into this Java class.  Possible values: INSERT, MERGE.
 * INSERT: Write SQL Insert files.
 * MERGE: Write SQL Merge files.
 *
 * Update Notes 1/30/2010 4:33 PM by Adrian Wijasa:
 * Renamed getStatement() to getStatements().
 * Renamed InsertStatements to SQLStatements.
 *
 * Created on April 2, 2007, 4:51 PM
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
import java.sql.SQLException;
import java.util.LinkedList;
import sql.SQLScript;
import sql.SQLStatements;
import sql.TableNotFoundException;

/**
 *
 * @author awijasa
 */
public class SQLWriter {
    
    /** Creates a new instance of SQLWriter */
    public SQLWriter( Main main, int task ) throws ClassNotFoundException, ColumnNotConfiguredException, IOException, SQLException, TableNotFoundException {
        this.main = main;
        this.task = task;
        sqlFile = main.fileChooser.getFile();
        write();
    }
    
    private void write() throws ClassNotFoundException, ColumnNotConfiguredException, IOException, SQLException, TableNotFoundException {
        pWriter = new PrintWriter( new FileWriter( sqlFile ) );
        char cr = 13;
        char lf = 10;

        SQLScript sqlScript;

        if( task == INSERT )
            sqlScript = main.csvPanel.currentImage.getInsertScript();
        else
            sqlScript = main.csvPanel.currentImage.getMergeScript();

        LinkedList<SQLStatements> script = sqlScript.script;

        if( main.dbType.equals( "MySQL" ) )
            pWriter.println( "set sql_mode = 'STRICT_ALL_TABLES';" + cr + lf );
        
        if( main.dbType.equals( "Oracle" ) )
            pWriter.println( "whenever sqlerror exit sql.sqlcode rollback" + cr + lf );
        else
            pWriter.println( "start transaction;" + cr + lf );

        System.out.println( "There are SQL statements to be written for " + script.size() + " tables." );
        
        for( int i = 0; i < script.size(); i++ ) {
            script.get( i ).writeStatements( pWriter );
            System.out.println( i + 1 + " out of " + script.size() + " tables have had their SQL statements written." );
        }

        pWriter.close();
    }
    
    private File sqlFile;
    public static final int INSERT = 1; // The task value that is used to make SQLWriter produce an Insert Script.
    public static final int MERGE = 2;  // The task value that is used to make SQLWriter produce a Merge Script.
    private int task;
    private Main main;
    private PrintWriter pWriter;
}
