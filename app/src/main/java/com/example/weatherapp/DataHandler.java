package com.example.weatherapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class DataHandler {
    String apiKeyWeather = "ca24c435872fdf4bc3ae39e625230aae";

    public boolean setData(String cityName, Context context){
        Geocoder geocoder = new Geocoder(context);
        try {
            List<Address> address = geocoder.getFromLocationName(cityName,5);
            Address location = address.get(0);

            Log.d("Latitude:", String.valueOf(location.getLatitude()));
            Log.d("Longtitude:", String.valueOf(location.getLongitude()));
            getDataFromApi(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private HashMap getDataFromApi(String lat, String lon){
        HttpURLConnection http = null;
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
            Log.d("fromURL", buffer.toString());


            http.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }



        return new HashMap();
    }

}
