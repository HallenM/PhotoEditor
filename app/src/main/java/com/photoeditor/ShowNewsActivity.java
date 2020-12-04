package com.photoeditor;

import android.content.Context;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;

public class ShowNewsActivity extends AppCompatActivity {

    ListView newsList = null;
    ArrayList<NewsList> newItems = new ArrayList<NewsList>();
    NewsListAdapter adapter = null;
    Boolean nonFilter1 = true;
    Boolean nonFilter2 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);

        SharedPreferences localStorage = getSharedPreferences("RSS SETTINGS", Context.MODE_PRIVATE);
        String rssURL = localStorage.getString("rss_url", "unknown_url").trim();

        getRSSList(rssURL);
        SharedPreferences.Editor editor = localStorage.edit();
        editor.clear();
        editor.commit();

        Button btnFilter1 = findViewById(R.id.btnFilter1);
        Button btnFilter2 = findViewById(R.id.btnFilter2);

        btnFilter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nonFilter1) {
                    adapter = new NewsListAdapter(getBaseContext(), newItems, 1);
                } else {
                    adapter = new NewsListAdapter(getBaseContext(), newItems, 0);
                }
                newsList.setAdapter(adapter);

                nonFilter1 = !nonFilter1;
                nonFilter2 = true;
            }
        });

        btnFilter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nonFilter2) {
                    adapter = new NewsListAdapter(getBaseContext(), newItems, 2);
                } else {
                    adapter = new NewsListAdapter(getBaseContext(), newItems, 0);
                }
                newsList.setAdapter(adapter);

                nonFilter2 = !nonFilter2;
                nonFilter1 = true;
            }
        });
    }

    void getRSSList(String url){
        //url = "https://lenta.ru/rss/articles";
        //url = "https://habr.com/ru/rss/hubs/all/";
        //url = "https://news.mail.ru/rss/sport/";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest requestForApplications = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getRSSDoc(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERR_GET_REQ", error.toString());
            }
        });

        // Tag the request
        String TAG = "Tag_for_get_xml_request";
        // Set the tag on the request.
        requestForApplications.setTag(TAG);
        queue.add(requestForApplications);
        queue.start();
    }

    void getRSSDoc(String response) {
        newItems = NewsList.createNewsList(response);

        newsList = findViewById(R.id.listViewRss);
        adapter = new NewsListAdapter(this, newItems, 0);
        newsList.setAdapter(adapter);
    }
}

