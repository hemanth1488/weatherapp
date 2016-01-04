package com.ringmd.weatherapp.Model;

import java.io.Serializable;

/**
 * Created by hemanth on 31/12/2015.
 */
public class DataPoint implements Serializable {
    Weather weather;
    String city;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataPoint dataPoint = (DataPoint) o;

        return city.equals(dataPoint.city);

    }

    @Override
    public int hashCode() {
        return city.hashCode();
    }

    public String getCity() {
        return city;
    }

    public DataPoint(String city) {
        this.city = city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Weather getWeather() {
        return weather;
    }

    public DataPoint(Weather weather) {
        this.weather = weather;
    }

    public DataPoint() {
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
