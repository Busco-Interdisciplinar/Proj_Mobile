package com.example.busco.Cadastros.Cadastro_Usuario;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.busco.R;

public class ConfirmaCadastro extends AppCompatActivity {
    private EditText digito1, digito2, digito3, digito4;
    private ImageView imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma_cadastro);

        imageView3 = findViewById(R.id.imageView3); // Corrigido o erro ao encontrar a ImageView
        gif();
    }

    public void gif() {
        Glide.with(this)
                .load(R.drawable.codigo_sms) // ou a URL do GIF
                .into(imageView3);
    }

    public void confirmar(View view) {
        digito1 = findViewById(R.id.editTextCodigo1);
        digito2 = findViewById(R.id.editTextCodigo2);
        digito3 = findViewById(R.id.editTextCodigo3);
        digito4 = findViewById(R.id.editTextCodigo4);

        String codigoCompleto = digito1.getText().toString() + digito2.getText().toString() + digito3.getText().toString() + digito4.getText().toString();
    }
}
