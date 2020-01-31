/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sort;

import api_current_weather.CurrentWeather;
import java.util.Comparator;

/**
 *
 * @author elisc
 */
public class SortByHumidity implements Comparator<CurrentWeather>{

    @Override
    public int compare(CurrentWeather w1, CurrentWeather w2) {
        if(w1.getMain().getHumidity() < w2.getMain().getHumidity()){
            return 1;
        }
        else if(w1.getMain().getHumidity() > w2.getMain().getHumidity()){
            return -1;
        }
        return 0;
    }
    
}
