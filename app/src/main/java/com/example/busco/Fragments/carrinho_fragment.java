package com.example.busco.Fragments;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.busco.Api.Models.Carrinho;
import com.example.busco.Api.Models.Produto;
import com.example.busco.CarrinhoAdapter;
import com.example.busco.NavigationManager.NavigationManager;
import com.example.busco.R;
import com.example.busco.SQLite.CarrinhoDAO;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class carrinho_fragment extends Fragment {
    private CarrinhoAdapter carrinhoAdapter;
    TextView subtotal, total, desconto;
    Map<String, String> listaCupons = new HashMap<>();
    double descontoD = 0;

    ListView listView;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.carrinho_fragment, container, false);
        List<Carrinho> listaCarrinho;
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO(getContext());
        try {
            listaCarrinho = carrinhoDAO.listar();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        //Cupons mockados
        listaCupons.put("Brócolis", "Brócolis35");
        listaCupons.put("Morango", "Morango40");
        listaCupons.put("Abacaxi", "abacaxi30");
        listaCupons.put("Batata", "Batata10");
        listaCupons.put("Laranja", "Laranja20");
        listaCupons.put("Abacate", "Abacate15");

        listView = view.findViewById(R.id.list);
        carrinhoAdapter = new CarrinhoAdapter(getContext(), listaCarrinho);
        listView.setAdapter(carrinhoAdapter);

        TextView textViewCarrinhoVazio = view.findViewById(R.id.textViewCarrinhoVazio);
        ImageView carrinhoVazio = view.findViewById(R.id.imageView);
        Button adicionar = view.findViewById(R.id.adicionar);
        Button agendar = view.findViewById(R.id.agendar);
        TextView produtos = view.findViewById(R.id.produtos_list);
        TextView temCupom = view.findViewById(R.id.textView);
        EditText cupom1 = view.findViewById(R.id.editTextText);
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
            cupom1.setVisibility(View.GONE);
            subtotalCarrinho.setVisibility(View.GONE);
            descontoCarrinho.setVisibility(View.GONE);
            totalCarrinho.setVisibility(View.GONE);
            subtotalValor.setVisibility(View.GONE);
            descontoValor.setVisibility(View.GONE);
            totalValor.setVisibility(View.GONE);

            adicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavigationManager navigationManager = new NavigationManager(requireActivity().getSupportFragmentManager());

                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new produtos_fragment())
                            .commit();
                }
            });
        } else {
            textViewCarrinhoVazio.setVisibility(View.GONE);
            carrinhoVazio.setVisibility(View.GONE);
            adicionar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            agendar.setVisibility(View.VISIBLE);
            produtos.setVisibility(View.VISIBLE);
            temCupom.setVisibility(View.VISIBLE);
            cupom1.setVisibility(View.VISIBLE);
            subtotalCarrinho.setVisibility(View.VISIBLE);
            descontoCarrinho.setVisibility(View.VISIBLE);
            totalCarrinho.setVisibility(View.VISIBLE);
            subtotalValor.setVisibility(View.VISIBLE);
            descontoValor.setVisibility(View.VISIBLE);
            totalValor.setVisibility(View.VISIBLE);

            agendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Produtos agendados com sucesso. Obrigado!", Toast.LENGTH_LONG).show();
                }
            });

            cupom1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String cupom = s.toString();
                    if (listaCupons.containsValue(cupom)){
                        String produtoName = cupom.substring(0, cupom.length() - 2);
                        for (Carrinho carrinho : listaCarrinho){
                            if (carrinho.getNome().equals(produtoName)){
                                carrinho.setCupom(cupom);
                                carrinhoDAO.atualizarCupomPorNome(carrinho.getNome(), cupom);
                                carrinhoAdapter.notifyDataSetChanged();
                                cupom1.setText("");
                                Toast.makeText(getContext(), "Cupom adicionado com sucesso para " + carrinho.getNome(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        subtotal = view.findViewById(R.id.subtotal);
        total = view.findViewById(R.id.valorTotal);
        desconto = view.findViewById(R.id.valorDesconto);
        double subtotalDouble = 0;
        double descontoD = 0;
        for (Carrinho carrinho : listaCarrinho){
            subtotalDouble += (carrinho.getPreco() * carrinho.getQuantidade());

            String cupom2 = carrinho.getCupom();
            if (cupom2 != null){
                String ultimosDois = cupom2.length() >= 2 ? cupom2.substring(cupom2.length() - 2) : cupom2;
                Double porcentagemDesconto = Double.parseDouble(ultimosDois) / 100;
                descontoD += (carrinho.getPreco() * carrinho.getQuantidade()) * porcentagemDesconto;
                descontoD = Math.round(descontoD);
            }

        }
        subtotal.setText("R$ " + subtotalDouble);
        desconto.setText("R$ " + descontoD);
        total.setText("R$ " + (subtotalDouble - descontoD));

        carrinhoAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                double subtotalDouble = 0;
                double descontoD = 0;
                for (Carrinho carrinho : listaCarrinho){
                    subtotalDouble += (carrinho.getPreco() * carrinho.getQuantidade());

                    String cupom3 = carrinho.getCupom();
                    if (cupom3 != null){
                        String ultimosDois = cupom3.length() >= 2 ? cupom3.substring(cupom3.length() - 2) : cupom3;
                        Double porcentagemDesconto = Double.parseDouble(ultimosDois) / 100;
                        descontoD += (carrinho.getPreco() * carrinho.getQuantidade()) * porcentagemDesconto;
                        descontoD = Math.round(descontoD);
                    }

                }
                subtotal.setText("R$ " + subtotalDouble);
                desconto.setText("R$ " + descontoD);
                total.setText("R$ " + (subtotalDouble - descontoD));
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