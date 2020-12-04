package com.photoeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText urlStr = findViewById(R.id.editTextURL);
        Button btnShow = findViewById(R.id.btnShowNews);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String rssURL = urlStr.getText().toString();

            SharedPreferences localStorage = getSharedPreferences("RSS SETTINGS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = localStorage.edit();
            editor.putString("rss_url", rssURL);
            editor.apply();

            Intent intent = new Intent(getBaseContext(), ShowNewsActivity.class);
            startActivity(intent);
            }
        });

    }
}
