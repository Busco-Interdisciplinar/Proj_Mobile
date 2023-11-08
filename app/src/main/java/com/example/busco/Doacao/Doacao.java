package com.example.busco.Doacao;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busco.Api.ApiResponse;
import com.example.busco.Api.ApiService;
import com.example.busco.Api.Models.Instituicao;
import com.example.busco.Api.Models.Produto;
import com.example.busco.Api.Models.Usuarios;
import com.example.busco.Fragments.perfil_fragment;
import com.example.busco.R;
import com.google.gson.Gson;

import android.app.DatePickerDialog;
import android.text.format.DateFormat;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Doacao extends AppCompatActivity {

    private EditText quantidadeKilos, dataDoacao;
    private ImageView checkIconProdutoDoacao, checkIconQuantidadeKilos;
    private CheckBox checkBoxAceite;
    private EditText editTextDataDoacao;
    private List<Produto> listaProdutos1 = new ArrayList<>();
    private String[] produtosNames;
    private String produtoEscolhido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doacao);

        ApiService.getInstance().listarProdutos().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().isResponseSucessfull()) {
                        List<Object> objectList1 = response.body().getObject();
                        List<String> produtosNomes = new ArrayList<>();
                        Gson gson = new Gson();
                        for (Object object : objectList1) {
                            String produtoString = gson.toJson(object);
                            Produto produto = gson.fromJson(produtoString, Produto.class);
                            produtosNomes.add(produto.getNome());
                            listaProdutos1.add(produto);
                        }
                        Collections.sort(produtosNomes);
                        produtosNames = produtosNomes.toArray(new String[0]);
                        Spinner produtoDoacao = findViewById(R.id.produtosSpinner);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, produtosNames);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        produtoDoacao.setAdapter(adapter);
                        produtoDoacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                produtoEscolhido = produtosNames[position];
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                Toast.makeText(getApplicationContext(), "Nenhum item selecionado", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });

        quantidadeKilos = findViewById(R.id.editTextQuantidadeKilos);
        dataDoacao = findViewById(R.id.editTextDataDoacao);


        checkIconQuantidadeKilos = findViewById(R.id.checkIconQuantidadeKilos);
        checkBoxAceite = findViewById(R.id.checkBoxAceite);

        editTextDataDoacao = findViewById(R.id.editTextDataDoacao);

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

    private boolean camposValidos() {
        String kg = quantidadeKilos.getText().toString();

        return quantidadeValida(kg);
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
                    editTextDataDoacao.setText(DateFormat.format("yyyy/MM/dd", calendar));
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
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000));
        datePickerDialog.show();
    }

    public void confirmar(View view){
        if (!camposValidos()) {
            Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!checkBoxAceite.isChecked()) {
            Toast.makeText(this, "Você deve aceitar os termos de contrato", Toast.LENGTH_SHORT).show();
            return;
        }

        if (camposValidos() && checkBoxAceite.isChecked()) {
            Gson gson = new Gson();
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            String json = sharedPreferences.getString("user", "");
            Usuarios usuario = gson.fromJson(json, Usuarios.class);

            EditText quantidadeEditText = findViewById(R.id.editTextQuantidadeKilos);
            int quantidade = Integer.parseInt(quantidadeEditText.getText().toString());

            EditText dataDoacaoEditText = findViewById(R.id.editTextDataDoacao);
            String dataDoacaoString = dataDoacaoEditText.getText().toString().replace("/", "-");
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date dataDoacaoFormatada = null;
            try {
                dataDoacaoFormatada = formato.parse(dataDoacaoString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date finalDataDoacaoFormatada = dataDoacaoFormatada;
            ApiService.getInstance().buscarInstituicaoByUserId(usuario.getId()).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().isResponseSucessfull()) {
                            List<Object> listaObjetos = response.body().getObject();
                            String instituicaoJson = gson.toJson(listaObjetos.get(0));
                            Instituicao instituicao = gson.fromJson(instituicaoJson, Instituicao.class);
                            Produto produtoSelecionado = null;
                            for (Produto produto : listaProdutos1){
                                if (produto.getNome().equals(produtoEscolhido)){
                                    produtoSelecionado = produto;
                                    break;
                                }
                            }
                            com.example.busco.Api.Models.Doacao doacao = new com.example.busco.Api.Models.Doacao(finalDataDoacaoFormatada, "Doação de produtos", produtoSelecionado.getId(), usuario.getId(), instituicao.getId(),quantidade);
                            ApiService.getInstance().fazerDoacao(doacao).enqueue(new Callback<ApiResponse>() {
                                @Override
                                public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                                    if (response.isSuccessful()){
                                        if (response.body() != null && response.body().isResponseSucessfull()){
                                            Intent intent = new Intent(getApplicationContext(), DoacaoEfetuada.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Falahao realizar doação", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {

                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {

                }
            });
        }
    }

    public void voltar(View view) {
        finish();
    }
}