package com.example.customer.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class MasukActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{


    //kenalin /variable global

    SignInButton mSignButton;

    GoogleApiClient mGoogleApiClient;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    String TAG = "Authactivity";
    int RC_SIGNIN = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);
        getSupportActionBar().hide();


        //TODO 1
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        //TODO 2
        mGoogleApiClient = new GoogleApiClient.Builder(MasukActivity.this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


        //TODO 3
        mAuth = FirebaseAuth.getInstance();

        //TODO 4
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "User Berhasil Login" + user.getUid());
                    Toast.makeText(MasukActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                    masukAplikasiUtama();
                } else {
                    Log.d(TAG, "Sign Out");
                    Toast.makeText(MasukActivity.this, "Anda Belum Login", Toast.LENGTH_SHORT).show();
                }
            }
        };
                //TODO 5 deklarasi dan pemberian aksi button google
                mSignButton = (SignInButton) findViewById(R.id.sign_in_button);
                mSignButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //UNTUK AMBIL DATA DARI GMAIL
                        Intent signIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                        startActivityForResult(signIntent, RC_SIGNIN);
                    }
                });


    }

    //TODO 6 mengambil data dari google api service

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGNIN){
            if (resultCode == RESULT_OK){
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()){
                    GoogleSignInAccount account = result.getSignInAccount();
                    firebaseAuthWithGoogle(account);
                }else {
                    Log.e(TAG, "Gagal Login");
                    Toast.makeText(this,"Gagal Login",Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    //TODO 7
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        Log.e(TAG," Firebase credential"+credential.toString() );
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(MasukActivity.this, "Gagal Autentifikasi", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Gagal Autentifikasi" +task.getException());
                }else {
                    Toast.makeText(MasukActivity.this,"Sukses",Toast.LENGTH_SHORT).show();
                    masukAplikasiUtama();
                }
            }

        });
    }


    //TODO 8
    private void masukAplikasiUtama() {

        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
    //TODO 9
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Toast.makeText(this, "Google Play Tidak Tersedia",Toast.LENGTH_SHORT).show();

    }
    //TODO 10
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    //TODO 11
    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


        }
