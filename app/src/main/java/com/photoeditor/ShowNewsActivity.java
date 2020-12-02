package com.photoeditor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ShowNewsActivity extends AppCompatActivity {

    ListView newsList = null;
    ArrayList<NewsList> newItems = new ArrayList<NewsList>();
    NewsListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);

        SharedPreferences localStorage = getSharedPreferences("RSS SETTINGS", Context.MODE_PRIVATE);
        String rssURL = localStorage.getString("rss_url", "unknown").trim();

        getRSSList(rssURL);
        String rss = localStorage.getString("rss_doc", "unknown").trim();
        newItems = NewsList.createNewsList(rss);

        newsList = findViewById(R.id.listViewRss);
        adapter = new NewsListAdapter(this, newItems);
        newsList.setAdapter(adapter);
    }

    void getRSSList(String urlRSS){
        //String url = "https://lenta.ru/rss/articles";
        //String url = "https://habr.com/ru/rss/hubs/all/";
        String url = "https://news.mail.ru/rss/sport/";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest requestForApplications = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SharedPreferences localStorage = getSharedPreferences("RSS SETTINGS", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = localStorage.edit();
                editor.putString("rss_doc", response);
                editor.apply();
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
}
