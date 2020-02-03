/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import api_call.DestinationNotFoundException;
import api_call.OpenWeatherCall;
import api_current_weather.CurrentWeather;
import gui.CompareWeatherConditionsGUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import org.jdom2.JDOMException;
import sort.SortByDestination;
import sort.SortByHumidity;
import sort.SortByMaxTemp;
import sort.SortByMinTemp;
import sort.SortByPressure;
import sort.SortByTemp;

/**
 *
 * @author elisc
 */
public class TravelSoftwareTableModel extends AbstractTableModel {

    private ArrayList<CurrentWeather> entries = new ArrayList<>();
    private String[] COLUMNS = {"Icon", "Destination", "Temp", "Min Temp", "Max Temp", "Humidity", "Pressure"};

    /**
     * adds weather entry to list and table
     * @param weather 
     */
    public void add(CurrentWeather weather) {
        entries.add(weather);
        save();
        fireTableRowsInserted(entries.size() - 1, entries.size() - 1);
    }

    /**
     * removes weather entry from list and table
     * @param indices 
     */
    public void remove(int[] indices) {
        for (int i = indices.length - 1; i >= 0; i--) {
            entries.remove(indices[i]);
            save();
            fireTableRowsDeleted(indices[i], indices[i]);
        }
    }

    @Override
    public int getRowCount() {
        return entries.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return entries.get(rowIndex);
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    /**
     * passes weather entry list in order for it to be saved to XML
     */
    private void save() {
        try {
            DestinationXML.getInstance().saveDestinations(entries);
        } catch (JDOMException ex) {
            Logger.getLogger(TravelSoftwareTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TravelSoftwareTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * loads destination names and retrieves current weather data for respective destination
     */
    public void load() throws DestinationNotFoundException {
        try {
            ArrayList<String> destinations = DestinationXML.getInstance().loadDestinations();
            for (String destination : destinations) {
                String[] parts = destination.split(",");
                String city = parts[0];
                String country = parts[1];
                entries.add(new OpenWeatherCall().getCurrentWeatherByCityAndCountry(city, country));
                fireTableRowsInserted(entries.size() - 1, entries.size() - 1);
            }
        } catch (JDOMException ex) {
            Logger.getLogger(TravelSoftwareTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TravelSoftwareTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<CurrentWeather> getWeatherData() {
        return entries;
    }

    public void clearTable() {
        entries.clear();
        fireTableDataChanged();
    }

    public void sortColumn(String column) {
        switch (column) {
            case "Destination":
                entries.sort(new SortByDestination());
                break;
            case "Temp":
                entries.sort(new SortByTemp());
                break;
            case "Min Temp":
                entries.sort(new SortByMinTemp());
                break;
            case "Max Temp":
                entries.sort(new SortByMaxTemp());
                break;
            case "Humidity":
                entries.sort(new SortByHumidity());
                break;
            case "Pressure":
                entries.sort(new SortByPressure());
                break;
        }

        fireTableDataChanged();
    }
    
    public void compareWeatherConditions(int[] indices) throws Exception{
        if(indices.length != 2){
            throw new Exception("Please select TWO destinations to compare weather conditions!");
        }
        
        CurrentWeather[] conditions = new CurrentWeather[2];
        conditions[0] = entries.get(indices[0]);
        conditions[1] = entries.get(indices[1]);
        
        new CompareWeatherConditionsGUI().showConditions(conditions);
    }

}
