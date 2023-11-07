package com.example.busco.Api.Models;

import java.util.Date;

public class Rota {
    private int id;
    private String ponto_referencia;
    private String horario;
    private String bairro;
    private String rua;
    private Date data_rota;
    private int rota;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPonto_referencia() {
        return ponto_referencia;
    }

    public void setPonto_referencia(String ponto_referencia) {
        this.ponto_referencia = ponto_referencia;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Date getData_rota() {
        return data_rota;
    }

    public void setData_rota(Date data_rota) {
        this.data_rota = data_rota;
    }

    public int getRota() {
        return rota;
    }

    public void setRota(int rota) {
        this.rota = rota;
    }

    public Rota(String ponto_referencia, String horario, String bairro, String rua, Date data_rota, int rota) {
        this.ponto_referencia = ponto_referencia;
        this.horario = horario;
        this.bairro = bairro;
        this.rua = rua;
        this.data_rota = data_rota;
        this.rota = rota;
    }

    public Rota(int id, String ponto_referencia, String horario, String bairro, String rua, Date data_rota, int rota) {
        this.id = id;
        this.ponto_referencia = ponto_referencia;
        this.horario = horario;
        this.bairro = bairro;
        this.rua = rua;
        this.data_rota = data_rota;
        this.rota = rota;
    }

    @Override
    public String toString() {
        return "Rota{" +
                "id=" + id +
                ", ponto_referencia='" + ponto_referencia + '\'' +
                ", horario='" + horario + '\'' +
                ", bairro='" + bairro + '\'' +
                ", rua='" + rua + '\'' +
                ", data_rota=" + data_rota +
                ", rota=" + rota +
                '}';
    }
}
