package com.ringmd.weatherapp.RequestHandler;

import android.app.Activity;
import android.content.Context;

import com.ringmd.weatherapp.Adapter.WeatherAdapter;
import com.ringmd.weatherapp.R;
import com.ringmd.weatherapp.Util.MUtil;
import com.ringmd.weatherapp.Util.WeatherServiceClass;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hemanth on 31/12/2015.
 */
public class RequestHandler {

    private static String BASE_URL = "http://api.worldweatheronline.com/free/v2/weather.ashx?q=";
    private static String BASE_URL_PT2 = "&format=json&num_of_days=5&date=today&key=";

    public static String getWeatherData(String cityname,Context context){

        if(MUtil.isNetworkAvailable(context)) {
            HttpURLConnection con = null;
            InputStream is = null;

            try {
                con = (HttpURLConnection) (new URL(BASE_URL + cityname + BASE_URL_PT2 + context.getString(R.string.key))).openConnection();
                con.setRequestMethod("GET");
                con.setDoInput(true);
                con.setDoOutput(true);
                con.connect();

                //Reading the response
                StringBuffer buffer = new StringBuffer();
                is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while ((line = br.readLine()) != null)
                    buffer.append(line + "\r\n");
                is.close();
                con.disconnect();
                return buffer.toString();
            } catch (Exception e) {
                return "Error connecting to the server";
            } finally {
                try {
                    is.close();
                } catch (Throwable t) {
                }
                try {
                    con.disconnect();
                } catch (Throwable t) {
                }
            }
        }else{

            return "Internet not available";

        }

    }

    public static void setWeatherData(WeatherAdapter.ViewHolder holder, Activity mActivity, String city) {

        new WeatherServiceClass(holder,mActivity).execute(city);
    }
}
