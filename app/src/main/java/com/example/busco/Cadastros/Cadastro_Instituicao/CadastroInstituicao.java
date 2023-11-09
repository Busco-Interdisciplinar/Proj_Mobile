package com.example.busco.Cadastros.Cadastro_Instituicao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.busco.Api.ApiResponse;
import com.example.busco.Api.ApiService;
import com.example.busco.Api.Models.Instituicao;
import com.example.busco.Api.Models.Usuarios;
import com.example.busco.Fragments.perfil_fragment;
import com.example.busco.Login;
import com.example.busco.R;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroInstituicao extends AppCompatActivity {

    private EditText nomeEditText, cepEditText, CNPJEditText;
    private ImageView checkIconNome, cepIcon, checkIconCNPJ;
    private CheckBox checkBox;
    private Button criarConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_instituicao);

        nomeEditText = findViewById(R.id.cnpj);

        cepEditText = findViewById(R.id.editTextTextCep);
        CNPJEditText = findViewById(R.id.editTextCNPJ);

        checkIconNome = findViewById(R.id.checkIconNome);
        cepIcon = findViewById(R.id.checkIconCepInstituicao);
        checkIconCNPJ = findViewById(R.id.checkIconCNPJ);
        checkBox = findViewById(R.id.checkBox);
        criarConta = findViewById(R.id.buttonCadastrar);

        setupTextWatchers();
        verificarEstadoBotao();
    }

    private void setupTextWatchers() {
        nomeEditText.addTextChangedListener(createTextWatcher(checkIconNome, this::nomeValido));
        cepEditText.addTextChangedListener(createTextWatcher(cepIcon, this::cepValido));
        CNPJEditText.addTextChangedListener(createTextWatcher(checkIconCNPJ, this::cnpjValido));

        CNPJEditText.addTextChangedListener(createMask(CNPJEditText, "##.###.###/####-##"));
        cepEditText.addTextChangedListener(createMask(CNPJEditText, "#####-###"));
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
        String cep = cepEditText.getText().toString();
        String cnpj = CNPJEditText.getText().toString();

        return !nome.isEmpty() && !cep.isEmpty() && !cnpj.isEmpty();
    }

    private void habilitarBotaoVerde() {
        criarConta.setBackgroundResource(R.drawable.button_backgroud_on);
    }

    private void desabilitarBotaoCinza() {
        criarConta.setBackgroundResource(R.drawable.button_background);
    }

    private boolean imagensVerdes() {
        return checkIconNome.getTag() != null
                && cepIcon.getTag() != null
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


    private boolean cepValido(String cep) {
        cep = cep.replaceAll("[^0-9]", "");
        return cep.length() == 8;
    }

    private boolean camposValidos() {
        String nome = nomeEditText.getText().toString();
        String cep = cepEditText.getText().toString();
        String cnpj = CNPJEditText.getText().toString();

        return nomeValido(nome) &&
                cepValido(cep) &&
                cnpjValido(cnpj);
    }

    public void voltarTelaLogin(View view) {
        finish();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
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
            String cep = cepEditText.getText().toString();
            String cnpj = CNPJEditText.getText().toString();
            Gson gson = new Gson();

            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            String json = sharedPreferences.getString("user", "");
            Usuarios usuario = gson.fromJson(json, Usuarios.class);
            Instituicao instituicao = new Instituicao(cnpj, cep, nome, usuario.getTelefone(), usuario.getId());

            ApiService.getInstance().inserirInstituicao(instituicao).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null && response.body().isResponseSucessfull()){
                            Toast.makeText(getApplicationContext(), "Você agora é uma instituição, obrigado!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Falha ao cadastrar instituição", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void voltar(View view) {
        finish();
    }

    interface TextValidator {
        boolean isValid(String text);
    }
}