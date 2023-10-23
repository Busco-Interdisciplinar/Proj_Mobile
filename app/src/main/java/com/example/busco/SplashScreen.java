package com.example.busco;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_SCREEN_DELAY = 2000; // Tempo de atraso em milissegundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(this::run, SPLASH_SCREEN_DELAY);
    }

    public void run() {
        // Cria uma Intent para abrir a MainActivity
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
        finish(); // Fecha a tela de splash
    }
}