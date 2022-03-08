package com.example.weatherapp;

import android.app.Activity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class ViewHandler {
    Activity activity;


    public ViewHandler(Activity activity){
        this.activity = activity;
    }

    public void setCityName(String city){
        TextView cityName = (TextView) activity.findViewById(R.id.cityTV);
        cityName.setText(city);
    }

    public void setValuesFromJsonObject(JSONObject jsonObject){
        TextView temperature = (TextView) activity.findViewById(R.id.temperature);
        TextView wind = (TextView) activity.findViewById(R.id.wind);
        TextView pressure = (TextView) activity.findViewById(R.id.pressure);
        TextView visibility = (TextView) activity.findViewById(R.id.visibility);

        TextView[] nextDays = {
                (TextView) activity.findViewById(R.id.FirstDayData),
                (TextView) activity.findViewById(R.id.SecondDayData),
                (TextView) activity.findViewById(R.id.ThirdDayData),
                (TextView) activity.findViewById(R.id.FourthDayData)
        };

        Date date;
        DateFormat df = new SimpleDateFormat("dd E MMM");

        try {
            JSONArray array = jsonObject.getJSONArray("daily");

            for(int i=0; i<5; i++){
                if(i==0){
                    JSONObject current = jsonObject.getJSONObject("current");
                    temperature.setText(Math.round(current.getDouble("temp") - 273.15) + "℃");
                    wind.setText("Wind:"+current.getDouble("wind_speed") + " km/h");
                    pressure.setText("Pressure:"+current.getInt("pressure") + " hPa");
                    visibility.setText("Visibility:"+current.getInt("visibility")/1000 + "km");
                }else{
                    JSONObject insideObject = array.getJSONObject(i);
                    JSONObject temp = insideObject.getJSONObject("temp");
                    date = new Date((long)insideObject.getInt("dt")*1000);
                    nextDays[i-1].setText(df.format(date) + " " + (Math.round(temp.getDouble("day") - 273.15)) + "℃" + "/" + (Math.round(temp.getDouble("night") - 273.15)) + "℃");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
