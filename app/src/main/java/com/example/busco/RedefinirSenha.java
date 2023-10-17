package com.example.busco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.busco.Login;
import com.example.busco.R;

public class RedefinirSenha extends AppCompatActivity {

    private EditText senhaEditText, emailEditText;
    private ImageView checkIconSenha, checkIconEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);

        senhaEditText = findViewById(R.id.textInputLayoutNovaSenha);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);


        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String email = editable.toString();
                if (emailValido(email)) {
                    checkIconEmail.setImageResource(R.drawable.check_verde);
                    checkIconEmail.setVisibility(View.VISIBLE);
                } else {
                    checkIconEmail.setImageResource(R.drawable.check_cinza);
                    checkIconEmail.setVisibility(View.VISIBLE);
                }
            }
        });

        senhaEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String senha = editable.toString();
                if (senhaValida(senha)) {
                    checkIconSenha.setImageResource(R.drawable.check_verde);
                    checkIconSenha.setVisibility(View.VISIBLE);
                } else {
                    checkIconSenha.setImageResource(R.drawable.check_cinza);
                    checkIconSenha.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private boolean emailValido(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean senhaValida(String senha) {
        return senha.length() >= 8;
    }

    private boolean camposValidos() {

        String email = emailEditText.getText().toString();
        String senha = senhaEditText.getText().toString();

        return emailValido(email) && senhaValida(senha);
    }

    public void continuar(View view) {
        if (!camposValidos()) {
            Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(this, "Senha alterada com sucesso", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
    }
}