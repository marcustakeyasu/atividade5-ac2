package com.example.ac2_atividade5;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepService {
    @GET("{cep}/json/")
    Call<Endereco> getEndereco(@Path("cep") String cep);
}
