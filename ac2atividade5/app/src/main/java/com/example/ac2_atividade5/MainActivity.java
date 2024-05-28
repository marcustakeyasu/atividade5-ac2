package com.example.ac2_atividade5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button btnListarAlunos;
    private Button btnListagemAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnListagemAluno = findViewById(R.id.btnListagemAlunos);
        btnListarAlunos = findViewById(R.id.btnListarAlunos);
        btnListarAlunos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, activity_cadastro_aluno.class);
                startActivity(intent);
            }
        });

        btnListagemAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, activity_lista_alunos.class);
                startActivity(intent2);
            }
        });
    }
}