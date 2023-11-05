package com.example.busco.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.busco.Api.Models.Produto;
import com.example.busco.Firebase.Connection;
import com.example.busco.ProdutoAdapter;
import com.example.busco.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class produtos_fragment extends Fragment {

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

                            String produtoId = String.valueOf(produto.getId());

                            //Buscando a imagem do produto no firebase
                            Connection connection = Connection.getInstance();
                            DatabaseReference databaseReference = connection.getDatabaseReference();
                            DatabaseReference imagemRef = databaseReference.child("produtos_images").child(produtoId);

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

                        produtos.sort(Comparator.comparing(Produto::getNome));
                        produtoAdapter = new ProdutoAdapter(getActivity(), produtos);
                        listView.setAdapter(produtoAdapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(requireContext().getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });

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

        return view;
    }

    private String buscarUrlDaImagemNoFirebase(String produtoId) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        String caminhoImagem = "produtos_images/" + produtoId;
        StorageReference imagemRef = storageRef.child(caminhoImagem);

        imagemRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageUrl = uri.toString();
                if (produtoAdapter != null) {
                    produtoAdapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        });

        return caminhoImagem;
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