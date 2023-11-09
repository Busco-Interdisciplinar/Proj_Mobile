package com.example.busco.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.busco.Api.ApiResponse;
import com.example.busco.Api.ApiService;
import com.example.busco.Api.Models.Carrinho;
import com.example.busco.Api.Models.Produto;
import com.example.busco.Firebase.Connection;
import com.example.busco.ProdutoAdapter;
import com.example.busco.R;
import com.example.busco.SQLite.CarrinhoDAO;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.gson.reflect.TypeToken;


public class  produtos_fragment extends Fragment {

    private ListView listView;
    private List<Produto> produtos = new ArrayList<>();
    private ProdutoAdapter produtoAdapter;
    private EditText searchEditText;
    private TextView naoExiste;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.produtos_fragment, container, false);
        listView = view.findViewById(R.id.listProdutos);
        searchEditText = view.findViewById(R.id.searchEditText);
        naoExiste = view.findViewById(R.id.noProductTextView);

        View loadingProgressBar = view.findViewById(R.id.loadingProgressBar);
        loadingProgressBar.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("ProductsData", Context.MODE_PRIVATE);
        String listaProdutosJson = sharedPreferences.getString("listProducts", "");

        if (listaProdutosJson.equals("")){
            ApiService.getInstance().listarProdutos().enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().isResponseSucessfull()) {
                            List<Object> objectList1 = response.body().getObject();
                            Gson gson = new Gson();
                            for (Object object : objectList1) {
                                String produtoString = gson.toJson(object);
                                Produto produto = gson.fromJson(produtoString, Produto.class);
                                String idProduto = String.valueOf(produto.getId());

                                //Buscando a imagem do produto no firebase
                                Connection connection = Connection.getInstance();
                                DatabaseReference databaseReference = connection.getDatabaseReference();
                                DatabaseReference imagemRef = databaseReference.child("produtos_images").child(idProduto);

                                imagemRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String url = snapshot.child("url").getValue(String.class);
                                        if (url != null){
                                            produto.setFoto(url);
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                                    }
                                });
                                produtos.add(produto);
                            }

                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    produtos.sort(Comparator.comparing(Produto::getNome));
                                    produtoAdapter = new ProdutoAdapter(getActivity(), produtos);
                                    listView.setAdapter(produtoAdapter);
                                    produtoAdapter.notifyDataSetChanged();

                                    String listaProdutosJson = gson.toJson(produtos);
                                    SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("ProductsData", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("listProducts", listaProdutosJson);
                                    editor.apply();
                                }
                            }, 1500);


                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("ProductsData", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.remove("listProducts");
                                    editor.apply();
                                }
                            }, 1200000);
                        }
                        produtos.sort(Comparator.comparing(Produto::getNome));
                        produtoAdapter = new ProdutoAdapter(getActivity(), produtos);
                        loadingProgressBar.setVisibility(View.INVISIBLE);
                        listView.setAdapter(produtoAdapter);
                    }
                }
                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(requireContext().getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Gson gson = new Gson();
            Type produtoListType = new TypeToken<List<Produto>>() {}.getType();
            produtos = gson.fromJson(listaProdutosJson, produtoListType);
            loadingProgressBar.setVisibility(View.INVISIBLE);
            produtos.sort(Comparator.comparing(Produto::getNome));
            produtoAdapter = new ProdutoAdapter(getActivity(), produtos);
            listView.setAdapter(produtoAdapter);
        }



        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produto = produtos.get(position);
//                EditText quantidade = view.findViewById(R.id.quantidade);
//                String quantidadeString = quantidade.getText().toString();
//                int quantidadeInt = Integer.parseInt(quantidadeString);
                Carrinho carrinho = new Carrinho(produto.getNome(), 1, produto.getPreco(), produto.getFoto(), null);
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

        return view;
    }

    private void filterProducts(String searchText) {
        List<Produto> filtro = new ArrayList<>();
        for (Produto produto : produtos) {
            if (produto.getNome().toLowerCase().contains(searchText.toLowerCase())) {
                filtro.add(produto);
            }
        }

        if (filtro.isEmpty()) {
            naoExiste.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            naoExiste.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            produtoAdapter = new ProdutoAdapter(getActivity(), filtro);
            listView.setAdapter(produtoAdapter);
        }
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}