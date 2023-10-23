package com.example.busco.Doacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.busco.R;

public class Doacao extends AppCompatActivity {

    private EditText produto, quantidade;
    private ImageView checkIconProduto, checkIconKG;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doacao);

        produto = findViewById(R.id.produto);
        quantidade = findViewById(R.id.quantidade);

        checkIconProduto = findViewById(R.id.checkIconProduto);
        checkIconKG = findViewById(R.id.checkIconKG);

        checkBox = findViewById(R.id.checkBox);


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

        quantidade.addTextChangedListener(new TextWatcher() {
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
                    checkIconKG.setImageResource(R.drawable.check_verde);
                    checkIconKG.setVisibility(View.VISIBLE);
                } else {
                    checkIconKG.setImageResource(R.drawable.check_cinza);
                    checkIconKG.setVisibility(View.VISIBLE);
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

    public void fazerDoacao(View view) {
        if (!camposValidos()) {
            Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!checkBox.isChecked()) {
            Toast.makeText(this, "Você deve aceitar os termos de contrato", Toast.LENGTH_SHORT).show();
            return;
        }

        if(camposValidos() && checkBox.isChecked()){
            Intent intent = new Intent(this, DoacaoEfetuada.class);
            startActivity(intent);
        }
    }

    private boolean camposValidos() {
        String produtoDoado = produto.getText().toString();
        String kg = quantidade.getText().toString();

        return produtoValido(produtoDoado) &&
                quantidadeValida(kg);
    }
}