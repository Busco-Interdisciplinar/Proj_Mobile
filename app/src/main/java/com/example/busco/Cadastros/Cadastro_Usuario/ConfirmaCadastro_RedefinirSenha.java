package com.example.busco.Cadastros.Cadastro_Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.busco.Api.ApiResponse;
import com.example.busco.Api.ApiService;
import com.example.busco.Api.Models.Usuarios;
import com.example.busco.Login;
import com.example.busco.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmaCadastro_RedefinirSenha extends AppCompatActivity {
    private String codigo;
    private Usuarios usuario;
    private final Gson gson = new Gson();
    private Bundle bundle;
    String emailRecebido;
    String novaSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma_cadastro_redefinir_senha);
        bundle = getIntent().getExtras();
        if (bundle != null){
            if (Objects.equals(bundle.getString("action"), "senha")){
                emailRecebido = bundle.getString("email");
                novaSenha = bundle.getString("senha");
                codigo = bundle.getString("codigoFormatado");
                Button botao = findViewById(R.id.btnConfirmar);
                botao.setText("REDEFINIR SENHA");
                TextView tituloTela = findViewById(R.id.tituloCadastro_Senha);
                tituloTela.setText("Confirmar redefinição de senha");
            }else{
                String usuarioJson;
                codigo = bundle.getString("codigoFormatado");
                usuarioJson = bundle.getString("usuario");
                usuario = gson.fromJson(usuarioJson, Usuarios.class);
                String telefoneUsuario = usuario.getTelefone();
                telefoneUsuario = telefoneUsuario.replace("(", "");
                telefoneUsuario = telefoneUsuario.replace(")", "");
                telefoneUsuario = telefoneUsuario.replace(" ", "");
                usuario.setTelefone(telefoneUsuario.replace("+55", ""));
            }
        }

        ImageView imageView = findViewById(R.id.gif);
        Glide.with(this).load(R.raw.codigo_sms).into(imageView);
        setIntent(new Intent());
        setupEditTextListeners();

        EditText digito1, digito2, digito3, digito4;
        digito1 = findViewById(R.id.editTextCodigo1);
        digito2 = findViewById(R.id.editTextCodigo2);
        digito3 = findViewById(R.id.editTextCodigo3);
        digito4 = findViewById(R.id.editTextCodigo4);

        String digitoRecebido1 = codigo.substring(0, 1);
        String digitoRecebido2 = codigo.substring(1, 2);
        String digitoRecebido3 = codigo.substring(2, 3);
        String digitoRecebido4 = codigo.substring(3, 4);

        digito1.setText(digitoRecebido1);
        digito2.setText(digitoRecebido2);
        digito3.setText(digitoRecebido3);
        digito4.setText(digitoRecebido4);
    }

    private void setupEditTextListeners() {
        EditText digito1, digito2, digito3, digito4;
        digito1 = findViewById(R.id.editTextCodigo1);
        digito2 = findViewById(R.id.editTextCodigo2);
        digito3 = findViewById(R.id.editTextCodigo3);
        digito4 = findViewById(R.id.editTextCodigo4);

        digito1.addTextChangedListener(new GenericTextWatcher(digito1, digito2));
        digito2.addTextChangedListener(new GenericTextWatcher(digito2, digito3));
        digito3.addTextChangedListener(new GenericTextWatcher(digito3, digito4));
        digito4.addTextChangedListener(new GenericTextWatcher(digito4, null));
    }

    private class GenericTextWatcher implements TextWatcher {
        private final View view;
        private final EditText next;

        private GenericTextWatcher(View view, EditText next) {
            this.view = view;
            this.next = next;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            if (text.length() == 1 && next != null) {
                next.requestFocus();
            }
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
    }

    public void confirmar(View view){
        EditText digito1, digito2, digito3, digito4;
        digito1 = findViewById(R.id.editTextCodigo1);
        digito2 = findViewById(R.id.editTextCodigo2);
        digito3 = findViewById(R.id.editTextCodigo3);
        digito4 = findViewById(R.id.editTextCodigo4);

        String codigoCompleto = (digito1.getText().toString() + digito2.getText().toString() + digito3.getText().toString() + digito4.getText().toString());
        if(bundle != null){
            if (Objects.equals(bundle.getString("action"), "senha")){
                resetarSenha(emailRecebido, novaSenha, codigoCompleto);
            }else{
                if (codigoCompleto.equals(codigo)){
                    ApiService.getInstance().cadastrarUsuario(usuario).enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                            if (response.isSuccessful()){
                                if (response.body() != null && response.body().isResponseSucessfull()){
                                    List<Object> usuarioObject = response.body().getObject();
                                    String objetoJson = gson.toJson(usuarioObject.get(0));
                                    Usuarios usuarioCadastrado = gson.fromJson(objetoJson, Usuarios.class);
                                    Intent in = new Intent(ConfirmaCadastro_RedefinirSenha.this, Login.class);
                                    in.putExtra("email", usuarioCadastrado.getEmail());
                                    in.putExtra("senha", usuarioCadastrado.getSenha());
                                    startActivity(in);
                                    finish();
                                    Toast.makeText(getApplicationContext(), response.body().getDescription(), Toast.LENGTH_LONG).show();
                                }
                            } else {
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
                        public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                            Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    finish();
                    Toast.makeText(getApplicationContext(), "O código inserido é inválido", Toast.LENGTH_LONG).show();
                }
            }
        }


    }

    public void resetarSenha(String email, String novaSenha, String codigoCompleto){
        if (codigoCompleto.equals(codigo)){
            ApiService.getInstance().resetSenha(email, novaSenha).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null && response.body().isResponseSucessfull()){
                            Intent in = new Intent(ConfirmaCadastro_RedefinirSenha.this, Login.class);
                            in.putExtra("email", email);
                            in.putExtra("senha", novaSenha);
                            startActivity(in);
                            finish();
                            Toast.makeText(getApplicationContext(), response.body().getDescription(), Toast.LENGTH_LONG).show();
                        }
                    }else{
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
