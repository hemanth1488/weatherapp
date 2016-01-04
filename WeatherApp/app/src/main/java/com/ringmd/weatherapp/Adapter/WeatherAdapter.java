package com.ringmd.weatherapp.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ringmd.weatherapp.Model.DataPoint;
import com.ringmd.weatherapp.R;
import com.ringmd.weatherapp.RequestHandler.RequestHandler;
import com.ringmd.weatherapp.Util.WeatherServiceClass;

import java.util.ArrayList;
import java.util.List;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private final Activity mActivity;
    private List<DataPoint> mDataset = new ArrayList<DataPoint>();
    OnItemClickListener mItemClickListener;

    public WeatherAdapter(Activity mActivity, List<DataPoint> scoreList) {
        this.mActivity = mActivity;
        mDataset=scoreList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View sView = mInflater.inflate(R.layout.weatheritem, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder , int position) {
        holder.mNameTextView.setText(mDataset.get(position).getCity());

        RequestHandler.setWeatherData(holder,mActivity,mDataset.get(position).getCity());




		
    }

    @Override
    public int getItemCount() {

        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    	 public ImageView weatherimg;
         public TextView mNameTextView,mMaxTextView,mMinTextView,mPrecipitationTextView,mDescTextView,mcloudyTextView;

View view;
        public void setVisibilitylevel(int value){

            view.setVisibility(value);
        }
         /**
          * Constructor
          * @param v The container view which holds the elements from the row item xml
          */
         public ViewHolder(View v) {
             super(v);

             view=v;

             mNameTextView = (TextView) v.findViewById(R.id.title);
             mMaxTextView = (TextView) v.findViewById(R.id.max);
             mMinTextView = (TextView) v.findViewById(R.id.min);
             mPrecipitationTextView = (TextView) v.findViewById(R.id.precipitation);
             mDescTextView = (TextView) v.findViewById(R.id.desc);
             mcloudyTextView = (TextView) v.findViewById(R.id.cloudcover);
             weatherimg=(ImageView)v.findViewById(R.id.imagetitle);
             v.setOnClickListener(this);
                }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }

    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


}


