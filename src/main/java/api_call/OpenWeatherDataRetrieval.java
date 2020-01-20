/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_call;


import api_response.OpenWeatherResponse;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author elisc
 */
public class OpenWeatherDataRetrieval {
    
    private static String URI = "http://api.openweathermap.org/data/2.5/";
    private static String PATH = "weather";
    private static String UNITS = "metric";
    private static String APPID = "0154ac07e7c0fc3b2556cc8e5da8ad48";
    
    public OpenWeatherResponse getWeatherForecast(String city, String countrycode){
        Client c = ClientBuilder.newClient();
        Response r = c.target(URI)
                .path(PATH)
                .queryParam("appid", APPID)
                .queryParam(UNITS)
                .queryParam("q", city + "," + countrycode)
                .request(MediaType.APPLICATION_JSON)
                .get();

        String jsonString = r.readEntity(String.class);
        Gson gson = new Gson();
        OpenWeatherResponse response = gson.fromJson(jsonString, OpenWeatherResponse.class);
        
        return response;
    }
    
}
