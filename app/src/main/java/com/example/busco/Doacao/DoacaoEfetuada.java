package com.example.busco.Doacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.busco.Doacao.Doacao;
import com.example.busco.Fragments.principal_fragment;
import com.example.busco.R;

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

    public void voltar(View view) {
        Intent intent = new Intent(this, principal_fragment.class);
        startActivity(intent);
    }
}