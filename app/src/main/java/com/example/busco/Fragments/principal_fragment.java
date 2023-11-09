package com.example.busco.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.busco.Api.Models.Usuarios;
import com.example.busco.R;

import com.example.busco.WebViewActivity;

import com.example.busco.Redefinir_Senha;
import com.example.busco.SobreNos;

import com.google.gson.Gson;

public class principal_fragment extends Fragment {
    private LinearLayout acoes;
    private LinearLayout quemSomos;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.principal_fragment, container, false);

        acoes = view.findViewById(R.id.linearLayout7);
        quemSomos = view.findViewById(R.id.linearLayout72);

        acoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirLandPage();
            }
        });

        quemSomos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirLandPage();
            }
        });

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("user", "");
        Usuarios usuario = gson.fromJson(json, Usuarios.class);

        String nomeUsuario = usuario.getNome();
        nomeUsuario = nomeUsuario.substring(0, 1).toUpperCase() + nomeUsuario.substring(1);
        String mensagem = "Olá, ";
        String[] partesDoNome = nomeUsuario.split(" ");
        mensagem = mensagem + partesDoNome[0];
        TextView nomeCliente = view.findViewById(R.id.nomeCliente);
        nomeCliente.setText(mensagem);

        return view;
    }

    private void abrirLandPage() {
        Intent intent = new Intent(requireContext(), WebViewActivity.class);
        startActivity(intent);
    }
}
