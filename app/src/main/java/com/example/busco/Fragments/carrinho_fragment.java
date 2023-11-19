package com.example.busco.Fragments;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.busco.Api.Models.Carrinho;
import com.example.busco.CarrinhoAdapter;
import com.example.busco.R;
import com.example.busco.SQLite.CarrinhoDAO;

import java.text.ParseException;
import java.util.List;

public class carrinho_fragment extends Fragment {
    private CarrinhoAdapter carrinhoAdapter;
    TextView subtotal, total, desconto;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.carrinho_fragment, container, false);
        List<Carrinho> listaCarrinho;
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO(getContext());
        try {
            listaCarrinho = carrinhoDAO.listar();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        ListView listView = view.findViewById(R.id.list);
        carrinhoAdapter = new CarrinhoAdapter(getContext(), listaCarrinho);
        listView.setAdapter(carrinhoAdapter);

        TextView textViewCarrinhoVazio = view.findViewById(R.id.textViewCarrinhoVazio);
        ImageView carrinhoVazio = view.findViewById(R.id.imageView);
        Button adicionar = view.findViewById(R.id.adicionar);
        Button agendar = view.findViewById(R.id.agendar);
        TextView produtos = view.findViewById(R.id.produtos_list);
        TextView temCupom = view.findViewById(R.id.textView);
        EditText cupom = view.findViewById(R.id.editTextText);
        TextView subtotalCarrinho = view.findViewById(R.id.subtotaltext);
        TextView descontoCarrinho = view.findViewById(R.id.descontoText);
        TextView totalCarrinho = view.findViewById(R.id.total);
        TextView subtotalValor = view.findViewById(R.id.subtotal);
        TextView descontoValor = view.findViewById(R.id.valorDesconto);
        TextView totalValor = view.findViewById(R.id.valorTotal);

        if (listaCarrinho.isEmpty()) {
            textViewCarrinhoVazio.setVisibility(View.VISIBLE);
            carrinhoVazio.setVisibility(View.VISIBLE);
            adicionar.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            agendar.setVisibility(View.GONE);
            produtos.setVisibility(View.GONE);
            temCupom.setVisibility(View.GONE);
            cupom.setVisibility(View.GONE);
            subtotalCarrinho.setVisibility(View.GONE);
            descontoCarrinho.setVisibility(View.GONE);
            totalCarrinho.setVisibility(View.GONE);
            subtotalValor.setVisibility(View.GONE);
            descontoValor.setVisibility(View.GONE);
            totalValor.setVisibility(View.GONE);
        } else {
            textViewCarrinhoVazio.setVisibility(View.GONE);
            carrinhoVazio.setVisibility(View.GONE);
            adicionar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            agendar.setVisibility(View.VISIBLE);
            produtos.setVisibility(View.VISIBLE);
            temCupom.setVisibility(View.VISIBLE);
            cupom.setVisibility(View.VISIBLE);
            subtotalCarrinho.setVisibility(View.VISIBLE);
            descontoCarrinho.setVisibility(View.VISIBLE);
            totalCarrinho.setVisibility(View.VISIBLE);
            subtotalValor.setVisibility(View.VISIBLE);
            descontoValor.setVisibility(View.VISIBLE);
            totalValor.setVisibility(View.VISIBLE);
        }

        subtotal = view.findViewById(R.id.subtotal);
        total = view.findViewById(R.id.valorTotal);
        desconto = view.findViewById(R.id.valorDesconto);
        desconto.setText("R$ 00.0");
        double subtotalDouble = 0;
        for (Carrinho carrinho : listaCarrinho){
            subtotalDouble += (carrinho.getPreco() * carrinho.getQuantidade());
        }
        subtotal.setText("R$ " + subtotalDouble);
        total.setText("R$ " + subtotalDouble);

        carrinhoAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                double subtotalDouble = 0;
                for (Carrinho carrinho : listaCarrinho){
                    subtotalDouble += (carrinho.getPreco() * carrinho.getQuantidade());
                }
                subtotal.setText("R$ " + subtotalDouble);
                total.setText("R$ " + subtotalDouble);
            }
        });
        return view;
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}