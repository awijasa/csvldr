/*
 * PlanWriter.java
 *
 * --- Last Update: 1/30/2010 12:18 PM ---
 *
 * Update Notes 1/30/2010 12:18 PM by Adrian Wijasa:
 * Renamed from InsertPlanWriter to PlanWriter.
 * Renamed getInsertPlan to getPlan.
 *
 * Created on April 13, 2007, 2:52 PM
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
import java.sql.SQLException;

/**
 *
 * @author awijasa
 */
public class PlanWriter {
    
    /** Creates a new instance of PlanWriter */
    public PlanWriter( Main main ) throws IOException, SQLException {
        this.main = main;
        write();
    }
    
    private void write() throws IOException, SQLException {
        PrintWriter pWriter = new PrintWriter( new FileWriter( main.fileChooser.getFile() ) );
        pWriter.println( main.csvPanel.currentImage.plan.getPlan() );
        pWriter.close();
    }
    
    private Main main;
}
