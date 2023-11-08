package com.example.busco.Api.Models;

public class Instituicao {
    private int id;
    private String cnpj;
    private String cep_sede;
    private String nome;
    private String telefone;
    private int usuario_padrao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCep_sede() {
        return cep_sede;
    }

    public void setCep_sede(String cep_sede) {
        this.cep_sede = cep_sede;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getUsuario_padrao() {
        return usuario_padrao;
    }

    public void setUsuario_padrao(int usuario_padrao) {
        this.usuario_padrao = usuario_padrao;
    }

    public Instituicao(int id, String cnpj, String cep_sede, String nome, String telefone, int usuario_padrao) {
        this.id = id;
        this.cnpj = cnpj;
        this.cep_sede = cep_sede;
        this.nome = nome;
        this.telefone = telefone;
        this.usuario_padrao = usuario_padrao;
    }

    public Instituicao(String cnpj, String cep_sede, String nome, String telefone, int usuario_padrao) {
        this.cnpj = cnpj;
        this.cep_sede = cep_sede;
        this.nome = nome;
        this.telefone = telefone;
        this.usuario_padrao = usuario_padrao;
    }

    @Override
    public String toString() {
        return "Instituicao{" +
                "id=" + id +
                ", cnpj='" + cnpj + '\'' +
                ", cep_sede='" + cep_sede + '\'' +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", usuario_padrao=" + usuario_padrao +
                '}';
    }
}
