/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *
 * Created on 5/18/2010 1:23 AM by Adrian Wijasa.
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

/**
 * This exception is thrown if a CSV Column is not configured to go into any DB Column
 *
 * @author awijasa
 */
public class ColumnNotConfiguredException extends Exception {

    public ColumnNotConfiguredException( Main main, String column ) {
        super( column + " has not been configured to go to any " + main.dbType + " column yet." );
    }
}
