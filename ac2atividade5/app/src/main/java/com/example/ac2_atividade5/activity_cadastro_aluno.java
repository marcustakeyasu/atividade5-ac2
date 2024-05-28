package com.example.ac2_atividade5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class activity_cadastro_aluno extends AppCompatActivity {


    private EditText etRA, etNome, etCEP, etLogradouro, etComplemento, etBairro, etCidade, etUF;
    private Button btnSalvar, btnListaAlunos;
    private ViaCepService viaCepService;

    private MockApiService mockApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno);

        mockApiService = RetrofitInstance.getRetrofitInstance().create(MockApiService.class);


        etRA = findViewById(R.id.etRA);
        etNome = findViewById(R.id.etNome);
        etCEP = findViewById(R.id.etCEP);
        etLogradouro = findViewById(R.id.etLogradouro);
        etComplemento = findViewById(R.id.etComplemento);
        etBairro = findViewById(R.id.etBairro);
        etCidade = findViewById(R.id.etCidade);
        etUF = findViewById(R.id.etUF);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnListaAlunos = findViewById(R.id.btnListaAlunos);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        viaCepService = retrofit.create(ViaCepService.class);

        etCEP.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String cep = etCEP.getText().toString();
                if (!cep.isEmpty()) {
                    buscarEndereco(cep);
                }
            }
        });

        btnSalvar.setOnClickListener(v -> salvarAluno());

        btnListaAlunos.setOnClickListener(v -> {
            Intent intent = new Intent(activity_cadastro_aluno.this, activity_lista_alunos.class);
            startActivity(intent);
        });
    }

    private void buscarEndereco(String cep) {
        Call<Endereco> call = viaCepService.getEndereco(cep);
        call.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if (response.isSuccessful()) {
                    Endereco endereco = response.body();
                    if (endereco != null) {
                        etLogradouro.setText(endereco.getLogradouro());
                        etComplemento.setText(endereco.getComplemento());
                        etBairro.setText(endereco.getBairro());
                        etCidade.setText(endereco.getLocalidade());
                        etUF.setText(endereco.getUf());
                    } else {
                        Log.e("ViaCepService", "Endereço é nulo");
                        Toast.makeText(activity_cadastro_aluno.this, "Endereço não encontrado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("ViaCepService", "Erro na resposta da API: " + response.code());
                    Toast.makeText(activity_cadastro_aluno.this, "Erro ao buscar endereço", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                Log.e("ViaCepService", "Erro na chamada de rede", t);
                Toast.makeText(activity_cadastro_aluno.this, "Erro de rede", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void salvarAluno() {
        int ra = Integer.parseInt(etRA.getText().toString());
        String nome = etNome.getText().toString();
        String cep = etCEP.getText().toString();
        String logradouro = etLogradouro.getText().toString();
        String complemento = etComplemento.getText().toString();
        String bairro = etBairro.getText().toString();
        String cidade = etCidade.getText().toString();
        String uf = etUF.getText().toString();

        Aluno aluno = new Aluno(ra, nome, cep, logradouro, complemento, bairro, cidade, uf);

        Call<Aluno> call = mockApiService.criarAluno(aluno);
        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(activity_cadastro_aluno.this, "Aluno salvo com sucesso", Toast.LENGTH_SHORT).show();
                    // Limpar campos ou outras ações
                } else {
                    Log.e("MockApiService", "Erro na resposta da API: " + response.code());
                    Toast.makeText(activity_cadastro_aluno.this, "Erro ao salvar aluno", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                Log.e("MockApiService", "Erro na chamada de rede", t);
                Toast.makeText(activity_cadastro_aluno.this, "Erro de rede", Toast.LENGTH_SHORT).show();
            }
        });
    }
}