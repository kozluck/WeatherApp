package com.example.weatherapp;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DataHandler {
    String apiKeyWeather = "ca24c435872fdf4bc3ae39e625230aae";

    public boolean setData(String cityName, Activity activity){
        Geocoder geocoder = new Geocoder(activity.getApplicationContext());
        try {
            List<Address> address = geocoder.getFromLocationName(cityName,5);
            Address location = address.get(0);
            ViewHandler viewHandler = new ViewHandler(activity);
            JSONObject jsonObject = getDataFromApi(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
            viewHandler.setValuesFromJsonObject(jsonObject);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private JSONObject getDataFromApi(String lat, String lon){
        HttpURLConnection http;
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" +lat+"&lon="+lon+"&appid="+apiKeyWeather);
            http = (HttpsURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.connect();

            StringBuffer buffer = new StringBuffer();
            InputStream is = http.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;

            while((line=br.readLine())!=null)
                buffer.append(line);
            is.close();
            http.disconnect();
            Log.d("JSON:",buffer.toString());

            return new JSONObject(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
