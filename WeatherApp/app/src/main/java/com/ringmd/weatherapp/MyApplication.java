package com.ringmd.weatherapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MyApplication  extends Application {
	private static Context mContext;
	static String PREF_KEY_STRINGS="citylist";
    public static List<String> cityList=new ArrayList<>();
	static SharedPreferences.Editor editor;
	public MyApplication () {
	}

    public static List<String> getCityList() {
        return getData();
    }

    public static void setCityList(List<String> cityList) {
        saveData(cityList);
    }

    @Override
	public void onCreate() {
		super.onCreate();

		mContext=this;
		editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();

	}


	public static Context getContext(){
        return mContext;
    }
	public static void saveData(List<String>cityList)
	{

		editor.putString(PREF_KEY_STRINGS, TextUtils.join(",", cityList));
		editor.commit();
	}
	public static List<String> getData()
	{SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
		String serialized = prefs.getString(PREF_KEY_STRINGS, "");

	    return  new ArrayList(Arrays.asList(TextUtils.split(serialized, ",")));
	}

 public static List<String> getErrorList(){
	 return Arrays.asList(getContext().getResources().getStringArray(R.array.city));


 }
}