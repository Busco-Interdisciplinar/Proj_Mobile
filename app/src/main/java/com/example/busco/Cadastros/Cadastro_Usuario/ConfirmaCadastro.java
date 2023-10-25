package com.example.busco.Cadastros.Cadastro_Usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.text.TextWatcher;

import com.bumptech.glide.Glide;
import com.example.busco.Api.ApiResponse;
import com.example.busco.Api.ApiService;
import com.example.busco.Api.Models.Usuarios;
import com.example.busco.Login;
import com.example.busco.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmaCadastro extends AppCompatActivity {
    private String codigo;
    private Usuarios usuario;
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma_cadastro);
        Bundle bundle = getIntent().getExtras();
        String usuarioJson;
        codigo = bundle.getString("codigoFormatado");
        usuarioJson = bundle.getString("usuario");
        usuario = gson.fromJson(usuarioJson, Usuarios.class);
        String telefoneUsuario = usuario.getTelefone();
        usuario.setTelefone(telefoneUsuario.replace("+55", ""));
        ImageView imageView = (ImageView) findViewById(R.id.gif);
        Glide.with(this).load(R.raw.codigo_sms).into(imageView);
        setIntent(new Intent());
    }

    public void confirmar(View view){
        EditText digito1, digito2, digito3, digito4;
        digito1 = findViewById(R.id.editTextCodigo1);
        digito2 = findViewById(R.id.editTextCodigo2);
        digito3 = findViewById(R.id.editTextCodigo3);
        digito4 = findViewById(R.id.editTextCodigo4);

        String codigoCompleto = (digito1.getText().toString() + digito2.getText().toString() + digito3.getText().toString() + digito4.getText().toString());

        if (codigoCompleto.equals(codigo)){
            ApiService.getInstance().cadastrarUsuario(usuario).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null && response.body().isResponseSucessfull()){
                            List<Object> usuarioObject = response.body().getObject();
                            String objetoJson = gson.toJson(usuarioObject.get(0));
                            objetoJson = objetoJson.substring(1, objetoJson.length() - 1);
                            Usuarios usuarioCadastrado = gson.fromJson(objetoJson, Usuarios.class);
                            Intent in = new Intent(ConfirmaCadastro.this,Login.class);
                            in.putExtra("email", usuarioCadastrado.getEmail());
                            in.putExtra("senha", usuarioCadastrado.getSenha());
                            startActivity(in);
                            finish();
                            Toast.makeText(getApplicationContext(), response.body().getDescription(), Toast.LENGTH_LONG).show();
                        }
                    }else{
                        if (response.errorBody() != null){
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
                public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }else{
            finish();
            Toast.makeText(getApplicationContext(), "O código inserido é inválido", Toast.LENGTH_LONG).show();
        }
    }
}