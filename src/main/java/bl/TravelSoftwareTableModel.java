/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import api_call.OpenWeatherCall;
import api_response.OpenWeatherData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import org.jdom2.JDOMException;

/**
 *
 * @author elisc
 */
public class TravelSoftwareTableModel extends AbstractTableModel {

    private ArrayList<OpenWeatherData> data = new ArrayList<>();
    private String[] COLUMNS = {"Icon", "Destination", "Temp", "Min Temp", "Max Temp", "Humidity", "Pressure"};

    public void add(OpenWeatherData weather) {
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
                OpenWeatherCall call = new OpenWeatherCall();
                data.add(call.getWeatherForecastByCity(destination));
                fireTableRowsInserted(data.size() - 1, data.size() - 1);
            }
        } catch (JDOMException ex) {
            Logger.getLogger(TravelSoftwareTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TravelSoftwareTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
