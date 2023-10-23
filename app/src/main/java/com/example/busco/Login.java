package com.example.busco;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busco.Cadastros.Cadastro_Usuario.Cadastro;
import com.example.busco.Fragments.inflate;

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
                openWebView("https://www.google.com.br");
            }
        });

        facebookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebView("https://www.facebook.com");
            }
        });

        instagramImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebView("https://www.instagram.com");
            }
        });
    }

    private void openWebView(String url) {
        if (isNetworkAvailable()) {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("URL", url);
            startActivity(intent);
        } else {
            showNoInternetConnectionMessage();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    private void showNoInternetConnectionMessage() {
        Toast.makeText(this, "Sem conexão à internet", Toast.LENGTH_SHORT).show();
    }

    public void criarConta(View view) {

        startActivity( new Intent(this, Cadastro.class));
    }

    public void redefinirSenha(View view) {
        startActivity( new Intent(this, Redefinir_Senha.class));
    }

    public void fazerLogin(View view) {
        startActivity( new Intent(this, inflate.class));
        finish();
    }
}


//Usar finish para não sobrepor