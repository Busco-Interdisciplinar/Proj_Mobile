package com.example.busco.Cadastros.Cadastro_Usuario;

import android.Manifest;
import android.app.PendingIntent;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.busco.Api.ApiResponse;
import com.example.busco.Api.ApiService;
import com.example.busco.Api.Models.Usuarios;
import com.example.busco.Login;
import com.example.busco.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cadastro extends AppCompatActivity {

    private EditText nomeEditText, emailEditText, CpfEditText, senhaEditText, confirmarSenhaEditText, telefoneEditText, cepEditText;
    private ImageView checkIconNome, checkIconEmail, checkIconCpf, checkIconSenha, checkIconConfirmarSenha, checkIconTelefone, checkIconCep;
    private CheckBox checkBox;
    private Button buttonCriarConta;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Inicialização de elementos
        nomeEditText = findViewById(R.id.editTextTextPersonName);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        CpfEditText = findViewById(R.id.editTextNumber);
        senhaEditText = findViewById(R.id.editTextTextPassword2);
        confirmarSenhaEditText = findViewById(R.id.editTextTextPassword3);
        telefoneEditText = findViewById(R.id.editTextTelefone);
        cepEditText = findViewById(R.id.editTextCep);
        checkBox = findViewById(R.id.checkBox);
        buttonCriarConta = findViewById(R.id.buttonCriarConta);

        checkIconTelefone = findViewById(R.id.checkIconTelefone);
        checkIconNome = findViewById(R.id.checkIconNome);
        checkIconEmail = findViewById(R.id.checkIconEmail);
        checkIconCpf = findViewById(R.id.checkIconIdade);
        checkIconSenha = findViewById(R.id.checkIconSenha);
        checkIconCep = findViewById(R.id.checkIconCep);
        checkIconConfirmarSenha = findViewById(R.id.checkIconConfirmarSenha);

        setupTextWatchers();
        verificarEstadoBotao();
    }



    private void setupTextWatchers() {
        nomeEditText.addTextChangedListener(createTextWatcher(checkIconNome, this::nomeValido));
        emailEditText.addTextChangedListener(createTextWatcher(checkIconEmail, this::emailValido));
        CpfEditText.addTextChangedListener(createTextWatcher(checkIconCpf, this::cpfValido));
        senhaEditText.addTextChangedListener(createTextWatcher(checkIconSenha, this::senhaValida));
        confirmarSenhaEditText.addTextChangedListener(createTextWatcher(checkIconConfirmarSenha, text -> confirmacaoSenha(text, senhaEditText.getText().toString())));
        telefoneEditText.addTextChangedListener(createTextWatcher(checkIconTelefone, this::telefoneValido));
        cepEditText.addTextChangedListener(createTextWatcher(checkIconCep, this::cepValido));

        // Adicione um lisztener ao CheckBox para acompanhar as mudanças
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
                    checkIcon.setTag(null); // Remove a marca da ImageView
                } else if (validator.isValid(text)) {
                    checkIcon.setImageResource(R.drawable.check_verde);
                    checkIcon.setTag("verde"); // Marca a ImageView como verde
                } else {
                    checkIcon.setImageResource(R.drawable.check_cinza);
                    checkIcon.setTag(null); // Remove a marca da ImageView
                }

                verificarEstadoBotao(); // Verifica o estado do botão
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
        String cpf = CpfEditText.getText().toString();
        String senha = senhaEditText.getText().toString();
        String confirmarSenha = confirmarSenhaEditText.getText().toString();
        String telefone = telefoneEditText.getText().toString();

        return !nome.isEmpty() && !email.isEmpty() && !cpf.isEmpty() && !senha.isEmpty() && !confirmarSenha.isEmpty() && !telefone.isEmpty();
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
                && checkIconTelefone.getTag() != null;
    }

    private boolean nomeValido(String nome) {
        // Validação de nome: Deve conter pelo menos 3 caracteres.
        return nome.length() >= 3;
    }

    private boolean emailValido(String email) {
        if (email == null) {
            return false;
        }

        email = email.trim(); // removendo espaços em branco do email

        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false; // Deve conter exatamente um "@"
        }

        String username = parts[0];
        String domain = parts[1];

        if (username.isEmpty() || domain.isEmpty()) {
            return false; // Parte do nome de usuário ou do domínio não pode ser vazia
        }

        if (domain.startsWith(".") || domain.endsWith(".") || domain.contains("..") || username.contains("..")) {
            return false; // Não pode começar ou terminar com ".", conter dois pontos seguidos em nenhum lugar
        }

        String[] domainParts = domain.split("\\.");
        if (domainParts.length < 2) {
            return false; // Deve conter pelo menos um ponto no domínio
        }

        String topLevelDomain = domainParts[domainParts.length - 1];
        if (!topLevelDomain.equals("com") && !topLevelDomain.equals("br") && !topLevelDomain.equals("org")) {
            return false; // O domínio deve ser .com, .com.br, .org, .org.br, ou outro válido
        }

        return true;
    }

    private boolean cpfValido(String cpf) {
        // Remove quaisquer caracteres não numéricos do CPF
        cpf = cpf.replaceAll("[^0-9]", "");

        // CPF deve ter 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Validação básica do CPF
        int[] digitos = new int[11];
        for (int i = 0; i < 11; i++) {
            digitos[i] = Integer.parseInt(String.valueOf(cpf.charAt(i)));
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += digitos[i] * (10 - i);
        }
        int resto = soma % 11;
        int digitoVerificador1 = (resto < 2) ? 0 : (11 - resto);

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += digitos[i] * (11 - i);
        }
        resto = soma % 11;
        int digitoVerificador2 = (resto < 2) ? 0 : (11 - resto);

        return digitos[9] == digitoVerificador1 && digitos[10] == digitoVerificador2;
    }

    private boolean senhaValida(String senha) {
        // Validação de senha: Deve conter pelo menos 6 caracteres.
        return senha.length() >= 6;
    }

    private boolean confirmacaoSenha(String confirmarSenha, String senha) {
        // Validação de confirmação de senha: Deve ser igual à senha.
        return confirmarSenha.equals(senha);
    }

    private boolean telefoneValido(String telefone) {
        // Validação de telefone: Deve conter pelo menos 10 dígitos.
        return telefone.length() >= 10;
    }

    private boolean cepValido(String cep) {
        // Remove quaisquer caracteres não numéricos do CEP
        cep = cep.replaceAll("[^0-9]", "");

        // CEP deve ter 8 dígitos
        return cep.length() == 8;
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

        if(camposValidos() && checkBox.isChecked()){

            String numero = "+55" + telefoneEditText.getText().toString().trim();
            String nome =  nomeEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String senha = senhaEditText.getText().toString().trim();
            String cep =  cepEditText.getText().toString().trim();
            String cpf = CpfEditText.getText().toString().trim();
            String numeroSemCodigo = numero.replace("+55", "");

            ApiService.getInstance().buscarCpfEmailTelefone(cpf, email, numeroSemCodigo).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null && response.body().isResponseSucessfull()){
//                            List<Object> usuarioObject = response.body().getObject();
//                            String objetoJson = gson.toJson(usuarioObject.get(0));
//                            objetoJson = objetoJson.substring(1, objetoJson.length() - 1);
//                            Usuarios usuarioExistente = gson.fromJson(objetoJson, Usuarios.class);
                            try {
                                JSONObject jsonString = new JSONObject(response.body().getAditionalInformation());
                                Boolean cpf= (Boolean) jsonString.get("cpf");
                                Boolean email = (Boolean) jsonString .get("email");
                                Boolean telefone = (Boolean) jsonString .get("telefone");

                                int focus = 0;
                                String mensagem = "";
                                if (cpf){
                                    mensagem += "\n \r CPF já cadastrado no banco.";
                                    focus = 2;
                                }
                                if (email){
                                    mensagem += "\n \r Email já cadastrado no banco.";
                                    if(focus == 0){
                                        focus = 1;
                                    }
                                }

                                if (telefone){
                                    mensagem += "\n \r Telefone já cadastrado no banco.";
                                    if(focus != 2 && focus != 1){
                                        focus = 3;
                                    }
                                }

                                if (focus == 1){
                                    emailEditText.requestFocus();
                                } else if (focus == 2) {
                                    CpfEditText.requestFocus();
                                }else{
                                    telefoneEditText.requestFocus();
                                }
                                if (!mensagem.equals("")){
                                    Toast.makeText(getApplicationContext(),"Foram encontrados os seguintes dados já cadastrados no banco: \n \r" + mensagem, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }else{
                        Random random = new Random();
                        int codigo = random.nextInt(10000);
                        String codigoFormatado = String.format("%04d", codigo);
                        String mensagem = "Seu código de verificação da Busco é " + codigoFormatado;
                        mensagem = "Verificação Busco: " + codigoFormatado;
                        Usuarios usuario = new Usuarios(email, senha, cep, nome, cpf, numero);
                        Intent intentSMS = new Intent(getApplicationContext(),ConfirmaCadastro.class);
                        Bundle bundle = new Bundle();
                        Gson gson = new Gson();
                        String usuarioJson = gson.toJson(usuario);
                        bundle.putString("codigoFormatado", codigoFormatado);
                        bundle.putString("usuario", usuarioJson);
                        intentSMS.putExtras(bundle);

                        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 1, intentSMS,PendingIntent.FLAG_IMMUTABLE);
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(numero, null, mensagem, pi,null);
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 3000);
                        setIntent(new Intent());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    private boolean camposValidos() {
        String nome = nomeEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String cpf = CpfEditText.getText().toString();
        String senha = senhaEditText.getText().toString();
        String confirmarSenha = confirmarSenhaEditText.getText().toString();
        String telefone = telefoneEditText.getText().toString();

        return nomeValido(nome) &&
                emailValido(email) &&
                cpfValido(cpf) &&
                senhaValida(senha) &&
                confirmacaoSenha(confirmarSenha, senha) &&
                telefoneValido(telefone);
    }

    private void exibirMensagem(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }

    private void enviarSolicitacaoAoServidor() {
        // Coloque aqui a lógica de envio da solicitação ao servidor
        // Por exemplo, se a solicitação for bem-sucedida, você pode redirecionar para a próxima atividade.
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