package com.example.busco;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

    private boolean senhaValida(String senha) {
        if (!senha.matches(".*[A-Z].*") || !senha.matches(".*[a-z].*") || !senha.matches(".*\\d.*") || senha.length() < 8) {
            return false;
        }
        return true;
    }

    private boolean emailValido(String email) {
        return email.contains("@gmail") && email.contains(".com");
    }

    public void voltar(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    interface TextValidator {
        boolean isValid(String text);
    }
}