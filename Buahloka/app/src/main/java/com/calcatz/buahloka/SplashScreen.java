package com.calcatz.buahloka;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user!=null){
                    intent = new Intent(SplashScreen.this,MainActivity.class);
                }
                else {
                    intent = new Intent(SplashScreen.this,LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },1500);
    }
}
