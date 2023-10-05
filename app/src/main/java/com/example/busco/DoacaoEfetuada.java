package com.example.busco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DoacaoEfetuada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doacao_efetuada);
    }

    public void outraDoacao(View view) {
        Intent intent = new Intent(this, Doacao.class);
        startActivity(intent);
    }
}