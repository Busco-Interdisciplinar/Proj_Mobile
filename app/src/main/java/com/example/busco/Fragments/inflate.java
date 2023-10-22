package com.example.busco.Fragments;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busco.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class inflate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Lide com os eventos de clique aqui
                switch (item.getItemId()) {
                    case R.id.menu_item1:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new principal_fragment())
                                .commit();
                        return true;
                    case R.id.produto:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new produtos_fragment())
                                .commit();
                        return true;
                    case R.id.carrinho:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new carrinho_fragment())
                                .commit();
                        return true;
                    case R.id.promocao:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new promocao_fragment())
                                .commit();
                        return true;
//                    case R.id.perfil:
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.fragment_container, new perfil_fragment())
//                                .commit();
//                        return true;
                }
                return false;
            }
        });
    }
}

