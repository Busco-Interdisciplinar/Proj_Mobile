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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busco.Api.ApiResponse;
import com.example.busco.Api.ApiService;
import com.example.busco.Api.EndpointsMap;
import com.example.busco.Produto;
import com.example.busco.ProdutoAdapter;
import com.example.busco.R;

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

        EndpointsMap apiService = ApiService.getInstance();
        Call<ApiResponse> call = apiService.getProdutos();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null && apiResponse.isResponseSucessfull()) {
                        List<Object> objectList = apiResponse.getObject();
                        List<Produto> produtos = new ArrayList<>();
                        for (Object object : objectList) {
                            if (object instanceof Produto) {
                                Produto produto = (Produto) object;
                                String nome = produto.getNome();
                                double preco = produto.getPreco();
                                String foto = produto.getFoto();
                                produtos.add(produto);
                            }
                        }
                        produtoAdapter = new ProdutoAdapter(getActivity(), produtos);
                        listView.setAdapter(produtoAdapter);
                    }
                } else {
                    // Lidar com erros de resposta da API
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