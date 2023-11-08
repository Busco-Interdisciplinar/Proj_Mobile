package com.example.busco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.busco.Api.ApiResponse;
import com.example.busco.Api.ApiService;
import com.example.busco.Api.Models.Usuarios;
import com.example.busco.Cadastros.Cadastro_Usuario.ConfirmaCadastro_RedefinirSenha;
import com.google.gson.Gson;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Redefinir_Senha extends AppCompatActivity {

    private EditText emailEditText, novaSenhaEditText;
    private ImageView checkIconEmail, checkIconSenhaNova;
    private Button buttonContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);

        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        novaSenhaEditText = findViewById(R.id.editTextTextSenha);
        checkIconEmail = findViewById(R.id.checkIconEmail);
        checkIconSenhaNova = findViewById(R.id.checkIconSenhaNova);
        buttonContinuar = findViewById(R.id.buttonContinuar);

        verificarEstadoBotao();
        setupTextWatchers();
    }

    private void setupTextWatchers() {
        novaSenhaEditText.addTextChangedListener(createTextWatcher(checkIconSenhaNova, this::senhaValida));
        emailEditText.addTextChangedListener(createTextWatcher(checkIconEmail, this::emailValido));
    }

    private TextWatcher createTextWatcher(ImageView checkIcon, TextValidator validator) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                int iconResource = validator.isValid(text) ? R.drawable.check_verde : R.drawable.check_cinza;
                checkIcon.setImageResource(iconResource);
                checkIcon.setTag(iconResource == R.drawable.check_verde ? "verde" : null);
                verificarEstadoBotao();
            }
        };
    }

        private void verificarEstadoBotao() {
        boolean camposNaoVazios = camposNaoVazios();
        boolean imagensVerdes = imagensVerdes();

        if (camposNaoVazios && imagensVerdes) {
            habilitarBotaoVerde();
        } else {
            desabilitarBotaoCinza();
        }
    }

    private boolean camposNaoVazios() {
        String senhaNova = novaSenhaEditText.getText().toString();
        String email = emailEditText.getText().toString();

        return !senhaNova.isEmpty() && !email.isEmpty();
    }

    private boolean imagensVerdes() {
        return checkIconSenhaNova.getTag() != null && checkIconSenhaNova.getTag().equals("verde") &&
                checkIconEmail.getTag() != null && checkIconEmail.getTag().equals("verde");
    }

    private void habilitarBotaoVerde() {
        buttonContinuar.setBackgroundResource(R.drawable.button_backgroud_on);
    }

    private void desabilitarBotaoCinza() {
        buttonContinuar.setBackgroundResource(R.drawable.button_background);
    }

    private boolean emailValido(String email) {
        return email != null && email.trim().matches("^[A-Za-z0-9+_.-]+@[gmail|outlook|hotmail|germinare|org]+\\.(com|com.br|org|org.br)$");
    }

    private boolean senhaValida(String senha) {
        if (!senha.matches(".*[a-z].*")) {
            return false;
        }
        if (!senha.matches(".*\\d.*")) {
            return false;
        }
        if (senha.length() < 8) {
            return false;
        }
        return true;
    }

    public void voltar(View view) {
        finish();
    }
    interface TextValidator {
        boolean isValid(String text);
    }

    private boolean contagem = true;
    public void resetarSenha(View view) {
        if (contagem = true){
            contagem = true;

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    contagem = true;
                }
            }, 6000);

            String email = emailEditText.getText().toString();
            String novaSenha = novaSenhaEditText.getText().toString();

            if (emailValido(email) && senhaValida(novaSenha)){
                ApiService.getInstance().findByEmail(email).enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null && response.body().isResponseSucessfull()){
                                Random random = new Random();
                                Gson gson = new Gson();
                                int codigo = random.nextInt(10000);
                                String codigoFormatado = String.format("%04d", codigo);
                                String mensagem = "Verificação Busco: " + codigoFormatado;
                                List<Object> apiResponse = response.body().getObject();
                                String usuarioEncontradoJson = gson.toJson(apiResponse.get(0));
                                Usuarios usuarioEncontrado = gson.fromJson(usuarioEncontradoJson, Usuarios.class);
                                String telefone = usuarioEncontrado.getTelefone();

                                Intent intentSMS = new Intent(getApplicationContext(), ConfirmaCadastro_RedefinirSenha.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("codigoFormatado", codigoFormatado);
                                bundle.putString("email", email);
                                bundle.putString("senha", novaSenha);
                                bundle.putString("action", "senha");
                                intentSMS.putExtras(bundle);

                                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 1, intentSMS,PendingIntent.FLAG_IMMUTABLE);
                                SmsManager sms = SmsManager.getDefault();
                                try {
                                    sms.sendTextMessage(telefone, null, mensagem, pi,null);
                                }catch (Exception e){
                                    Toast.makeText(getApplicationContext(),"Não foi possível enviar o SMS, verifica a conectividade\n" + e , Toast.LENGTH_LONG).show();
                                    startActivity(intentSMS);
                                }
                                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 3000);
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"Usuário não encontrado no banco", Toast.LENGTH_LONG).show();
                            emailEditText.requestFocus();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                        Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                Toast.makeText(this, "Email ou senha não segue os padrões", Toast.LENGTH_SHORT).show();
            }
        }
    }
}