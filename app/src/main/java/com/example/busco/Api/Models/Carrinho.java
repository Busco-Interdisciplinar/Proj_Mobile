package com.example.busco.Api.Models;

import android.widget.ImageView;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Carrinho {
    int id;
    String nome;
    int quantidade;
    double preco;
    String foto;
    String cupom;

    public Carrinho(String nome, int quantidade, double preco, String foto, String cupom) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.foto = foto;
        this.cupom = cupom;
    }

    public Carrinho(int id, String nome, int quantidade, double preco, String foto, String cupom) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.foto = foto;
        this.cupom = cupom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCupom() {
        return cupom;
    }

    public void setCupom(String cupom) {
        this.cupom = cupom;
    }

    @Override
    public String toString() {
        return "Carrinho{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", quantidade=" + quantidade +
                ", preco=" + preco +
                ", foto='" + foto + '\'' +
                ", cupom='" + cupom + '\'' +
                '}';
    }
}
