package com.example.customer.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

//TAMPILAN AWAL KOTAK TIGA

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();
        getSupportActionBar().setTitle("SEMUA BISA MASAK ENAK");

        //imageslider
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

    }
    public void logout (View view){

        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, MasukActivity.class);
        startActivity(intent);
        finish();
    }

    public void history (View view){

        Intent intent = new Intent(MainActivity.this, History.class);
        startActivity(intent);
        finish();
    }

    public void seafood(View view) {
        Intent intent = new Intent(MainActivity.this, TipeResep.class);
        intent.putExtra("tipe", "Seafood");
        startActivity(intent);

    }

    public void ayam(View view) {
        Intent intent = new Intent(MainActivity.this, TipeResep.class);
        intent.putExtra("tipe", "Ayam");
        startActivity(intent);

    }
     public void sayur (View view){
        Intent intent = new Intent( MainActivity.this, TipeResep.class);
         intent.putExtra("tipe", "Sayur");
        startActivity(intent);
     }




}
