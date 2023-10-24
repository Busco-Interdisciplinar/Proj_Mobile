package com.example.busco;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Produto> productList;

    public ProductAdapter(List<Produto> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produtos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto produto = productList.get(position);

        holder.foto.setImageResource(produto.getFoto());
        holder.nome.setText(produto.getNome());
        holder.preco.setText("R$ " + produto.getPreco());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView foto;
        public TextView nome;
        public TextView preco;

        public ViewHolder(View view) {
            super(view);
            foto = view.findViewById(R.id.foto_produto);
            nome = view.findViewById(R.id.nome);
            preco = view.findViewById(R.id.preco);
        }
    }
}
