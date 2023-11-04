package com.example.busco;

public class Usuario {

    private int id;
    private String name;
    private String email;
    private String fotoPerfil;

    public Usuario(int id, String name, String email, String fotoPerfil) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.fotoPerfil = fotoPerfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
}
