/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import api_call.OpenWeatherCall;
import api_current_weather.CurrentWeather;
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

    private ArrayList<CurrentWeather> data = new ArrayList<>();
    private String[] COLUMNS = {"Icon", "Destination", "Temp", "Min Temp", "Max Temp", "Humidity", "Pressure"};

    public void add(CurrentWeather weather) {
        data.add(weather);
        save();
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }

    public void remove(int[] indices) {
        for (int i = indices.length - 1; i >= 0; i--) {
            data.remove(indices[i]);
            save();
            fireTableRowsDeleted(indices[i], indices[i]);
        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex);
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    private void save() {
        try {
            DestinationXML.getInstance().saveDestinations(data);
        } catch (JDOMException ex) {
            Logger.getLogger(TravelSoftwareTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TravelSoftwareTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void load() {
        try {
            ArrayList<String> destinations = DestinationXML.getInstance().loadDestinations();
            for (String destination : destinations) {
                String[] parts = destination.split(",");
                String city = parts[0];
                String country = parts[1];
                data.add(OpenWeatherCall.getInstance().getCurrentWeatherByCityAndCountry(city, country));
                fireTableRowsInserted(data.size() - 1, data.size() - 1);
            }
        } catch (JDOMException ex) {
            Logger.getLogger(TravelSoftwareTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TravelSoftwareTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<CurrentWeather> getWeatherData() {
        return data;
    }

    public void clearTable() {
        data.clear();
        fireTableDataChanged();
    }

    public void sortColumn(String column) {
        switch (column) {
            case "Destination":
                data.sort(new SortByDestination());
                break;
            case "Temp":
                data.sort(new SortByTemp());
                break;
            case "Min Temp":
                data.sort(new SortByMinTemp());
                break;
            case "Max Temp":
                data.sort(new SortByMaxTemp());
                break;
            case "Humidity":
                data.sort(new SortByHumidity());
                break;
            case "Pressure":
                data.sort(new SortByPressure());
                break;
        }

        fireTableDataChanged();
    }

}
