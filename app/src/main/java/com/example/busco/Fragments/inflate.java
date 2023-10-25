package com.example.busco.Fragments;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busco.NavigationManager.NavigationManager;
import com.example.busco.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class inflate extends AppCompatActivity {

    NavigationManager navigationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation);

        navigationManager = new NavigationManager(getSupportFragmentManager());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item1:
                        navigationManager.abrirTela(new principal_fragment());
                        return true;
                    case R.id.produto:
                        navigationManager.abrirTela(new produtos_fragment());
                        return true;
                    case R.id.carrinho:
                        navigationManager.abrirTela(new carrinho_fragment());
                        return true;
                    case R.id.promocao:
                        navigationManager.abrirTela(new promocao_fragment());
                        return true;
                    case R.id.perfil:
                        navigationManager.abrirTela(new perfil_fragment());
                        return true;
                }
                return false;
            }
        });
    }
}

