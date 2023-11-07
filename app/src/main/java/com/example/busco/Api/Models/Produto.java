package com.example.busco.Api.Models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Produto {
    private int id;
    private String nome;
    private int estoque;
    private Date data_colheita;
    private double preco;
    private String tipo;
    private int fornecedor;
    private String foto;

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

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public Date getData_colheita() {
        return data_colheita;
    }

    public void setData_colheita(Date data_colheita) {
        this.data_colheita = data_colheita;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(int fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Produto(String nome, int estoque, Date data_colheita, double preco, String tipo, int fornecedor) {
        this.nome = nome;
        this.estoque = estoque;
        this.data_colheita = data_colheita;
        this.preco = preco;
        this.tipo = tipo;
        this.fornecedor = fornecedor;
    }

    public Produto(int id, String nome, int estoque, Date data_colheita, double preco, String tipo, int fornecedor, String foto) {
        this.id = id;
        this.nome = nome;
        this.estoque = estoque;
        this.data_colheita = data_colheita;
        this.preco = preco;
        this.tipo = tipo;
        this.fornecedor = fornecedor;
        this.foto = foto;
    }
}
