package com.example.ac2_atividade5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_lista_alunos extends AppCompatActivity {

    private ListView lvAlunos;
    private AlunoAdapter adapter;

    private MockApiService mockApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        mockApiService = RetrofitInstance.getRetrofitInstance().create(MockApiService.class);

        lvAlunos = findViewById(R.id.lvAlunos);
        adapter = new AlunoAdapter(this, new ArrayList<>());
        lvAlunos.setAdapter(adapter);

        carregarAlunos();
    }

    private void carregarAlunos() {
        Call<List<Aluno>> call = mockApiService.getAlunos();
        call.enqueue(new Callback<List<Aluno>>() {
            @Override
            public void onResponse(Call<List<Aluno>> call, Response<List<Aluno>> response) {
                if (response.isSuccessful()) {
                    List<Aluno> alunos = response.body();
                    adapter.clear();
                    adapter.addAll(alunos);
                } else {
                    Toast.makeText(activity_lista_alunos.this, "Erro ao carregar alunos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Aluno>> call, Throwable t) {
                Toast.makeText(activity_lista_alunos.this, "Erro de rede", Toast.LENGTH_SHORT).show();
            }
        });
    }
}