package com.example.weatherapp;

import android.app.Activity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewHandler {
    Activity activity;


    public ViewHandler(Activity activity){
        this.activity = activity;
    }

    public void setValuesFromJsonObject(JSONObject jsonObject){
        TextView cityName = (TextView) activity.findViewById(R.id.cityTV);
        TextView temperature = (TextView) activity.findViewById(R.id.temperature);
        TextView wind = (TextView) activity.findViewById(R.id.wind);
        TextView pressure = (TextView) activity.findViewById(R.id.pressure);
        TextView visibility = (TextView) activity.findViewById(R.id.visibility);


        try {
            JSONObject main = jsonObject.getJSONObject("main");
            JSONObject windObject = jsonObject.getJSONObject("wind");

            cityName.setText(jsonObject.getString("name"));
            temperature.setText(Math.round(main.getDouble("temp") - 273.15) + "℃");
            wind.setText("Wind:"+windObject.getDouble("speed") + " km/h");
            pressure.setText("Pressure:"+main.getInt("pressure") + " hPa");
            visibility.setText("Visibility:"+jsonObject.getInt("visibility")/1000 + "km");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
