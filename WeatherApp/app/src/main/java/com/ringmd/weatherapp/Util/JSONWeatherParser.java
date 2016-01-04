package com.ringmd.weatherapp.Util;

import com.ringmd.weatherapp.Model.DataPoint;
import com.ringmd.weatherapp.Model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hemanth on 31/12/2015.
 */
public class JSONWeatherParser {

    public JSONWeatherParser() {

    }

    public DataPoint getWeather(String data) throws JSONException {
        DataPoint dp = new DataPoint();
        Weather weather = new Weather();   //This is just a class that has a bunch of strings in it for the weather info.
        JSONObject jObj = new JSONObject(data);

        //Parsing JSON data
        JSONObject dObj = jObj.getJSONObject("data");
        JSONArray cArr = dObj.getJSONArray("current_condition");
        JSONObject JSONCurrent = cArr.getJSONObject(0);
        weather.setCurrent_temp(getString("temp_F",JSONCurrent));
        weather.setHour(getString("observation_time",JSONCurrent));

        JSONArray jArrIcon = JSONCurrent.getJSONArray("weatherIconUrl");
        JSONObject JSONIcon = jArrIcon.getJSONObject(0);
        JSONArray jArrDesc = JSONCurrent.getJSONArray("weatherDesc");
        JSONObject JSONdesc = jArrDesc.getJSONObject(0);
        weather.setDescription(getString("value", JSONdesc));
        weather.setWeatherIconURL(getString("value", JSONIcon));
        weather.setDate(getString("observation_time", JSONCurrent));
        weather.setPrecipmm(getString("precipMM", JSONCurrent)+ " mm");
        weather.setFeelsLikeC(getString("FeelsLikeC", JSONCurrent));
        weather.setFeelsLikeF(getString("FeelsLikeF", JSONCurrent));
        weather.setTemp_C(getString("temp_C", JSONCurrent)+" C");
        weather.setTemp_F(getString("temp_F", JSONCurrent)+" F");
        weather.setWeatherCode(getString("weatherCode", JSONCurrent));
        weather.setWeatherIconURL(getString("value", JSONIcon));
        weather.setWinddir16point(getString("winddir16Point", JSONCurrent));
        weather.setWinddirDegree(getString("winddirDegree", JSONCurrent));
        weather.setWindspeedKmph(getString("windspeedKmph", JSONCurrent)+ " Kmph");
        weather.setWindspeedMiles(getString("windspeedMiles", JSONCurrent));
        weather.setCloudcover(getString("cloudcover",JSONCurrent));
        dp.setWeather(weather);
        return dp;
    }

    private static String getString(String tagName, JSONObject jObj)
            throws JSONException {
        return jObj.getString(tagName);
    }
}
