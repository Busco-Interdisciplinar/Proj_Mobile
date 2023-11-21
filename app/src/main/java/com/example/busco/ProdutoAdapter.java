package com.example.busco;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.busco.Api.Models.Carrinho;
import com.example.busco.Api.Models.Produto;
import com.example.busco.SQLite.CarrinhoDAO;

public class ProdutoAdapter extends ArrayAdapter<Produto> {

    public ProdutoAdapter(Context context, List<Produto> produtos) {
        super(context, 0, produtos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Produto produto = getItem(position);

        Produto produtoClicado = getItem(position);

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
                Carrinho carrinho = new Carrinho(produtoClicado.getNome(), 1, produtoClicado.getPreco(), produtoClicado.getFoto(), null);
                CarrinhoDAO carrinhoDAO = new CarrinhoDAO(getContext());
                Carrinho produtoPesquisado = carrinhoDAO.pesquisar(carrinho.getNome());
                if (produtoPesquisado == null){
                    carrinhoDAO.salvar(carrinho);
                    Toast.makeText(getContext(), "Produto adicionado ao carrinho com sucesso", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "Produto já adicionado ao carrinho", Toast.LENGTH_LONG).show();
                }
            }
        });
        return convertView;
    }
}