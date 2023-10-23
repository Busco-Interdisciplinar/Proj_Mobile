package com.example.busco.Cadastros.Cadastro_Usuario;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busco.Login;
import com.example.busco.R;

public class Cadastro extends AppCompatActivity {

    private EditText nomeEditText, emailEditText, cpfEditText, senhaEditText, confirmarSenhaEditText, telefoneEditText, cepEditText;
    private ImageView checkIconNome, checkIconEmail, checkIconCpf, checkIconSenha, checkIconConfirmarSenha, checkIconTelefone, checkIconCep;
    private CheckBox checkBox;
    private Button buttonCriarConta;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nomeEditText = findViewById(R.id.editTextTextPersonName);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        cpfEditText = findViewById(R.id.editTextNumber);
        senhaEditText = findViewById(R.id.editTextTextPassword2);
        confirmarSenhaEditText = findViewById(R.id.editTextTextPassword3);
        telefoneEditText = findViewById(R.id.editTextTelefone);
        cepEditText = findViewById(R.id.editTextCep);
        checkBox = findViewById(R.id.checkBox);
        buttonCriarConta = findViewById(R.id.buttonCriarConta);

        checkIconTelefone = findViewById(R.id.checkIconTelefone);
        checkIconNome = findViewById(R.id.checkIconNome);
        checkIconEmail = findViewById(R.id.checkIconEmail);
        checkIconCpf = findViewById(R.id.checkIconCpf);
        checkIconSenha = findViewById(R.id.checkIconSenha);
        checkIconCep = findViewById(R.id.checkIconCep);
        checkIconConfirmarSenha = findViewById(R.id.checkIconConfirmarSenha);

