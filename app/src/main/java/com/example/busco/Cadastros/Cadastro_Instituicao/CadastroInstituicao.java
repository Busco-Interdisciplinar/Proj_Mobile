package com.example.busco.Cadastros.Cadastro_Instituicao;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.busco.Api.Models.Usuarios;
import com.example.busco.Cadastros.Cadastro_Usuario.Cadastro;
import com.example.busco.Cadastros.Cadastro_Usuario.ConfirmaCadastro_RedefinirSenha;
import com.example.busco.Doacao.Doacao;
import com.example.busco.Fragments.perfil_fragment;
import com.example.busco.R;
import com.google.gson.Gson;

import java.util.Random;

public class CadastroInstituicao extends AppCompatActivity {

    private EditText nomeEditText, emailEditText, CNPJEditText;
    private ImageView checkIconNome, checkIconEmail, checkIconCNPJ;
    private CheckBox checkBox;
    private Button criarConta;

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
        criarConta = findViewById(R.id.buttonCadastrar);

        setupTextWatchers();
        verificarEstadoBotao();
    }

    private void setupTextWatchers() {
        nomeEditText.addTextChangedListener(createTextWatcher(checkIconNome, this::nomeValido));
        emailEditText.addTextChangedListener(createTextWatcher(checkIconEmail, this::emailValido));
        CNPJEditText.addTextChangedListener(createTextWatcher(checkIconEmail, this::cnpjValido));

        CNPJEditText.addTextChangedListener(createMask(CNPJEditText, "##.###.###/####-##"));
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> verificarEstadoBotao());
    }

    private TextWatcher createTextWatcher(ImageView checkIcon, TextValidator validator) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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

    private TextWatcher createMask(EditText editText, String mask) {
        return new TextWatcher() {
            private boolean isUpdating;
            private String oldText = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                oldText = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                Boolean isDeleting = false;
                String newText = s.toString();
                String unmaskedText = newText.replaceAll("[^0-9]", "");
                String maskedText = "";

                int index = 0;
                for (char m : mask.toCharArray()) {
                    if (m != '#') {
                        if (index < unmaskedText.length() && unmaskedText.charAt(index) == m) {
                            maskedText += unmaskedText.charAt(index);
                            index++;
                        }
                        else if (oldText.length() > newText.length()) {
                            isDeleting = true;
                            break;
                        }
                        else {
                            maskedText += m;
                        }
                        continue;
                    }
                    try {
                        maskedText += unmaskedText.charAt(index);
                        index++;
                    } catch (Exception e) {
                        break;
                    }
                }
                if(isDeleting){
                    editText.removeTextChangedListener(this);
                    editText.setText(newText);
                    editText.addTextChangedListener(this);
                    editText.setSelection(newText.length());
                    return;
                }

                isUpdating = true;
                editText.setText(maskedText);
                editText.setSelection(maskedText.length());
            }
        };
    }

    private void verificarEstadoBotao() {
        boolean camposNaoVazios = camposNaoVazios();
        boolean imagensVerdes = imagensVerdes();

        if (camposNaoVazios && imagensVerdes && checkBox.isChecked()) {
            habilitarBotaoVerde();
        } else {
            desabilitarBotaoCinza();
        }
    }

    private boolean camposNaoVazios() {
        String nome = nomeEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String cnpj = CNPJEditText.getText().toString();

        return !nome.isEmpty() && !email.isEmpty() && !cnpj.isEmpty();
    }

    private void habilitarBotaoVerde() {
        criarConta.setBackgroundResource(R.drawable.button_backgroud_on);
    }

    private void desabilitarBotaoCinza() {
        criarConta.setBackgroundResource(R.drawable.button_background);
    }

    private boolean imagensVerdes() {
        return checkIconNome.getTag() != null
                && checkIconEmail.getTag() != null
                && checkIconCNPJ.getTag() != null;
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

    private boolean camposValidos() {
        String nome = nomeEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String cnpj = CNPJEditText.getText().toString();

        return nomeValido(nome) &&
                emailValido(email) &&
                cnpjValido(cnpj);
    }

    public void voltarTelaLogin(View view) {

    }

    public void criarConta(View view) {
        if (!camposValidos()) {
            Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!checkBox.isChecked()) {
            Toast.makeText(this, "Você deve aceitar os termos de contrato", Toast.LENGTH_SHORT).show();
            return;
        }

        if (camposValidos() && checkBox.isChecked()) {
            String nome = nomeEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String cnpj = CNPJEditText.getText().toString();

            //fazer a parte da api
        }
    }

    public void voltar(View view) {
        Intent intent = new Intent(this, perfil_fragment.class);
        startActivity(intent);
    }

    interface TextValidator {
        boolean isValid(String text);
    }
}