package com.example.ac2_atividade5;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MockApiService {

    @GET("alunos")
    Call<List<Aluno>> getAlunos();

    @POST("alunos")
    Call<Aluno> criarAluno(@Body Aluno aluno);
}
