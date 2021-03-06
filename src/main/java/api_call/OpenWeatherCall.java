/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_call;

import api_current_weather.CurrentWeather;
import api_forecast.WeatherForecast;
import com.google.gson.Gson;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author elisc
 */
public class OpenWeatherCall {

    private static String URI = "http://api.openweathermap.org/data/2.5/";
    private static String PATH_1 = "weather";
    private static String PATH_2 = "forecast";
    private static String UNITS = "metric";
    private static String APPID = "0154ac07e7c0fc3b2556cc8e5da8ad48";
    private static String URI_ICON = "http://openweathermap.org/img/wn/";
    private static String ICON_END = "@2x.png";

    public OpenWeatherCall() {

    }

    /**
     * retrieves current weather for specified location
     *
     * @param city
     * @param countrycode
     * @return
     */
    public CurrentWeather getCurrentWeatherByCityAndCountry(String city, String countrycode) throws DestinationNotFoundException {
        String param = city.replaceAll(" ", "%20") + "," + countrycode;

        Client c = ClientBuilder.newClient();
        Response r = c.target(URI)
                .path(PATH_1)
                .queryParam("appid", APPID)
                .queryParam("units", UNITS)
                .queryParam("q", param)
                .request(MediaType.APPLICATION_JSON)
                .get();

        String responseStr = r.readEntity(String.class);

        if (responseStr.contains("\"cod\":\"404\"")) {
            throw new DestinationNotFoundException("Weather data for destination \"" + city + "\" could not be found!");
        }

        Gson gson = new Gson();
        CurrentWeather response = gson.fromJson(responseStr, CurrentWeather.class);

        return response;
    }

    /**
     * returns image (buffered due to testing requirements) of weather icon for
     * given icon id
     *
     * @param id
     * @return
     */
    public BufferedImage getWeatherIcon(String id) {
        BufferedImage image = null;
        try {
            URL url = new URL(URI_ICON + id + ICON_END);
            image = ImageIO.read(url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(OpenWeatherCall.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OpenWeatherCall.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }

    /**
     * returns 5-day weather forecast for given location
     *
     * @param city
     * @param countrycode
     * @return
     */
    public WeatherForecast getForecastByCityAndCountry(String city, String countrycode) throws DestinationNotFoundException {
        String param = city.replaceAll(" ", "%20") + "," + countrycode;

        Client c = ClientBuilder.newClient();
        Response r = c.target(URI)
                .path(PATH_2)
                .queryParam("appid", APPID)
                .queryParam("units", UNITS)
                .queryParam("q", param)
                .request(MediaType.APPLICATION_JSON)
                .get();

        String responseStr = r.readEntity(String.class);

        if (responseStr.contains("\"cod\":\"404\"")) {
            throw new DestinationNotFoundException("Weather data for destination \"" + city + "\" could not be found!");
        }

        Gson gson = new Gson();
        WeatherForecast response = gson.fromJson(responseStr, WeatherForecast.class);

        return response;
    }

}
