package com.example.busco.Api.Models;

import java.util.Date;

public class Doacao {
    private int id;
    private Date data_doacao;
    private String descricao;
    private int produto_doado;
    private int usuario_doador;
    private Integer instituicao_doador;
    private int quantidade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData_doacao() {
        return data_doacao;
    }

    public void setData_doacao(Date data_doacao) {
        this.data_doacao = data_doacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getProduto_doado() {
        return produto_doado;
    }

    public void setProduto_doado(int produto_doado) {
        this.produto_doado = produto_doado;
    }

    public int getUsuario_doador() {
        return usuario_doador;
    }

    public void setUsuario_doador(int usuario_doador) {
        this.usuario_doador = usuario_doador;
    }

    public int getInstituicao_doador() {
        return instituicao_doador;
    }

    public void setInstituicao_doador(int instituicao_doador) {
        this.instituicao_doador = instituicao_doador;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Doacao(int id, Date data_doacao, String descricao, int produto_doado, int usuario_doador, Integer instituicao_doador, int quantidade) {
        this.id = id;
        this.data_doacao = data_doacao;
        this.descricao = descricao;
        this.produto_doado = produto_doado;
        this.usuario_doador = usuario_doador;
        this.instituicao_doador = instituicao_doador;
        this.quantidade = quantidade;
    }
    public Doacao(Date data_doacao, String descricao, int produto_doado, int usuario_doador, Integer instituicao_doador, int quantidade) {
        this.data_doacao = data_doacao;
        this.descricao = descricao;
        this.produto_doado = produto_doado;
        this.usuario_doador = usuario_doador;
        this.instituicao_doador = instituicao_doador;
        this.quantidade = quantidade;
    }

    public Doacao(Date data_doacao, String descricao, int produto_doado, int usuario_doador, int quantidade) {
        this.data_doacao = data_doacao;
        this.descricao = descricao;
        this.produto_doado = produto_doado;
        this.usuario_doador = usuario_doador;
        this.quantidade = quantidade;
    }

    public Doacao(String descricao,Date data_doacao, int produto_doado, Integer instituicao_doador, int quantidade) {
        this.data_doacao = data_doacao;
        this.descricao = descricao;
        this.produto_doado = produto_doado;
        this.instituicao_doador = instituicao_doador;
        this.quantidade = quantidade;

    }

    @Override
    public String toString() {
        return "Doacao{" +
                "id=" + id +
                ", data_doacao=" + data_doacao +
                ", descricao='" + descricao + '\'' +
                ", produto_doado=" + produto_doado +
                ", usuario_doador=" + usuario_doador +
                ", instituicao_doador=" + instituicao_doador +
                ", quantidade=" + quantidade +
                '}';
    }
}
