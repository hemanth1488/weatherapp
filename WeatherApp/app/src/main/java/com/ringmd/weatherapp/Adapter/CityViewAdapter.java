package com.ringmd.weatherapp.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ringmd.weatherapp.R;

import java.util.List;

/**
 * Created by hemanth on 1/1/2016.
 */
public class CityViewAdapter extends ArrayAdapter<String> {

    Activity mContext;
    int layoutResourceId;
    List<String>  data = null;

    public CityViewAdapter(Activity mContext, int layoutResourceId, List<String> data) {

        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        // object item based on the position
        String city = data.get(position);

        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView textViewItem = (TextView) convertView.findViewById(R.id.title);
        textViewItem.setText(city);



        return convertView;

    }

}
