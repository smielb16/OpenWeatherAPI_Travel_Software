/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import api_current_weather.CurrentWeather;
import api_current_weather.Sys;
import api_forecast.City;
import api_forecast.ForecastElement;
import api_forecast.WeatherForecast;
import com.google.gson.Gson;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author elisc
 */
public class ForecastParser {

    /**
     * returns CurrentWeather-object for easy implementation in table model
     * @param forecast
     * @param date
     * @return 
     */
    public CurrentWeather parseForecastToCurrentWeather(WeatherForecast forecast, LocalDate date) {
        ArrayList<ForecastElement> list = forecast.getList();
        Gson gson = new Gson();
        CurrentWeather weather = null;

        for (ForecastElement elem : list) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateStr = elem.getDt_txt().split(" ")[0];
            LocalDate fDate = LocalDate.parse(dateStr, dtf);
            
            if (fDate.equals(date)) {
                weather = convertForecast(elem, forecast.getCity());
            }
        }
        return weather;
    }

    /**
     * parses the forecast values into a CurrentWeather-object
     * @param elem
     * @param city
     * @return 
     */
    private CurrentWeather convertForecast(ForecastElement elem, City city) {
        CurrentWeather weather = new CurrentWeather();
        weather.setMain(elem.getMain());
        Sys sys = elem.getSys();
        sys.setCountry(city.getCountry());
        weather.setSys(sys);
        weather.setWeather(elem.getWeather());
        weather.setWind(elem.getWind());
        weather.setCoord(city.getCoord());
        weather.setName(city.getName());
        return weather;
    }

}
