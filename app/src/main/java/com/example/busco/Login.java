
package com.example.busco;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busco.Api.ApiResponse;
import com.example.busco.Api.ApiService;
import com.example.busco.Api.Models.Usuarios;
import com.example.busco.Cadastros.Cadastro_Instituicao.CadastroInstituicao;
import com.example.busco.Cadastros.Cadastro_Usuario.Cadastro;
import com.example.busco.Doacao.Doacao;
import com.example.busco.Fragments.inflate;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private final Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String emailCadastrado;
        String senhaCadastrada;
        EditText emailEditText = findViewById(R.id.email);
        EditText senhaEditText = findViewById(R.id.senha);

        Bundle bundleInfoCadastrada = getIntent().getExtras();
        if(bundleInfoCadastrada != null){
            emailCadastrado = bundleInfoCadastrada.getString("email");
            senhaCadastrada = bundleInfoCadastrada.getString("senha");
            emailEditText.setText(emailCadastrado);
            senhaEditText.setText(senhaCadastrada);
        }
        setIntent(new Intent());

        ImageView googleImageView = findViewById(R.id.google);
        ImageView facebookImageView = findViewById(R.id.facebook);
        ImageView instagramImageView = findViewById(R.id.instagram);

        googleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarTelaDeErro();
                openWebView("https://www.google.com.br");
            }
        });

        facebookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarTelaDeErro();
                openWebView("https://www.facebook.com");
            }
        });

        instagramImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarTelaDeErro();
                openWebView("https://www.instagram.com");
            }
        });
    }

    private void openWebView(String url) {
        mostrarTelaDeErro();

        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("URL", url);
        startActivity(intent);
    }

    private void mostrarTelaDeErro() {
        if (isNetworkAvailable()) {
            Toast.makeText(this, "Conexão com a Internet restaurada", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, Erro.class);
            startActivity(intent);
            finish();
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

    public void criarConta(View view) {
        Intent intent = new Intent(this, Cadastro.class);
        startActivity(intent);
    }

    public void redefinirSenha(View view) {
        startActivity( new Intent(this, inflate.class));
    }

    public void fazerLogin(View view) {
        if (isNetworkAvailable()) {
//            ApiService.getInstance().logarUsuario(email, senha).enqueue(new Callback<ApiResponse>() {
//                @Override
//                public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
//                    if (response.isSuccessful()) {
//                        if (response.body() != null && response.body().isResponseSucessfull()) {
//                            Intent in = new Intent(Login.this, Doacao.class);
//                            startActivity(in);
//                            Toast.makeText(getApplicationContext(), response.body().getDescription(), Toast.LENGTH_LONG).show();
//                            finish();
//                        }
//                    } else {
//                        if (response.errorBody() != null) {
//                            Intent intent = new Intent(getApplicationContext(), Erro.class);
//                            startActivity(intent);
//                            finish();
        EditText emailEditText = findViewById(R.id.email);
        EditText senhaEditText = findViewById(R.id.senha);
        String email = emailEditText.getText().toString();
        String senha = senhaEditText.getText().toString();
        ApiService.getInstance().logarUsuario(email, senha).enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null && response.body().isResponseSucessfull()){
                                List<Object> usuarioObject = response.body().getObject();
                                String objetoJson = gson.toJson(usuarioObject.get(0));
                                objetoJson = objetoJson.substring(1, objetoJson.length() - 1);
                                Usuarios usuarioCadastrado = gson.fromJson(objetoJson, Usuarios.class);
                                Intent in = new Intent(Login.this, inflate.class);
                                startActivity(in);
                                Toast.makeText(getApplicationContext(), response.body().getDescription(), Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }else {
                            if (response.errorBody() != null) {
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                                finish();
                                try {
                                    String apiResponseString = response.errorBody().string();
                                    ApiResponse apiResponseError = gson.fromJson(apiResponseString, ApiResponse.class);
                                    Toast.makeText(getApplicationContext(), apiResponseError.getDescription(), Toast.LENGTH_LONG).show();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                if (!isNetworkAvailable()) {
                    Intent intent = new Intent(getApplicationContext(), Erro.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                }
            }

        });
        } else {
            Intent intent = new Intent(this, Erro.class);
            startActivity(intent);
        }
        Intent intent = new Intent(this, Cadastro.class);
        startActivity(intent);
    }
}
