/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import api_call.OpenWeatherCall;
import api_current_weather.CurrentWeather;
import bl.CountryCodes;
import bl.ImageIconScalar;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author elisc
 */
public class TravelSoftwareTableCellRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel();
        CurrentWeather weather = (CurrentWeather) value;
        label.setForeground(Color.black);

        switch (table.convertColumnIndexToModel(column)) {
            case 0:
                Image img = new OpenWeatherCall()
                        .getWeatherIcon(weather.getWeather().get(0).getIcon());
                label.setIcon(new ImageIconScalar().getScaledImageIcon(img, 35, 35));
                break;
            case 1:
                label.setText(weather.getName() + ", "
                        + CountryCodes.getInstance()
                                .getValue(weather.getSys().getCountry()));
                break;
            case 2:
                double temp = weather.getMain().getTemp();
                label.setText(String
                        .format("%.2f °C", temp));
                
                if(temp <= 0.0){
                    label.setForeground(Color.blue);
                }
                else if(temp >= 30.0){
                    label.setForeground(Color.red);
                }
                
                break;
            case 3:
                double mintemp = weather.getMain().getTemp_min();
                label.setText(String
                        .format("%.2f °C", mintemp));
                
                if(mintemp <= 0.0){
                    label.setForeground(Color.blue);
                }
                else if(mintemp >= 30.0){
                    label.setForeground(Color.red);
                }
                
                break;
            case 4:
                double maxtemp = weather.getMain().getTemp_max();
                label.setText(String
                        .format("%.2f °C", maxtemp));
                
                if(maxtemp <= 0.0){
                    label.setForeground(Color.blue);
                }
                else if(maxtemp >= 25.0){
                    label.setForeground(Color.red);
                }
                
                break;
            case 5:
                label.setText(weather.getMain().getHumidity() + " %");
                break;
            case 6:
                label.setText(weather.getMain().getPressure() + " Pa");
                break;
        }


        if (isSelected) {
            label.setBackground(Color.lightGray);
        }
        label.setOpaque(true);

        label.setHorizontalAlignment(SwingConstants.CENTER);

        return label;
    }

}
