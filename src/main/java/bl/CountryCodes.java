/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elisc
 */
public class CountryCodes {

    private String countrycodepath = "resources/countries.csv";
    private HashMap<String, String> codemap = new HashMap<>();
    private HashMap<String, String> countrymap = new HashMap<>();
    private static CountryCodes instance;
    
    private CountryCodes(){
        loadCountries();
    }
    
    public static synchronized CountryCodes getInstance(){
        if(instance == null){
            instance = new CountryCodes();
        }
        return instance;
    }
    
    /**
     * loads all countries into map
     */
    public void loadCountries() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(new File(countrycodepath)));
            String line;
            
            while ((line = bf.readLine()) != null) {
                String[] parts = line.split(",");
                countrymap.put(parts[0], parts[1]);
                codemap.put(parts[1], parts[0]);
            }
        } catch (IOException ex) {
            Logger.getLogger(CountryCodes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * returns value from both maps for given key
     * @param key
     * @return 
     */
    public String getValue(String key){
        if(codemap.containsKey(key)){
            return codemap.get(key);
        }
        return countrymap.get(key);
    }
    
    /**
     * returns all countries
     * @return 
     */
    public Set<String> getCountries(){
        return countrymap.keySet();
    }

}
