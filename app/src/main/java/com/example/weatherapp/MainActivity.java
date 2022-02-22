package com.example.weatherapp;

import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    EditText cityNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        cityNameInput = (EditText)findViewById(R.id.cityName);
        cityNameInput.setOnEditorActionListener((v, id, event) -> {
            boolean handled = false;
            if (id == EditorInfo.IME_ACTION_DONE) {
                DataHandler dataHandler = new DataHandler();
                handled = dataHandler.setData(v.getText().toString(),this);
            }
            return handled;
        });
    }
}