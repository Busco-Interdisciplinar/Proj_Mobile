package com.example.busco;


import android.widget.ImageView;

public class Ticket {
    private String nome;
    private int fotoResource;
    private int fotoProdutoPromoResource;

    public Ticket(String nome, int fotoResource, int fotoProdutoPromoResource) {
        this.nome = nome;
        this.fotoResource = fotoResource;
        this.fotoProdutoPromoResource = fotoProdutoPromoResource;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getFotoResource() {
        return fotoResource;
    }

    public int getFotoProdutoPromoResource() {
        return fotoProdutoPromoResource;
    }
}