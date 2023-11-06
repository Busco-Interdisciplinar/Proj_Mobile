package com.example.busco.Firebase;

import java.util.Date;

public class Log {
    private String acao;
    private String data;
    private String descricao;
    private int id_usuario;
    private String nome_usuario;

    public Log(String acao, String data, String descricao, int id_usuario, String nome_usuario) {
        this.acao = acao;
        this.data = data;
        this.descricao = descricao;
        this.id_usuario = id_usuario;
        this.nome_usuario = nome_usuario;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    @Override
    public String toString() {
        return "Log{" +
                "acao='" + acao + '\'' +
                ", data=" + data +
                ", descricao='" + descricao + '\'' +
                ", id_usuario=" + id_usuario +
                ", nome_usuario='" + nome_usuario + '\'' +
                '}';
    }
}
