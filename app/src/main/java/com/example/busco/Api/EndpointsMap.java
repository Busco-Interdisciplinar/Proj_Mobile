package com.example.busco.Api;

import retrofit2.Call;

import com.example.busco.Api.Models.Fornecedor;
import com.example.busco.Api.Models.Usuarios;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface EndpointsMap {
    @POST("/api/usuarios/inserirUsuario")
    Call<ApiResponse> cadastrarUsuario(@Body Usuarios usuario);

    @GET("/api/usuarios/login/{email}/{senha}")
    Call<ApiResponse> logarUsuario(@Path("email") String email, @Path("senha") String senha);

    @GET("/api/usuarios/buscarCpfEmailTelefone")
    Call<ApiResponse> buscarCpfEmailTelefone(@Query("cpf") String cpf, @Query("email") String email, @Query("telefone") String telefone);

    @GET("/api/usuarios/buscarEmail/{email}")
    Call<ApiResponse> findByEmail(@Path("email") String email);

    @PATCH("/api/usuarios/resetarSenha/{email}/{senha}")
    Call<ApiResponse> resetSenha(@Path("email") String email, @Path("senha") String senha);
    @GET("/api/produto/listarProdutos")
    Call<ApiResponse> listarProdutos();

    @GET("/api/rota/buscarRota/{rota}")
    Call<ApiResponse> findRota(@Path("rota") int rota);

    @POST("/api/fornecedor/inserirFornecedor")
    Call<ApiResponse> inserirFornecedor(@Body Fornecedor fornecedor);
}