package com.example.busco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.busco.Api.ApiResponse;
import com.example.busco.Api.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmaCadastro extends AppCompatActivity {
    private EditText digito1, digito2, digito3, digito4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma_cadastro);
    }

    public void confirmar(View view) {
        digito1 = findViewById(R.id.editTextCodigo1);
        digito2 = findViewById(R.id.editTextCodigo2);
        digito3 = findViewById(R.id.editTextCodigo3);
        digito4 = findViewById(R.id.editTextCodigo4);

        String codigoCompleto = (digito1.toString() +digito2.toString() + digito3.toString() + digito4.toString());
    }
}