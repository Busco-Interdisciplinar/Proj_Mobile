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

import com.example.busco.Fragments.perfil_fragment;
import com.example.busco.R;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Calendar;

public class Doacao extends AppCompatActivity {

    private EditText produtoDoacao, quantidadeKilos, dataDoacao;
    private ImageView checkIconProdutoDoacao, checkIconQuantidadeKilos;
    private CheckBox checkBoxAceite;
    private EditText editTextDataDoacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doacao);

        produtoDoacao = findViewById(R.id.editTextProdutoDoacao);
        quantidadeKilos = findViewById(R.id.editTextQuantidadeKilos);
        dataDoacao = findViewById(R.id.editTextDataDoacao);

        checkIconProdutoDoacao = findViewById(R.id.checkIconProdutoDoacao);
        checkIconQuantidadeKilos = findViewById(R.id.checkIconQuantidadeKilos);
        checkBoxAceite = findViewById(R.id.checkBoxAceite);

        editTextDataDoacao = findViewById(R.id.editTextDataDoacao);

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
        return !nome.isEmpty() && nome.matches("[a-zA-ZÀ-ÖØ-öø-ÿ ]+");
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

    public void selecionarData(View view) {
        mostrarData();
    }

    private void mostrarData() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                long selectedTime = calendar.getTimeInMillis();

                Calendar umaSemana = Calendar.getInstance();
                umaSemana.add(Calendar.DATE, 7);
                long oneWeekLaterTime = umaSemana.getTimeInMillis();

                if (selectedTime > System.currentTimeMillis() && selectedTime <= oneWeekLaterTime) {
                    editTextDataDoacao.setText(DateFormat.format("dd/MM/yyyy", calendar));
                } else {
                    Toast.makeText(Doacao.this, "Selecione uma data dentro de uma semana a partir de hoje.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000)); // Uma semana em milissegundos
        datePickerDialog.show();
    }

    public void confirmar(View view) {
        Intent intent = new Intent(this, DoacaoEfetuada.class);
        startActivity(intent);
    }

    public void voltar(View view) {
        Intent intent = new Intent(this, perfil_fragment.class);
        startActivity(intent);
    }
}
