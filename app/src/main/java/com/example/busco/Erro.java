package com.example.busco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class Erro extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erro);
    }

    public void sair(View view) {
        finishAffinity();
    }

    public void tentarNovamente(View view) {
        if (isNetworkAvailable()) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            //finish();
        } else {
            Toast.makeText(this, "Falha ao reconectar. Verifique sua conexão com a internet.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static class Ticket {
        private String nome;
        private double preco;
        private String foto;

        public Ticket(String nome, double preco, String foto) {
            this.nome = nome;
            this.preco = preco;
            this.foto = foto;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public double getPreco() {
            return preco;
        }

        public void setPreco(double preco) {
            this.preco = preco;
        }


        public String getFoto() {
            return foto;
        }

        public void setFoto(String foto) {
            this.foto = foto;
        }

        public static class TicketAdapter extends ArrayAdapter<Ticket> {
            public TicketAdapter(Context context, List<Ticket> tickets) {
                super(context, 0, tickets);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Ticket ticket = getItem(position);

                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_tickets, parent, false);
                }

                TextView nome = convertView.findViewById(R.id.nome_ticket);
                TextView preco = convertView.findViewById(R.id.preco_ticket);
                ImageView foto = convertView.findViewById(R.id.imagem_ticket);

                nome.setText(ticket.getNome());
                preco.setText("R$ " + ticket.getPreco());

                Glide.with(getContext())
                        .load(ticket.getFoto())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(foto);

                return convertView;
            }
        }
    }
}