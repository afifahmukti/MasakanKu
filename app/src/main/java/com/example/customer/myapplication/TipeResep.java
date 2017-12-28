package com.example.customer.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TipeResep extends AppCompatActivity implements ResepAdapter.ClickListener {
     private RecyclerView recyclerView;
     String tipe;
     private List<DataResep> dataReseps;

     private ResepAdapter adapter;
     private DatabaseReference mReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seafood);
        tipe = getIntent().getStringExtra("tipe");
        getSupportActionBar().setTitle(tipe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        mReference = FirebaseDatabase.getInstance().getReference(tipe);
        fetchData();
    }



    @Override
    protected void onStart() {
        super.onStart();
        fetchData();
    }

    //get tipe resep
    public void  input(View view){
        Intent intent = new Intent(this, InputActivity.class);
        intent.putExtra("tipe",tipe);
        startActivity(intent);
    }





    @Override
    public void onClickPosition(int position) {
        Gson gson = new Gson();
        String data = gson.toJson(dataReseps.get(position));
        Intent intent = new Intent(this, DetailResep.class);
        intent.putExtra("data", data);
        startActivity(intent);
    }

    private void fetchData(){
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    if (dataSnapshot.getChildrenCount() > 0){
                        dataReseps = new ArrayList<>();
                        for(DataSnapshot dataResep : dataSnapshot.getChildren()){
                            DataResep data = dataResep.getValue(DataResep.class);
                            dataReseps.add(data);
                        }
                        adapter = new ResepAdapter(dataReseps, TipeResep.this);
                        recyclerView.setAdapter(adapter);

                    }
                    else
                    {
                        Toast.makeText(TipeResep.this, "KOSONG", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(TipeResep.this, "KOSONG", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TipeResep.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

    }
}


