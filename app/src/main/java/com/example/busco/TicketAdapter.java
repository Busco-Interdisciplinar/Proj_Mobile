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

        ImageView imageViewProduto = convertView.findViewById(R.id.foto_promo);
        TextView nomeProduto = convertView.findViewById(R.id.oferta_promocao);
        TextView precoProduto = convertView.findViewById(R.id.textView9);
        Button usarPromocao = convertView.findViewById(R.id.usar_promocaoAbacaxi);

        Glide.with(getContext())
                .load(ticket.getFotoResource())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageViewProduto);

        nomeProduto.setText(ticket.getNome());
        precoProduto.setText(ticket.getPreco());

        Glide.with(getContext())
                .load(ticket.getFotoProdutoPromoResource())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);

        usarPromocao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ação a ser executada ao clicar no botão de usar promoção
            }
        });

        return convertView;
    }
}