package com.ringmd.weatherapp.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ringmd.weatherapp.Adapter.CityViewAdapter;
import com.ringmd.weatherapp.Adapter.WeatherAdapter;
import com.ringmd.weatherapp.Listener.OnDialogFragmentListener;
import com.ringmd.weatherapp.Model.DataPoint;
import com.ringmd.weatherapp.MyApplication;
import com.ringmd.weatherapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WeatherActivity extends AppCompatActivity implements OnDialogFragmentListener {
    Toolbar toolbar;
    ActionBarDrawerToggle mDrawerToggle;
    RelativeLayout drawerRel;

    List<String> citylist,selectedcitylist;
    List<DataPoint> dataPoints;
    ListView listView;
    ListView citylistView;
    WeatherAdapter weatherAdapter;
    DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        setView();

    }
    void setView(){

        toolbar = (Toolbar)findViewById(R.id.toolbar);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        drawerRel=(RelativeLayout)findViewById(R.id.drawerrel);
        int width = getResources().getDisplayMetrics().widthPixels/2;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) drawerRel.getLayoutParams();
        params.width = width;
        drawerRel.setLayoutParams(params);
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close){
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
                invalidateOptionsMenu();
                syncState();
            }
            public void onDrawerOpened(View v){
                super.onDrawerOpened(v);
                invalidateOptionsMenu();
                syncState();
            }
        };
        citylist=new ArrayList<String>();
        selectedcitylist=new ArrayList<String>();
        selectedcitylist= MyApplication.getCityList();
        dataPoints=new ArrayList<DataPoint>();
        citylist= Arrays.asList(getResources().getStringArray(R.array.city));
        Collections.sort(citylist);
        setcitydata();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mDrawerToggle.syncState();


        RecyclerView weatherrecyclerView = (RecyclerView)findViewById(R.id.weather_recycler_view);
        weatherrecyclerView.setHasFixedSize(true);
        weatherrecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        weatherAdapter = new WeatherAdapter(this,dataPoints);
        weatherrecyclerView.setAdapter(weatherAdapter);
        citylistView = (ListView) findViewById(R.id.city_list);

        CityViewAdapter cityadapter = new CityViewAdapter(this,R.layout.cityitem,citylist);
        citylistView.setAdapter(cityadapter);
        cityadapter.notifyDataSetChanged();

        citylistView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        citylistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    mDrawerLayout.closeDrawer(drawerRel);

                                                    CheckedTextView checkedTextView=(CheckedTextView)view;

                                                    if (!checkedTextView.isChecked()) {

                                                        selectedcitylist.remove(citylist.get(position));
                                                        view.setSelected(false);
                                                        removedata(citylist.get(position));
                                                    } else {

                                                        selectedcitylist.add(citylist.get(position));
                                                        dataPoints.add(new DataPoint(citylist.get(position)));
                                                        view.setSelected(true);

                                                    }

                                                    MyApplication.setCityList(selectedcitylist);
                                                    weatherAdapter.notifyDataSetChanged();
                                                }


                                            }
        );
        if(selectedcitylist.size()==0){

            mDrawerLayout.openDrawer(drawerRel);
        }

        citylistView.clearFocus();
        citylistView.post(new Runnable() {

            @Override
            public void run() {

                citylistView.setSelection(0);
            }
        });
        citylistView.setSelection(0);
        cityadapter.notifyDataSetChanged();
        setchecked();
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home: {
                if (mDrawerLayout.isDrawerOpen(drawerRel)){
                    mDrawerLayout.closeDrawer(drawerRel);
                } else {
                    mDrawerLayout.openDrawer(drawerRel);
                }
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }


    void setcitydata(){
        dataPoints.clear();
        for(String city:selectedcitylist){

            dataPoints.add(new DataPoint(city));

        }
    }
    void removedata(String city){
        DataPoint temp=new DataPoint(city);
        for(DataPoint dataPoint:dataPoints){

            if(dataPoint.equals(temp))
            {
                dataPoints.remove(dataPoint);
                break;
            }
        }


    }

    void setchecked(){
        int i=0;
        for(String city:citylist){

            if(selectedcitylist.contains(city)){
                citylistView.setItemChecked(i,true);


            }
            i++;
        }


    }
    @Override
    public void onCancelledDialog(String tag, Bundle bundle) {

    }

    @Override
    public void onClickedPositiveDialog(String tag, Bundle bundle) {


    }

    public void  refresh(View v){

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
