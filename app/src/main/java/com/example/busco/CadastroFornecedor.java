package com.example.busco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.busco.R;

public class CadastroFornecedor extends AppCompatActivity {
    private EditText cnpj, produto;
    private ImageView checkIconProduto, checkIconCNPJ;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_fornecedor);

        produto = findViewById(R.id.produto);
        cnpj = findViewById(R.id.editTextTextCNPJ);

        checkIconProduto = findViewById(R.id.checkIconProduto);
        checkIconCNPJ = findViewById(R.id.checkIconCNPJ);

        checkBox = findViewById(R.id.checkBox);

        cnpj.addTextChangedListener(new TextWatcher() {
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

        produto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String nome = editable.toString();
                if (produtoValido(nome)) {
                    checkIconProduto.setImageResource(R.drawable.check_verde);
                    checkIconProduto.setVisibility(View.VISIBLE);
                } else {
                    checkIconProduto.setImageResource(R.drawable.check_cinza);
                    checkIconProduto.setVisibility(View.VISIBLE);
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

    private boolean produtoValido(String nome) {
        return !nome.isEmpty() && nome.matches("[a-zA-Z ]+");
    }
}