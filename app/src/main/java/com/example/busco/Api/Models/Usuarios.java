package com.example.busco.Api.Models;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Usuarios {
    private int id;
    private String email;
    private String senha;
    private String cep;
    private String nome;
    private String cpf;
    private Date data_cadastro;
    private int qnt_doacao;
    private String telefone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(Date data_cadastro) {
        this.data_cadastro = data_cadastro;
    }

    public int getQnt_doacao() {
        return qnt_doacao;
    }

    public void setQnt_doacao(int qnt_doacao) {
        this.qnt_doacao = qnt_doacao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Usuarios(String email, String senha, String cep, String nome, String cpf, String telefone) {
        this.email = email;
        this.senha = senha;
        this.cep = cep;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }
    public Usuarios(int id, String email, String senha, String cep, String nome, String cpf, String telefone) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.cep = cep;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Usuarios{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", cep='" + cep + '\'' +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", data_cadastro=" + data_cadastro +
                ", qnt_doacao=" + qnt_doacao +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}