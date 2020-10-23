package com.example.controledefrota.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.controledefrota.R;
import com.example.controledefrota.model.Carro;

public class FormNovoActivity extends AppCompatActivity {
    private EditText editModelo;
    private EditText editPlaca;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_novo);

        carregaViews();
        cliqueBotao();
    }

    private void cliqueBotao() {
        btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Carro carro = pegaCarroDoFormulario();

            }
        });
    }

    private Carro pegaCarroDoFormulario() {
        String modelo = editModelo.getText().toString();
        String placa = editPlaca.getText().toString();

        return new Carro(placa, modelo);
    }

    private void carregaViews() {
        editModelo = findViewById(R.id.formEditModelo);
        editPlaca = findViewById(R.id.formEditPlaca);
    }


}