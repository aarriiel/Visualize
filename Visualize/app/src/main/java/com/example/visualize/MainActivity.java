package com.example.visualize;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.StrictMode;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Disable android's restriction on running network operations on the main thread
        // of an application.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }


}
