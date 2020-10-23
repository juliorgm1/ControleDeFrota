package com.example.controledefrota.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.view.View;

import com.example.controledefrota.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCarros;
    private FloatingActionButton fabNovoCarro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configuraRecyclerView();
        cliqueBotao();
    }

    private void cliqueBotao() {
        fabNovoCarro = findViewById(R.id.fabNovoCarro);
        fabNovoCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormNovoActivity.class);
                startActivityForResult(intent,Constantes.SOLICITA_NOVO_CARRO);
            }
        });
    }

    private void configuraRecyclerView() {
        recyclerViewCarros = findViewById(R.id.recyclerViewCarros);
        recyclerViewCarros.setLayoutManager(new LinearLayoutManager(this));

    }
}