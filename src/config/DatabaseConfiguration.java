/*
 * DatabaseConfiguration.java
 *
 * --- Last Update: 8/7/2013 ---
 *
 * Update Notes 8/7/2013 by Bryan Pauquette:
 * Now use Databases.xml instead of tnsnames.ora
 *
 * Update Notes 5/11/2010 12:23 AM by Adrian Wijasa:
 * Now this class doesn't automatically turn the values written in TNSNAMES.ORA into uppercase.
 *
 * Created on March 6, 2007, 4:35 PM
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

/**
 * Reads Database.xml
 *
 * @author brpa
 */
public class DatabaseConfiguration {

    /**
     * Creates a new instance of DatabaseConfiguration
     */
    public DatabaseConfiguration() {
    }


    public void write() throws ConfigException {

        try {
            configurationFile = new File(CONFIGNAME);
            if (!configurationFile.exists()) {

                if (!configurationFile.createNewFile()) {
                    throw new ConfigException("Could not create " + CONFIGNAME);
                }
            }


            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(DATABASES);
            doc.appendChild(rootElement);


            for (int i = 0; i < databaseVector.size(); i++) {
                /* Read each Database Configuration */
                Vector databaseEntry = (Vector) databaseVector.get(i);
                // databases elements
                if (databaseEntry.size() >= 4) {
                    Element database = doc.createElement(DATABASE);
                    rootElement.appendChild(database);
                    String tnsName = (String) databaseEntry.get(0);    // Extract TNS Name
                    buildNode(doc, database, NAME, tnsName);
                    String host = (String) databaseEntry.get(1);       // Extract Host
                    buildNode(doc, database, HOST, host);
                    int port = (Integer) databaseEntry.get(2);         // Extract Port
                    buildNode(doc, database, PORT, new Integer(port).toString());
                    String sid = (String) databaseEntry.get(3);        // Extract SID
                    buildNode(doc, database, SID, sid);
                } else {
                    throw new ConfigException("Please enter all data for a database");
                }
            }


            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(configurationFile);

            // Output to console for testing
            StreamResult dump = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            throw new ConfigException(pce.getMessage());
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
            throw new ConfigException(tfe.getMessage());

        } catch (IOException e) {
            throw new ConfigException(e.getMessage());
        }


    }

    private void buildNode(Document doc, Element database, String nodeName, String nodeValue) {
        Element tnsNameElement = doc.createElement(nodeName);
        tnsNameElement.appendChild(doc.createTextNode(nodeValue));
        database.appendChild(tnsNameElement);
    }

    public void read() throws ConfigException {

        try {
            databaseVector = new Vector();

            if (configurationFile == null) {
                configurationFile = new File(CONFIGNAME);
            }
            if (!configurationFile.exists()||configurationFile.length()==0) {
                    // Create an empty shell so no SaxParseException
                write();

            }

            System.out.println(configurationFile.getAbsolutePath());

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(configurationFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName(DATABASE);
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) nNode;
                    if (element != null) {
                        Vector<Object> databaseEntry = new Vector<Object>();
                        if (element.getElementsByTagName(NAME).getLength() >= 1) {
                            String name = element.getElementsByTagName(NAME).item(0).getTextContent();
                            String host = element.getElementsByTagName(HOST).item(0).getTextContent();
                            Integer port = Integer.parseInt(element.getElementsByTagName(PORT).item(0).getTextContent());
                            String sid = element.getElementsByTagName(SID).item(0).getTextContent();
                            if (name != null) {
                                databaseEntry.add(name);
                                databaseEntry.add(host);
                                databaseEntry.add(port);
                                databaseEntry.add(sid);

                                databaseVector.add(databaseEntry);
                            }
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw new ConfigException("Configuration file was not found " + e.getMessage());
        } catch (SAXException e) {
            throw new ConfigException("Invalid xml in configuration file " + CONFIGNAME + e.getMessage());
        } catch (IOException e) {
            throw new ConfigException("Could not read " + configurationFile.getAbsolutePath() + e.getMessage());
        } catch (ParserConfigurationException e) {
            throw new ConfigException("Parser Configuration error" + e.getMessage());
        }
    }

    public Vector getDatabaseVector() {
        return databaseVector;
    }

    public File getConfigurationFile() {
        return configurationFile;
    }

    public void setConfigurationFile(File configurationFile) {
        this.configurationFile = configurationFile;
    }




    public void setDatabaseVector(Vector databaseVector) {
           this.databaseVector = databaseVector;
    }

    public Vector getColumnNames() {
        columnNames = new Vector<String>( 4 );
        columnNames.add( "Name" );
        columnNames.add( "Host" );
        columnNames.add( "Port" );
        columnNames.add( "SID" );
        return columnNames;
    }


    private File configurationFile;
    public final static String CONFIGNAME="Databases.xml";
    private final static String DATABASE="Database";
    private final static String DATABASES="Databases";
    private final static String NAME="Name";
    private final static String HOST="Host";
    private final static String PORT="Port";
    private final static String SID="Sid";

    private Vector columnNames;
    private Vector databaseVector;
}
