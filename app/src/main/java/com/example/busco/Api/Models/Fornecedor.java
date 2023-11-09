package com.example.busco.Api.Models;

public class Fornecedor {
    private int id;
    private String cnpj;
    private String telefone;
    private String nome;
    private String email;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUsuario_padrao() {
        return usuario_padrao;
    }

    public void setUsuario_padrao(int usuario_padrao) {
        this.usuario_padrao = usuario_padrao;
    }

    public Fornecedor(int id, String cnpj, String telefone, String nome, String email, int usuario_padrao) {
        this.id = id;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.nome = nome;
        this.email = email;
        this.usuario_padrao = usuario_padrao;
    }

    public Fornecedor(String cnpj, String telefone, String nome, String email, int usuario_padrao) {
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.nome = nome;
        this.email = email;
        this.usuario_padrao = usuario_padrao;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
                "id=" + id +
                ", cnpj='" + cnpj + '\'' +
                ", telefone='" + telefone + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", usuario_padrao=" + usuario_padrao +
                '}';
    }
}
