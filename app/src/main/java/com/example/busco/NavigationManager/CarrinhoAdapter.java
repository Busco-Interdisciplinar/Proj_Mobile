package com.example.busco.NavigationManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.busco.Api.Models.Carrinho;
import com.example.busco.Api.Models.Produto;
import com.example.busco.R;

import java.util.List;

public class CarrinhoAdapter extends ArrayAdapter<Carrinho> {
    public CarrinhoAdapter(Context context, List<Carrinho> carrinho) {
        super(context, 0, carrinho);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_carrinho, parent, false);
        }

        Carrinho carrinhoItem = getItem(position);

        TextView nomeProduto = convertView.findViewById(R.id.nome_produto);
        TextView precoProduto = convertView.findViewById(R.id.preco);
        ImageView fotoProduto = convertView.findViewById(R.id.foto_produto);
        TextView cupom = convertView.findViewById(R.id.cupom);

        nomeProduto.setText(carrinhoItem.getNome());
        precoProduto.setText("R$ " + carrinhoItem.getPreco());

        if (carrinhoItem.getCupom() != null) {
            cupom.setVisibility(View.VISIBLE);
            cupom.setText("Cupom: " + carrinhoItem.getCupom());
        } else {
            cupom.setVisibility(View.GONE);
        }

        TextView quantidadeProduto = convertView.findViewById(R.id.quantidade);
        Button btnDiminuir = convertView.findViewById(R.id.btnDiminuir);
        Button btnAumentar = convertView.findViewById(R.id.btnAumentar);

        int[] quantidadeAtual = { carrinhoItem.getQuantidade() };
        quantidadeProduto.setText(String.valueOf(quantidadeAtual[0]));

        btnDiminuir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantidadeAtual[0] > 1) {
                    quantidadeAtual[0]--;
                    carrinhoItem.setQuantidade(quantidadeAtual[0]);
                    notifyDataSetChanged();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Remover item");
                    builder.setMessage("Tem certeza de que deseja remover este item do carrinho?");
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            remove(carrinhoItem);
                            notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            quantidadeAtual[0] = 1;
                        }
                    });
                    builder.show();
                }
            }
        });

        btnAumentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantidadeAtual[0] < 20) {
                    quantidadeAtual[0]++;
                    carrinhoItem.setQuantidade(quantidadeAtual[0]);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Quantidade máxima atingida (20)", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView;
    }
}