package com.example.busco;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import java.util.List;

public class TicketAdapter extends ArrayAdapter<Ticket> {

    public TicketAdapter(Context context, List<Ticket> tickets) {
        super(context, 0, tickets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ticket ticket = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_ticket, parent, false);
        }

        TextView nome = convertView.findViewById(R.id.nome_produto);
        TextView preco = convertView.findViewById(R.id.preco_produto);
        ImageView foto = convertView.findViewById(R.id.img);

        nome.setText(ticket.getNome());
        preco.setText("R$ " + ticket.getPreco());

        // Use o Glide ou outra biblioteca para carregar a imagem do ticket
        // Exemplo com o Glide:
        Glide.with(getContext())
                .load(ticket.getFotoUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(foto);

        // Configurar o clique do botão (ou outros elementos, conforme necessário)
        Button usarCupom = convertView.findViewById(R.id.botao2);
        usarCupom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lide com o clique no botão de uso do cupom aqui
            }
        });

        return convertView;
    }
}