package com.ringmd.weatherapp.Util;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.ringmd.weatherapp.Adapter.WeatherAdapter;
import com.ringmd.weatherapp.Fragment.DismissableFragment;
import com.ringmd.weatherapp.Model.DataPoint;
import com.ringmd.weatherapp.MyApplication;
import com.ringmd.weatherapp.RequestHandler.RequestHandler;

/**
 * Created by hemanth on 31/12/2015.
 */
public class WeatherServiceClass extends AsyncTask<String, String, String> {
    private String city;
    Context context;
private WeatherAdapter.ViewHolder viewHolder;

    public WeatherServiceClass(WeatherAdapter.ViewHolder viewHolder,Context context) {
        this.viewHolder=viewHolder;
        this.context=context;
    }

    @Override
    protected String doInBackground(String... params) {


        city = params[0];

        String data = ((RequestHandler.getWeatherData(city,context)));

        return data;

    }

    @Override

    protected void onPostExecute(String result) {
        if (MyApplication.getErrorList().contains(result)) {

            DismissableFragment.show(context, "Error!!!!", result, "error", true, new Bundle(), "Ok", "Cancel");


        } else {
            DataPoint dp = new DataPoint();
            JSONWeatherParser jparser = new JSONWeatherParser();
            try {
                dp = jparser.getWeather(result);
                viewHolder.mMaxTextView.setText(dp.getWeather().getTemp_C());
                viewHolder.mMinTextView.setText(dp.getWeather().getTemp_F());
                viewHolder.mDescTextView.setText(dp.getWeather().getDescription());
                viewHolder.mPrecipitationTextView.setText(dp.getWeather().getPrecipmm());
                viewHolder.mcloudyTextView.setText(dp.getWeather().getWindspeedKmph());
                new DownloadImageTask(viewHolder.weatherimg)
                        .execute(dp.getWeather().getWeatherIconURL());
            } catch (Exception e) {
                DismissableFragment.show(context, "Error!!!!", "Error connecting to the server", "error", true, new Bundle(), "Ok", "Cancel");
                viewHolder.setVisibilitylevel(View.GONE);
            }

        }
    }
}
