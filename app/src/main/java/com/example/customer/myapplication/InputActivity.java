package com.example.customer.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class InputActivity extends AppCompatActivity {

    private final static int PICK_IMAGE_REQUEST = 0;
    private EditText bahan, penyajian, nama, tips;
    private Button btnInput, btnGambar;
    private ImageView gambar;
    private String tipe;
    private String pushId;
    private boolean finish1 = true, finish2 = true;
    private DatabaseReference mReference;
    private StorageReference mStorage;
    SharedPreferences sp;
    SharedPreferences.Editor spEdit;

    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tipe = getIntent().getStringExtra("tipe");
        mStorage = FirebaseStorage.getInstance().getReference(tipe);
        mReference = FirebaseDatabase.getInstance().getReference(tipe);
        bahan = (EditText) findViewById(R.id.bahan);
        tips = findViewById(R.id.tips);
        penyajian = (EditText) findViewById(R.id.penyajian);
        nama = findViewById(R.id.nama);
        btnInput = (Button) findViewById(R.id.btnInput);
        btnGambar = (Button) findViewById(R.id.btnGambar);
        gambar = (ImageView)  findViewById(R.id.gambar);

        sp = getSharedPreferences(History.PREF_RESEP, MODE_PRIVATE);
        spEdit = sp.edit();
    }

    public void pilihGambar(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK){
            uri = data.getData();
            gambar.setImageURI(uri);
        }
    }

    public void input(View view){
        final String bahan,penyajian, nama, tips;
        tips = this.tips.getText().toString();
        nama = this.nama.getText().toString();
        bahan = this.bahan.getText().toString();
        penyajian = this.penyajian.getText().toString();

        //input to firebase
        if(   tips.trim().length() > 0&& nama.trim().length() > 0 &&bahan.trim().length() > 0 && penyajian.trim().length() > 0 && uri != null ){
            if(finish1 && finish2){
                finish1 = false;
                finish2 = false;
                pushId = mReference.push().getKey();
                final AlertDialog dialog = new AlertDialog.Builder(this).setMessage("Memproses ...")
                        .create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                mStorage.child(pushId).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        finish1 = true;
                        DataResep  dataResep = new DataResep(bahan,taskSnapshot.getDownloadUrl().toString(),penyajian,nama, tips);
                        mReference.child(pushId).setValue(dataResep, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if(databaseError != null){
                                    Toast.makeText(InputActivity.this,"Data ada yang gagal disimpan" ,Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                                else{
                                    Toast.makeText(InputActivity.this,"Data disimpan" ,Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    finish();
                                }
                                finish2 = true;
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        finish1 = true;
                        finish2 = true;
                        Toast.makeText(InputActivity.this,"Data ada yang gagal disimpan" ,Toast.LENGTH_SHORT).show();

                    }
                });
            }
            else{
                Toast.makeText(this,"Gagal Memproses.Coba lagi" ,Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(this,"Data masih ada yang kosong" ,Toast.LENGTH_SHORT).show();
        }


        // input to sharedpreference
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("judulHistory",nama);
            jsonObject.put("tipeHistory",tipe);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (sp.contains("resep")){
            String data = sp.getString("resep","NO_DATA");
            Log.i("if","masuk");
            try {
                JSONArray jsonArray = new JSONArray(data);
                jsonArray.put(jsonObject);
                spEdit.putString("resep",jsonArray.toString());
                spEdit.apply();
                Log.i("try","masuk");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonObject);
            spEdit.putString("resep",jsonArray.toString());
            spEdit.apply();
            Log.i("else","masuk");
        }

        Toast.makeText(this, "sharedpreference judul "+nama, Toast.LENGTH_SHORT).show();
//        finish();
    }

}
