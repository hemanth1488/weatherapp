package com.ringmd.weatherapp.Model;

import java.io.Serializable;

/**
 * Created by hemanth on 31/12/2015.
 */
public class Weather implements Serializable{
    String current_temp;
    String hour;
    String date;
    String precipmm;
    String temp_C;
    String temp_F;
    String humidity;
    String pressure;
    String FeelsLikeF;
    String FeelsLikeC;
    String weatherCode;
    String weatherIconURL;
    String winddir16point;
    String winddirDegree;
    String windspeedKmph;
    String windspeedMiles;
    String cloudcover;
    String description;

    public String getCurrent_temp() {
        return current_temp;
    }

    public void setCurrent_temp(String current_temp) {
        this.current_temp = current_temp;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrecipmm() {
        return precipmm;
    }

    public void setPrecipmm(String precipmm) {
        this.precipmm = precipmm;
    }

    public String getTemp_C() {
        return temp_C;
    }

    public void setTemp_C(String temp_C) {
        this.temp_C = temp_C;
    }

    public String getTemp_F() {
        return temp_F;
    }

    public void setTemp_F(String temp_F) {
        this.temp_F = temp_F;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getFeelsLikeF() {
        return FeelsLikeF;
    }

    public void setFeelsLikeF(String feelsLikeF) {
        FeelsLikeF = feelsLikeF;
    }

    public String getFeelsLikeC() {
        return FeelsLikeC;
    }

    public void setFeelsLikeC(String feelsLikeC) {
        FeelsLikeC = feelsLikeC;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getWeatherIconURL() {
        return weatherIconURL;
    }

    public void setWeatherIconURL(String weatherIconURL) {
        this.weatherIconURL = weatherIconURL;
    }

    public String getWinddir16point() {
        return winddir16point;
    }

    public void setWinddir16point(String winddir16point) {
        this.winddir16point = winddir16point;
    }

    public String getWinddirDegree() {
        return winddirDegree;
    }

    public void setWinddirDegree(String winddirDegree) {
        this.winddirDegree = winddirDegree;
    }

    public String getWindspeedKmph() {
        return windspeedKmph;
    }

    public void setWindspeedKmph(String windspeedKmph) {
        this.windspeedKmph = windspeedKmph;
    }

    public String getWindspeedMiles() {
        return windspeedMiles;
    }

    public void setWindspeedMiles(String windspeedMiles) {
        this.windspeedMiles = windspeedMiles;
    }

    public String getCloudcover() {
        return cloudcover;
    }

    public void setCloudcover(String cloudcover) {
        this.cloudcover = cloudcover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Weather() {
    }


}
