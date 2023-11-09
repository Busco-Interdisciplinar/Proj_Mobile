package com.example.busco.Fragments;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
