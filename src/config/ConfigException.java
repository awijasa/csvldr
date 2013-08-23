/*
 * ConfigException.java
 *
 * Created on Aug 7, 2013, courtesy of Bryan Pauquette
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

package config;

/**
 * Exception Class made specifically for DatabasePanel
 *
 * @author brpa
 */
public class ConfigException extends java.lang.RuntimeException {
    
    /**
     * Creates a new instance of <code>ConfigException</code> without detail message.
     */
    public ConfigException(String message) {
        super(message);
    }
}
