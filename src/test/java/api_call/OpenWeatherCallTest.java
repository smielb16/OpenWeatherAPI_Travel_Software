/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_call;

import api_current_weather.CurrentWeather;
import api_current_weather.Sys;
import api_forecast.City;
import api_forecast.WeatherForecast;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author elisc
 */
public class OpenWeatherCallTest {
    
    public OpenWeatherCallTest() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }

    /**
     * Test of getCurrentWeatherByCityAndCountry method, of class OpenWeatherCall.
     */
    @org.junit.jupiter.api.Test
    public void testGetCurrentWeatherByCityAndCountry() {
        System.out.println("getCurrentWeatherByCityAndCountry");
        String city = "Graz";
        String countrycode = "AT";
        OpenWeatherCall instance = new OpenWeatherCall();
        
        CurrentWeather result = new CurrentWeather();
        Sys sysRes = new Sys();
        sysRes.setCountry("Error");
        result.setSys(sysRes);
        try {
            result = instance.getCurrentWeatherByCityAndCountry(city, countrycode);
        } catch (DestinationNotFoundException ex) {
            Logger.getLogger(OpenWeatherCallTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        assertEquals(countrycode, result.getSys().getCountry());
    }

    /**
     * assertEquals() apparently will not compare byte arrays :(
     * Test of getWeatherIcon method, of class OpenWeatherCall.
     */
    /*@org.junit.jupiter.api.Test
    public void testGetWeatherIcon() throws IOException {
        System.out.println("getWeatherIcon");
        String id = "10n";
        OpenWeatherCall instance = new OpenWeatherCall();
        
        URL url = new URL("http://openweathermap.org/img/wn/" + id + "@2x.png");
        BufferedImage expResult = ImageIO.read(url);
        byte[] expImgArr = ((DataBufferByte) expResult.getData().getDataBuffer()).getData();
        
        BufferedImage result = instance.getWeatherIcon(id);
        byte[] resImgArr = ((DataBufferByte) result.getData().getDataBuffer()).getData();
        
        assertEquals(expImgArr, resImgArr);
    }*/

    /**
     * Test of getForecastByCityAndCountry method, of class OpenWeatherCall.
     */
    @org.junit.jupiter.api.Test
    public void testGetForecastByCityAndCountry() {
        System.out.println("getForecastByCityAndCountry");
        String city = "Graz";
        String countrycode = "AT";
        OpenWeatherCall instance = new OpenWeatherCall();
        
        WeatherForecast result = new WeatherForecast();
        City cityRes = new City();
        cityRes.setCountry("Error");
        result.setCity(cityRes);
        try {
            result = instance.getForecastByCityAndCountry(city, countrycode);
        } catch (Exception ex) {
            Logger.getLogger(OpenWeatherCallTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        assertEquals(countrycode, result.getCity().getCountry());
    }
    
}
