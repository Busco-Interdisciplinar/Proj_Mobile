package com.example.busco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView googleImageView = findViewById(R.id.google);
        ImageView facebookImageView = findViewById(R.id.facebook);
        ImageView instagramImageView = findViewById(R.id.instagram);
        googleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    abrirWebView("https://www.google.com.br/");
                } else {
                    Intent intent = new Intent(Login.this, Erro.class);
                    startActivity(intent);
                }
            }
        });
        facebookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    abrirWebView("https://www.facebook.com/");
                } else {
                    Toast.makeText(getApplicationContext(), "Sem conexão à internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        instagramImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    abrirWebView("https://www.instagram.com/");
                } else {
                    Toast.makeText(getApplicationContext(), "Sem conexão à internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
    private void abrirWebView(String url) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("URL", url);
        startActivity(intent);
    }
    public void criarConta(View view) {
        Intent intent = new Intent(this, Cadastro.class);
        startActivity(intent);
    }
    public void redefinirSenha(View view) {
        Intent intent = new Intent(this, RedefinirSenha.class);
        startActivity(intent);
    }
}