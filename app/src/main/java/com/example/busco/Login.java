package com.example.busco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.busco.Api.ApiResponse;
import com.example.busco.Api.ApiService;
import com.example.busco.Api.Models.Usuarios;
import com.example.busco.Cadastros.Cadastro_Instituicao.CadastroInstituicao;
import com.example.busco.Cadastros.Cadastro_Usuario.Cadastro;
import com.example.busco.Doacao.Doacao;
import com.example.busco.Fragments.inflate;
import com.example.busco.Fragments.principal_fragment;
import com.google.gson.Gson;

import java.io.IOException;

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
            Toast.makeText(this, "Conexão com a Internet restaurada", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("URL", url);
        } else {
            Intent intent = new Intent(this, Erro.class);
            startActivity(intent);
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
        startActivity( new Intent(this, Redefinir_Senha.class));
    }

    public void fazerLogin(View view) {

        EditText emailEditText = findViewById(R.id.email);
        EditText senhaEditText = findViewById(R.id.senha);

        String email = emailEditText.getText().toString().trim();
        String senha = senhaEditText.getText().toString().trim();

        if (email.equals("") || senha.equals("")){
            Toast.makeText(getApplicationContext(), "Email ou senha vazios", Toast.LENGTH_LONG).show();
        } else {
        ApiService.getInstance().logarUsuario(email.trim(), senha.trim()).enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null && response.body().isResponseSucessfull()){
                            }
                        }else {
                            if (response.errorBody() != null) {
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
        }
    }
}
