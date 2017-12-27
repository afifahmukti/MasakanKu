package com.example.customer.myapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class History extends AppCompatActivity {

    public static String PREF_RESEP = "file.resep.history";
    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.rv_history);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllHistory();
    }

    private void getAllHistory() {
        SharedPreferences sp = this.getSharedPreferences(PREF_RESEP,MODE_PRIVATE);
        String data = sp.getString("resep","NO_DATA");
        Log.i("getHistory","masuk");

        String judul = sp.getString("judulHistory","");

        Log.i("getHistory","judul "+judul);



//        if (data!=null){
//            try {
//                JSONArray jsonArray = new JSONArray(data);
//                historyAdapter = new HistoryAdapter(this,jsonArray);
//                recyclerView.setAdapter(historyAdapter);
//                historyAdapter.notifyDataSetChanged();
//
//                Log.i("adapter","set");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }else {
//            JSONArray jsonArray = new JSONArray();
//            historyAdapter = new HistoryAdapter(this,jsonArray);
//            recyclerView.setAdapter(historyAdapter);
//            historyAdapter.notifyDataSetChanged();
//            Log.i("adapter","set");
//        }
    }
}
