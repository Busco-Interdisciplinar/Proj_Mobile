package com.example.busco;


public class Ticket {
    private String nome;
    private double preco;
    private String fotoUrl;

    public Ticket(String nome, double preco, String fotoUrl) {
        this.nome = nome;
        this.preco = preco;
        this.fotoUrl = fotoUrl;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}