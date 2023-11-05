package com.example.busco.Cadastros.Cadastro_Usuario;

import android.app.PendingIntent;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
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
        buttonCriarConta = findViewById(R.id.btnConfirmar);

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

        cpfEditText.addTextChangedListener(createMask(cpfEditText, "###.###.###-##"));
        cepEditText.addTextChangedListener(createMask(cepEditText, "#####-###"));
        telefoneEditText.addTextChangedListener(createMask(telefoneEditText, "(##) #####-####"));

        cpfEditText.addTextChangedListener(createTextWatcher(checkIconCpf, this::cpfValido));
        cepEditText.addTextChangedListener(createTextWatcher(checkIconCep, this::cepValido));
        telefoneEditText.addTextChangedListener(createTextWatcher(checkIconTelefone, this::telefoneValido));
        senhaEditText.addTextChangedListener(createTextWatcher(checkIconSenha, this::senhaValida));
        confirmarSenhaEditText.addTextChangedListener(createTextWatcher(checkIconConfirmarSenha, text -> confirmacaoSenha(text, senhaEditText.getText().toString())));
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
        String cpf = cpfEditText.getText().toString();
        String senha = senhaEditText.getText().toString();
        String confirmarSenha = confirmarSenhaEditText.getText().toString();
        String telefone = telefoneEditText.getText().toString();
        String cep = cepEditText.getText().toString();

        return !nome.isEmpty() && !email.isEmpty() && !cpf.isEmpty() && !senha.isEmpty() && !confirmarSenha.isEmpty() && !telefone.isEmpty() && !cep.isEmpty();
    }

    private void habilitarBotaoVerde() {
        buttonCriarConta.setBackgroundResource(R.drawable.button_backgroud_on);
    }

    private void desabilitarBotaoCinza() {
        buttonCriarConta.setBackgroundResource(R.drawable.button_background);
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
        return nome.trim().matches("^[a-zA-ZÀ-ÖØ-öø-ÿĀ-ž]+$");
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

            String numero = "+55" + telefoneEditText.getText().toString();
            String nome =  nomeEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String senha = senhaEditText.getText().toString().trim();
            String cep =  cepEditText.getText().toString().trim().replace("-", "");
            String cpf = cpfEditText.getText().toString().trim().replace(".", "").replace("-", "");
            String numeroSemCodigo = numero.replace("+55", "").replace("(", "").replace(")", "").replace("-", "").replace(" ", "");

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
                                Boolean cpf = (Boolean) jsonString.get("cpf");
                                Boolean email = (Boolean) jsonString.get("email");
                                Boolean telefone = (Boolean) jsonString.get("telefone");

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
                                    cpfEditText.requestFocus();
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
                        try {
                            sms.sendTextMessage(numero, null, mensagem, pi,null);
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Não foi possível enviar o SMS, verifica a conectividade\n" + e , Toast.LENGTH_LONG).show();
                        }
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 3000);
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
        String cpf = cpfEditText.getText().toString();
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

    private boolean cpfValido(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return false;
        }

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

    public void voltarTelaLogin(View view) {
        startActivity(new Intent(this, Login.class));
    }

    interface TextValidator {
        boolean isValid(String text);
    }
}