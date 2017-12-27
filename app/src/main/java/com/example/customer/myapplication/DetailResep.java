package com.example.customer.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DetailResep extends AppCompatActivity {

    private TextView bahan,penyajian,tips;
    private ImageView gambar;
    private DataResep dataResep;

    protected class OpenBrowserListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String theURL = "https://www.youtube.com/watch?v=sYPmBWZjjxQ";
            Intent iOpenBrowser1 = new Intent(Intent.ACTION_VIEW, Uri.parse(theURL));
            startActivity(iOpenBrowser1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balado);
        String data = getIntent().getStringExtra("data");
        Gson gson = new Gson();
        dataResep = gson.fromJson(data, new TypeToken<DataResep>(){}.getType());
        getSupportActionBar().setTitle(dataResep.getNama());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tips = findViewById(R.id.tips);
        penyajian = findViewById(R.id.penyajian);
        gambar = (ImageView)  findViewById(R.id.gambar);
        Glide.with(this).load(dataResep.getGambar()).into(gambar);
        bahan = findViewById(R.id.bahan);
        tips.setText(dataResep.getTips());
        bahan.setText(dataResep.getBahan());
        penyajian.setText(dataResep.getPenyajian());
        //Button btnOpenBrowser = (Button)this.findViewById(R.id.btnOpenBrowser1);
        //btnOpenBrowser.setOnClickListener(new OpenBrowserListener());
    }
}
