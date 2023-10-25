package com.example.busco.Doacao;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busco.R;

public class Doacao extends AppCompatActivity {

    private EditText produtoDoacao, quantidadeKilos, dataDoacao;
    private ImageView checkIconProdutoDoacao, checkIconQuantidadeKilos, checkIconDataDoacao;
    private CheckBox checkBoxAceite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doacao);

        produtoDoacao = findViewById(R.id.editTextProdutoDoacao);
        quantidadeKilos = findViewById(R.id.editTextQuantidadeKilos);
        dataDoacao = findViewById(R.id.editTextDataDoacao);

        checkIconProdutoDoacao = findViewById(R.id.checkIconProdutoDoacao);
        checkIconQuantidadeKilos = findViewById(R.id.checkIconQuantidadeKilos);
        checkIconDataDoacao = findViewById(R.id.checkIconDataDoacao);

        checkBoxAceite = findViewById(R.id.checkBoxAceite);

        produtoDoacao.addTextChangedListener(new TextWatcher() {
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
                    checkIconProdutoDoacao.setImageResource(R.drawable.check_verde);
                    checkIconProdutoDoacao.setVisibility(View.VISIBLE);
                } else {
                    checkIconProdutoDoacao.setImageResource(R.drawable.check_cinza);
                    checkIconProdutoDoacao.setVisibility(View.VISIBLE);
                }
            }
        });

        quantidadeKilos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String kg = editable.toString();
                if (quantidadeValida(kg)) {
                    checkIconQuantidadeKilos.setImageResource(R.drawable.check_verde);
                    checkIconQuantidadeKilos.setVisibility(View.VISIBLE);
                } else {
                    checkIconQuantidadeKilos.setImageResource(R.drawable.check_cinza);
                    checkIconQuantidadeKilos.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean quantidadeValida(String kg) {
        if (kg.isEmpty()) {
            return false;
        }
        try {
            double quantidade = Double.parseDouble(kg);
            if (quantidade > 0 && quantidade <= 1000) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean produtoValido(String nome) {
        return !nome.isEmpty() && nome.matches("[a-zA-Z ]+");
    }

    public void criarDoacao(View view) {
        if (!camposValidos()) {
            Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!checkBoxAceite.isChecked()) {
            Toast.makeText(this, "Você deve aceitar os termos de contrato", Toast.LENGTH_SHORT).show();
            return;
        }

        if (camposValidos() && checkBoxAceite.isChecked()) {
            Intent intent = new Intent(this, DoacaoEfetuada.class);
            startActivity(intent);
        }
    }

    private boolean camposValidos() {
        String produto = produtoDoacao.getText().toString();
        String kg = quantidadeKilos.getText().toString();

        return produtoValido(produto) &&
                quantidadeValida(kg);
    }
}
