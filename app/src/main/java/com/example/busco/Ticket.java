package com.example.busco;


import android.widget.ImageView;

public class Ticket {
    private String nome;

    private String preco;
    private int fotoResource;
    private int fotoProdutoPromoResource;

    public Ticket(String nome, String preco,int fotoResource, int fotoProdutoPromoResource) {
        this.nome = nome;
        this.preco = preco;
        this.fotoResource = fotoResource;
        this.fotoProdutoPromoResource = fotoProdutoPromoResource;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
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