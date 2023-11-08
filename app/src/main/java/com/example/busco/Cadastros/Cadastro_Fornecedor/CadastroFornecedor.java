package com.example.busco.Cadastros.Cadastro_Fornecedor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.busco.Api.ApiResponse;
import com.example.busco.Api.ApiService;
import com.example.busco.Api.Models.Fornecedor;
import com.example.busco.Api.Models.Usuarios;
import com.example.busco.Cadastros.Cadastro_Usuario.Cadastro;
import com.example.busco.Cadastros.Cadastro_Usuario.ConfirmaCadastro_RedefinirSenha;
import com.example.busco.Fragments.perfil_fragment;
import com.example.busco.Login;
import com.example.busco.R;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroFornecedor extends AppCompatActivity {
    private EditText cnpj;
    private ImageView checkIconCNPJ;
    private CheckBox checkBox;
    private Button buttonCriarConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_fornecedor);

        cnpj = findViewById(R.id.cnpj);
        checkIconCNPJ = findViewById(R.id.checkIconCNPJ);
        checkBox = findViewById(R.id.checkBox);
        buttonCriarConta = findViewById(R.id.buttonCadastrar);

        setupTextWatchers();
        verificarEstadoBotao();
    }
        private void setupTextWatchers() {

        cnpj.addTextChangedListener(createMask(cnpj, "##.###.###/####-##"));
        cnpj.addTextChangedListener(createTextWatcher(checkIconCNPJ, this::cnpjValido));
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> verificarEstadoBotao());
    }

    private TextWatcher createTextWatcher(ImageView checkIcon, CadastroFornecedor.TextValidator validator) {
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

    private boolean camposNaoVazios() {
        String c = cnpj.getText().toString();
        return !c.isEmpty();
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

    private void habilitarBotaoVerde() {
        buttonCriarConta.setBackgroundResource(R.drawable.button_backgroud_on);
        buttonCriarConta.setEnabled(true);
    }

    private void desabilitarBotaoCinza() {
        buttonCriarConta.setBackgroundResource(R.drawable.button_background);
        buttonCriarConta.setEnabled(true);
    }

    private boolean imagensVerdes() {
        return checkIconCNPJ.getTag() != null;
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

    public void voltar(View view) {
        finish();
    }

    public void cadastrar(View view) {
        if (!camposValidos()) {
            Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!checkBox.isChecked()) {
            Toast.makeText(this, "Você deve aceitar os termos de contrato", Toast.LENGTH_SHORT).show();
            return;
        }

        if (camposValidos() && checkBox.isChecked()){
            EditText cnpjEditText = findViewById(R.id.cnpj);
            String cnpj = cnpjEditText.getText().toString();
            cnpj = cnpj.replace(".", "").replace("/", "").replace("-", "");
            Gson gson = new Gson();

            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            String json = sharedPreferences.getString("user", "");
            Usuarios usuario = gson.fromJson(json, Usuarios.class);
            Fornecedor fornecedor = new Fornecedor(cnpj, usuario.getTelefone(), usuario.getNome(), usuario.getEmail(), usuario.getId());

            ApiService.getInstance().inserirFornecedor(fornecedor).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null && response.body().isResponseSucessfull()){
                            Toast.makeText(getApplicationContext(), "Você agora é um fornecedor, obrigado!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Falha ao cadastrar fornecedor", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {

                }
            });
        }
    }

    private boolean camposValidos() {
        String campoCnpj = cnpj.getText().toString();
        return cnpjValido(campoCnpj);
    }

    interface TextValidator {
        boolean isValid(String text);
    }
}