/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import api_current_weather.CurrentWeather;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author elisc
 */
public class DestinationXML {

    private static DestinationXML instance;
    private Document doc;
    private String xmlpath = "xml/destinations.xml";

    private DestinationXML() throws JDOMException, IOException {
        File f = new File(xmlpath);
        if(!f.exists()){
            f.createNewFile();
        }
        SAXBuilder builder = new SAXBuilder();
        //InputStream xmlIn = new FileInputStream(xmlpath);
        doc = builder.build(f);
    }
    
    public static synchronized DestinationXML getInstance() throws JDOMException, IOException{
        if(instance == null){
            instance = new DestinationXML();
        }
        return instance;
    }

    /**
     * saves given destination names to XML
     * @param data 
     */
    public void saveDestinations(ArrayList<CurrentWeather> data) {
        ArrayList<String> destinations = new ArrayList<>();
        for (CurrentWeather weather : data) {
            destinations.add(weather.getName() + "," + weather.getSys().getCountry());
        }
        
        Element root = new Element("destinations");
        doc.setRootElement(root);
        
        for (String destination : destinations) {
            Element destElem = new Element("destination");
            
            String[] destinationSplit = destination.split(",");
            String name = destinationSplit[0];
            String country = destinationSplit[1];
            
            destElem.addContent(new Element("name").setText(name));
            destElem.addContent(new Element("country").setText(country));
            root.addContent(destElem);
        }
        
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        try {
            xmlOutputter.output(doc, new FileOutputStream(new File(xmlpath)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DestinationXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DestinationXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * loads saved destination names from XML
     * @return 
     */
    public ArrayList<String> loadDestinations() {
        Element root = doc.getRootElement();
        ArrayList<String> destinations = new ArrayList<>();
        
        for(Element destination : root.getChildren()){
            String destinationStr = destination.getChild("name").getText() 
                    + "," + destination.getChild("country").getText();
            destinations.add(destinationStr);
        }
        
        return destinations;
    }

}