        setupTextWatchers();
        verificarEstadoBotao();
    }

    private void setupTextWatchers() {
        nomeEditText.addTextChangedListener(createTextWatcher(checkIconNome, this::nomeValido));
        emailEditText.addTextChangedListener(createTextWatcher(checkIconEmail, this::emailValido));
        cpfEditText.addTextChangedListener(createTextWatcher(checkIconCpf, this::cpfValido));
        senhaEditText.addTextChangedListener(createTextWatcher(checkIconSenha, this::senhaValida));
        confirmarSenhaEditText.addTextChangedListener(createTextWatcher(checkIconConfirmarSenha, text -> confirmacaoSenha(text, senhaEditText.getText().toString())));
        telefoneEditText.addTextChangedListener(createTextWatcher(checkIconTelefone, this::telefoneValido));
        cepEditText.addTextChangedListener(createTextWatcher(checkIconCep, this::cepValido));

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            verificarEstadoBotao();
        });
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
                if (text.isEmpty()) {
                    checkIcon.setImageResource(R.drawable.check_cinza);
                    checkIcon.setTag(null);
                } else if (validator.isValid(text)) {
                    checkIcon.setImageResource(R.drawable.check_verde);
                    checkIcon.setTag("verde");
                } else {
                    checkIcon.setImageResource(R.drawable.check_cinza);
                    checkIcon.setTag(null);
                }

                verificarEstadoBotao();
            }
        };
    }

    private void verificarEstadoBotao() {
        if (imagensVerdes() && checkBox.isChecked() && camposNaoVazios()) {
            habilitarBotaoVerde();
        } else {
            desabilitarBotaoCinza();
        }
    }

    private boolean camposNaoVazios() {
        String nome = nomeEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String cpf = cpfEditText.getText().toString();
        String senha = senhaEditText.getText().toString();
        String confirmarSenha = confirmarSenhaEditText.getText().toString();
        String telefone = telefoneEditText.getText().toString();
        String cep = cepEditText.getText().toString();

        return !nome.isEmpty() && !email.isEmpty() && !cpf.isEmpty() && !senha.isEmpty() && !confirmarSenha.isEmpty() && !telefone.isEmpty() && !cep.isEmpty();
    }

    private void habilitarBotaoVerde() {
        buttonCriarConta.setBackground(getDrawable(R.drawable.button_backgroud_on));
    }

    private void desabilitarBotaoCinza() {
        buttonCriarConta.setBackground(getDrawable(R.drawable.button_background));
    }

    private boolean imagensVerdes() {
        return checkIconNome.getTag() != null
                && checkIconEmail.getTag() != null
                && checkIconCpf.getTag() != null
                && checkIconSenha.getTag() != null
                && checkIconConfirmarSenha.getTag() != null
                && checkIconTelefone.getTag() != null
                && checkIconCep.getTag() != null;
    }

    private boolean nomeValido(String nome) {
        return nome.length() >= 3;
    }

    private boolean emailValido(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(gmail|outlook|hotmail|germinare)\\.(com|br|com.br|org.br)$";
        return email.matches(regex);
    }

    private boolean cpfValido(String cpf) {
        // Remove caracteres não numéricos do CPF
        cpf = cpf.replaceAll("[^0-9]", "");

        // CPF deve ter 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifique se todos os dígitos são iguais (caso de CPF inválido)
        boolean digitosIguais = true;
        for (int i = 1; i < 11; i++) {
            if (cpf.charAt(i) != cpf.charAt(0)) {
                digitosIguais = false;
                break;
            }
        }

        if (digitosIguais) {
            return false;
        }

        // Validação dos dígitos verificadores do CPF
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int primeiroDigito = 11 - (soma % 11);

        if (primeiroDigito == 10 || primeiroDigito == 11) {
            primeiroDigito = 0;
        }

        if (primeiroDigito != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int segundoDigito = 11 - (soma % 11);

        if (segundoDigito == 10 || segundoDigito == 11) {
            segundoDigito = 0;
        }

        return segundoDigito == Character.getNumericValue(cpf.charAt(10));
    }


    private boolean senhaValida(String senha) {
        return senha.length() >= 6;
    }

    private boolean confirmacaoSenha(String confirmarSenha, String senha) {
        return confirmarSenha.equals(senha);
    }

    private boolean telefoneValido(String telefone) {
        telefone = telefone.replaceAll("[^0-9]", "");
        return telefone.length() >= 10;
    }

    private boolean cepValido(String cep) {
        cep = cep.replaceAll("[^0-9]", "");
        return cep.length() == 8;
    }

    private boolean camposValidos() {
        String nome = nomeEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String cpf = cpfEditText.getText().toString();
        String senha = senhaEditText.getText().toString();
        String confirmarSenha = confirmarSenhaEditText.getText().toString();
        String telefone = telefoneEditText.getText().toString();
        String cep = cepEditText.getText().toString();

        return nomeValido(nome) &&
                emailValido(email) &&
                cpfValido(cpf) &&
                senhaValida(senha) &&
                confirmacaoSenha(confirmarSenha, senha) &&
                telefoneValido(telefone) &&
                cepValido(cep);
    }

    public void criarConta(View view) {
        if (!camposValidos()) {
            exibirMensagem("Preencha todos os campos corretamente");
            return;
        }

        if (!checkBox.isChecked()) {
            exibirMensagem("Você deve aceitar os termos de contrato");
            return;
        }

        if (cepValido(cepEditText.getText().toString())) {
            enviarSolicitacaoAoServidor();
        } else {
            exibirMensagem("CEP inválido");
        }
    }

    private void exibirMensagem(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }

    private void enviarSolicitacaoAoServidor() {
        // Implemente a lógica para enviar a solicitação ao servidor aqui
        // Por exemplo, você pode redirecionar para a próxima atividade em caso de sucesso.
        Intent intent = new Intent(this, ConfirmaCadastro.class);
        startActivity(intent);
    }

    public void voltarTelaLogin(View view) {
        startActivity(new Intent(this, Login.class));
    }

    interface TextValidator {
        boolean isValid(String text);
    }
}
