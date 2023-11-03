package com.example.busco.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.busco.Api.ApiResponse;
import com.example.busco.Api.ApiService;
import com.example.busco.Api.EndpointsMap;
import com.example.busco.Api.Models.Produto;
import com.example.busco.ProdutoAdapter;
import com.example.busco.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class produtos_fragment extends Fragment {

    private ListView listView;
    private List<Produto> produtos;
    private ProdutoAdapter produtoAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.produtos_fragment, container, false);
        listView = view.findViewById(R.id.listProdutos);

        ApiService.getInstance().listarProdutos().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().isResponseSucessfull()) {
                        List<Object> objectList1 = response.body().getObject();
                        List<Produto> produtos = new ArrayList<>();
                        Gson gson = new Gson();
                        for (Object object : objectList1) {
                                String produtoString = gson.toJson(object);
                                Produto produto = gson.fromJson(produtoString, Produto.class);
                                produtos.add(produto);
                        }
                        produtoAdapter = new ProdutoAdapter(getActivity(), produtos);
                        listView.setAdapter(produtoAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Lidar com erros de solicitação
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