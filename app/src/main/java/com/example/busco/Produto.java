package com.example.busco;

public class Produto {

    private String nome;
    private double preco;
    private int foto;

    public Produto(String nome, double preco, int foto) {
        this.nome = nome;
        this.preco = preco;
        this.foto = foto;
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

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
