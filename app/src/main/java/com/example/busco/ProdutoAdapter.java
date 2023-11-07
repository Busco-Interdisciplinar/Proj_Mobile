package com.example.busco;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.busco.Api.Models.Produto;

public class ProdutoAdapter extends ArrayAdapter<Produto> {

    public ProdutoAdapter(Context context, List<Produto> produtos) {
        super(context, 0, produtos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Produto produto = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_produtos, parent, false);
        }

        TextView nome = convertView.findViewById(R.id.nome_produto);
        TextView preco = convertView.findViewById(R.id.preco);
        ImageView foto = convertView.findViewById(R.id.foto_produto);
        ImageView carrinho = convertView.findViewById(R.id.carrinho);

        nome.setText(produto.getNome());
        preco.setText("R$ " + produto.getPreco());

        Glide.with(getContext())
                .load(produto.getFoto())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(foto);

        carrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lide com o clique no carrinho aqui
                // Você pode adicionar o produto correspondente à tela de carrinho
            }
        });
        return convertView;
    }
}