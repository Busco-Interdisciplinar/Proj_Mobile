package com.example.busco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;

import com.example.busco.Api.ApiResponse;
import com.example.busco.Api.ApiService;
import com.example.busco.Api.Models.Rota;
import com.example.busco.Fragments.Map_Fragment;
import com.example.busco.Fragments.perfil_fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Localizacao extends AppCompatActivity {
    TextView rua1EditText, rua2EditText, rua3EditText, rua4EditText, hora1EditText, hora2EditText, hora3EditText, hora4EditText;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacao);

        Fragment fragment = new Map_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.map,fragment).commit();

        rua1EditText = findViewById(R.id.rua1);
        rua2EditText = findViewById(R.id.rua2);
        rua3EditText = findViewById(R.id.rua3);
        rua4EditText = findViewById(R.id.rua4);
        hora1EditText = findViewById(R.id.hora1);
        hora2EditText = findViewById(R.id.hora2);
        hora3EditText = findViewById(R.id.hora3);
        hora4EditText = findViewById(R.id.hora4);

        SharedPreferences sharedPreferences = getSharedPreferences("RotasData", Context.MODE_PRIVATE);
        String rua1 = sharedPreferences.getString("rua1", "");
        String rua2 = sharedPreferences.getString("rua2", "");
        String rua3 = sharedPreferences.getString("rua3", "");
        String rua4 = sharedPreferences.getString("rua4", "");
        String hora1Format = sharedPreferences.getString("hora1", "");
        String hora2Format = sharedPreferences.getString("hora2", "");
        String hora3Format = sharedPreferences.getString("hora3", "");
        String hora4Format = sharedPreferences.getString("hora4", "");

        if (rua1.equals("")){
            ApiService.getInstance().findRota(1).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null && response.body().isResponseSucessfull()){
                            List<Object> listaRotasObject = response.body().getObject();
                            List<Rota> listaRotas = new ArrayList<>();
                            Gson gson = new Gson();
                            for(Object object : listaRotasObject){
                                String rotaJson = gson.toJson(object);
                                Rota rota = gson.fromJson(rotaJson, Rota.class);
                                listaRotas.add(rota);
                            }
                            Rota rota1 = listaRotas.get(0);
                            Rota rota2 = listaRotas.get(1);
                            Rota rota3 = listaRotas.get(2);
                            Rota rota4 = listaRotas.get(3);

                            String rua1 = rota1.getRua();
                            String hora1 = rota1.getHorario();
                            String hora1Format = hora1.substring(0, hora1.length() - 3);

                            String rua2 = rota2.getRua();
                            String hora2 = rota2.getHorario();
                            String hora2Format = hora2.substring(0, hora2.length() - 3);

                            String rua3 = rota3.getRua();
                            String hora3 = rota3.getHorario();
                            String hora3Format = hora3.substring(0, hora3.length() - 3);

                            String rua4 = rota4.getRua();
                            String hora4 = rota4.getHorario();
                            String hora4Format = hora4.substring(0, hora4.length() - 3);

                            rua1EditText.setText(rua1);
                            rua2EditText.setText(rua2);
                            rua3EditText.setText(rua3);
                            rua4EditText.setText(rua4);
                            hora1EditText.setText(hora1Format);
                            hora2EditText.setText(hora2Format);
                            hora3EditText.setText(hora3Format);
                            hora4EditText.setText(hora4Format);

                            SharedPreferences sharedPreferences = getSharedPreferences("RotasData", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("rua1", rua1);
                            editor.putString("rua2", rua2);
                            editor.putString("rua3", rua3);
                            editor.putString("rua4", rua4);
                            editor.putString("hora1", hora1Format);
                            editor.putString("hora2", hora2Format);
                            editor.putString("hora3", hora3Format);
                            editor.putString("hora4", hora4Format);
                            editor.apply();
                            Toast.makeText(getApplicationContext(), "Rotas buscadas com sucesso", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Nenhuma rota encontrada", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                    Toast.makeText(getApplicationContext(), "Falha ao buscar as rotas", Toast.LENGTH_LONG).show();
                }
            });
        }else{
            rua1EditText.setText(rua1);
            rua2EditText.setText(rua2);
            rua3EditText.setText(rua3);
            rua4EditText.setText(rua4);
            hora1EditText.setText(hora1Format);
            hora2EditText.setText(hora2Format);
            hora3EditText.setText(hora3Format);
            hora4EditText.setText(hora4Format);
        }
    }

    public void voltarTelaLogin(View view) {
        Intent intent = new Intent(this, perfil_fragment.class);
        startActivity(intent);
    }
}