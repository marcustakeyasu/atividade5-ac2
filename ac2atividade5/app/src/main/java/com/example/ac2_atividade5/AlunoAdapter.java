package com.example.ac2_atividade5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AlunoAdapter extends ArrayAdapter<Aluno> {
    public AlunoAdapter(Context context, List<Aluno> alunos) {
        super(context, 0, alunos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item_aluno, parent, false);
        }

        TextView tvNome = convertView.findViewById(R.id.tvNome);
        TextView tvRA = convertView.findViewById(R.id.tvRA);

        tvNome.setText(aluno.getNome());
        tvRA.setText(String.valueOf(aluno.getRa()));

        return convertView;
    }
}
