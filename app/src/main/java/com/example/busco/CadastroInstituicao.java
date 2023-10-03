package com.example.busco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

public class CadastroInstituicao extends AppCompatActivity {

    private EditText nomeEditText, emailEditText, CNPJEditText;
    private ImageView checkIconNome, checkIconEmail, checkIconCNPJ;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_instituicao);

        nomeEditText = findViewById(R.id.editTextTextPersonName);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        CNPJEditText = findViewById(R.id.textInputLayoutCNPJ);

        checkIconNome = findViewById(R.id.checkIconNome);
        checkIconEmail = findViewById(R.id.checkIconEmail);
        checkIconCNPJ = findViewById(R.id.checkIconCNPJ);
        checkBox = findViewById(R.id.checkBox);

        nomeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String nome = editable.toString();
                if (nomeValido(nome)) {
                    checkIconNome.setImageResource(R.drawable.check_verde);
                    checkIconNome.setVisibility(View.VISIBLE);
                } else {
                    checkIconNome.setImageResource(R.drawable.check_cinza);
                    checkIconNome.setVisibility(View.VISIBLE);
                }
            }
        });

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

        CNPJEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String cnpj = editable.toString();
                if (cnpjValido(cnpj)) {
                    checkIconCNPJ.setImageResource(R.drawable.check_verde);
                    checkIconCNPJ.setVisibility(View.VISIBLE);
                } else {
                    checkIconCNPJ.setImageResource(R.drawable.check_cinza);
                    checkIconCNPJ.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean cnpjValido(String cnpj){
        cnpj = cnpj.replaceAll("[^0-9]", "");
        if (cnpj.length() != 14) {
            return false;
        }

        int soma = 0;
        int peso = 5;
        for (int i = 0; i < 12; i++) {
            soma += (cnpj.charAt(i) - '0') * peso;
            peso--;
            if (peso == 1) {
                peso = 9;
            }
        }
        int resto = soma % 11;
        int digito1 = (resto < 2) ? 0 : (11 - resto);
        soma = 0;
        peso = 6;
        for (int i = 0; i < 13; i++) {
            soma += (cnpj.charAt(i) - '0') * peso;
            peso--;
            if (peso == 1) {
                peso = 9;
            }
        }
        resto = soma % 11;
        int digito2 = (resto < 2) ? 0 : (11 - resto);

        return (cnpj.charAt(12) - '0' == digito1) && (cnpj.charAt(13) - '0' == digito2);
    }

    private boolean nomeValido(String nome) {
        return !nome.isEmpty() && nome.matches("[a-zA-Z ]+");
    }

    private boolean emailValido(String email) {
        return email.contains("@gmail") && email.contains(".com");
    }
}