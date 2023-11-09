package com.example.busco.Doacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.busco.Fragments.inflate;
import com.example.busco.Fragments.principal_fragment;
import com.example.busco.R;

public class DoacaoEfetuada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doacao_efetuada);

        ImageView imageView = findViewById(R.id.gifemail);
        Glide.with(this).load(R.raw.email).into(imageView);
    }

    public void outraDoacao(View view) {
        Intent intent = new Intent(this, Doacao.class);
        startActivity(intent);
    }

    public void voltar(View view) {
        Intent intent = new Intent(getApplicationContext(), inflate.class);
        startActivity(intent);
    }


}