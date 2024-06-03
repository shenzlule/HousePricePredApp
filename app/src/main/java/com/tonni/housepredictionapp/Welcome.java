package com.tonni.housepredictionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.tonni.housepredictionapp.credentials.Authentication;

import java.lang.reflect.Array;

public class Welcome extends AppCompatActivity {


    ImageView imageView;
    Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        imageView = findViewById(R.id.imageView);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
        imageView.startAnimation(animation);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), Authentication.class);
                startActivity(intent);

                finish(); //This closes current activity
            }
        }, 1300); //It means 4 seconds
    }
}