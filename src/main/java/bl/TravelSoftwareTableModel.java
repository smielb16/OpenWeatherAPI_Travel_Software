/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import api_response.OpenWeatherData;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 *
 * @author elisc
 */
public class TravelSoftwareTableModel extends AbstractTableModel {

    private ArrayList<OpenWeatherData> data = new ArrayList<>();
    private String[] COLUMNS = {"Icon", "Destination", "Temp", "Min Temp", "Max Temp", "Humidity", "Pressure"};
    private Document doc;
    private String xmlpath = "";

    public void add(OpenWeatherData weather) {
        data.add(weather);
        //saveXML();
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }

    public void remove(int[] indices) {
        for (int i = indices.length - 1; i >= 0; i--) {
            data.remove(indices[i]);
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

    public void saveXML() {
        Element root = doc.getRootElement();
    }

}
