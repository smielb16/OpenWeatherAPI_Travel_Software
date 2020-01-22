/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import api_call.OpenWeatherCall;
import api_response.OpenWeatherData;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
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
        OpenWeatherData weather = (OpenWeatherData) value;
        
        switch (table.convertColumnIndexToModel(column)) {
            case 0:
                Image img = new OpenWeatherCall()
                        .getWeatherIcon(weather.getWeather().get(0).getIcon());
                label.setIcon(getScaledImageIcon(img, 35, 35));
                break;
            case 1:
                label.setText(weather.getName());
                break;
            case 2:
                label.setText(String
                        .format("%.2f °C", weather.getMain().getTemp() - 273.15));
                break;
            case 3:
                label.setText(String
                        .format("%.2f °C", weather.getMain().getTemp_min() - 273.15));
                break;
            case 4:
                label.setText(String
                        .format("%.2f °C", weather.getMain().getTemp_max() - 273.15));
                break;
            case 5:
                label.setText(weather.getMain().getHumidity() + " %");
                break;
            case 6:
                label.setText(weather.getMain().getPressure() + " Pa");
                break;
        }

        label.setForeground(Color.black);
        if (isSelected) {
            label.setBackground(Color.lightGray);
        }
        label.setOpaque(true);
        
        label.setHorizontalAlignment(SwingConstants.CENTER);

        return label;
    }
    
    public ImageIcon getScaledImageIcon(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return new ImageIcon(resizedImg);
    }

}
