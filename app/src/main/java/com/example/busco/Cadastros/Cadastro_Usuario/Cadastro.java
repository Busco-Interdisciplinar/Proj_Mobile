package com.example.busco.Cadastros.Cadastro_Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.busco.Api.ApiResponse;
//import com.example.busco.Api.ApiService;
import com.example.busco.R;

import javax.security.auth.callback.Callback;

//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;

public class Cadastro extends AppCompatActivity {

    private EditText nomeEditText, emailEditText, idadeEditText, senhaEditText, confirmarSenhaEditText, telefoneEditText;
    private ImageView checkIconNome, checkIconEmail, checkIconIdade, checkIconSenha, checkIconConfirmarSenha, checkIconTelefone;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nomeEditText = findViewById(R.id.editTextTextPersonName);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        idadeEditText = findViewById(R.id.editTextNumber);
        senhaEditText = findViewById(R.id.editTextTextPassword2);
        confirmarSenhaEditText = findViewById(R.id.editTextTextPassword3);
        telefoneEditText = findViewById(R.id.editTextTelefone);
        checkBox = findViewById(R.id.checkBox);

        checkIconTelefone = findViewById(R.id.checkIconTelefone);
        checkIconNome = findViewById(R.id.checkIconNome);
        checkIconEmail = findViewById(R.id.checkIconEmail);
        checkIconIdade = findViewById(R.id.checkIconIdade);
        checkIconSenha = findViewById(R.id.checkIconSenha);
        checkIconConfirmarSenha = findViewById(R.id.checkIconConfirmarSenha);

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

        idadeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String idade = editable.toString();
                if (idadeValida(idade)) {
                    checkIconIdade.setImageResource(R.drawable.check_verde);
                    checkIconIdade.setVisibility(View.VISIBLE);
                } else {
                    checkIconIdade.setImageResource(R.drawable.check_cinza);
                    checkIconIdade.setVisibility(View.VISIBLE);
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

        confirmarSenhaEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String confirmarSenha = editable.toString();
                String senha = senhaEditText.getText().toString();
                if (confirmacaoSenha(confirmarSenha, senha)) {
                    checkIconConfirmarSenha.setImageResource(R.drawable.check_verde);
                    checkIconConfirmarSenha.setVisibility(View.VISIBLE);
                } else {
                    checkIconConfirmarSenha.setImageResource(R.drawable.check_cinza);
                    checkIconConfirmarSenha.setVisibility(View.VISIBLE);
                }
            }
        });

        telefoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String telefone = editable.toString();
                if (telefoneValido(telefone)) {
                    checkIconTelefone.setImageResource(R.drawable.check_verde);
                    checkIconTelefone.setVisibility(View.VISIBLE);
                } else {
                    checkIconTelefone.setImageResource(R.drawable.check_cinza);
                    checkIconTelefone.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean telefoneValido(String telefone){
        String numeroLimpo = telefone.replaceAll("[^0-9]", "");

        if (numeroLimpo.length() == 11 || numeroLimpo.length() == 10) {
            return true;
        }
        return false;
    }

    private boolean nomeValido(String nome) {
        return !nome.isEmpty() && nome.matches("[a-zA-Z ]+");
    }

    private boolean emailValido(String email) {
        return email.contains("@gmail.com");
    }

    private boolean idadeValida(String idade) {
        try {
            int idadeInt = Integer.parseInt(idade);
            return idadeInt >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean senhaValida(String senha) {
        return senha.length() >= 8;
    }

    private boolean confirmacaoSenha(String confirmarSenha, String senha) {
        return confirmarSenha.equals(senha);
    }

//    public void criarConta(View view) {
//        if (!camposValidos()) {
//            Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (!checkBox.isChecked()) {
//            Toast.makeText(this, "Você deve aceitar os termos de contrato", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if(camposValidos() && checkBox.isChecked()){
//
//            String numero = telefoneEditText.toString();
//            ApiService.getInstance().enviarSms(numero).enqueue(new Callback<ApiResponse>() {
//                @Override
//                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                    System.out.println(response.body().getDescription());
//                    System.out.println(response.body().getAditionalInformation());
//                }
//
//                @Override
//                public void onFailure(Call<ApiResponse> call, Throwable t) {
//                    System.out.println(t);
//                }
//            });
//
//            Intent intent = new Intent(this, ConfirmaCadastro.class);
//            startActivity(intent);
//        }
//    }

    private boolean camposValidos() {
        String nome = nomeEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String idade = idadeEditText.getText().toString();
        String senha = senhaEditText.getText().toString();
        String confirmarSenha = confirmarSenhaEditText.getText().toString();
        String telefone = telefoneEditText.getText().toString();

        return nomeValido(nome) &&
               emailValido(email) &&
               idadeValida(idade) &&
               senhaValida(senha) &&
               confirmacaoSenha(confirmarSenha, senha) &&
               telefoneValido(telefone);
    }
}