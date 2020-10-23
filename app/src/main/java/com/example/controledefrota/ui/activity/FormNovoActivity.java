package com.example.controledefrota.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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
    private boolean eFormularioEdicao;
    private Carro carro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_novo);

        carregaViews();
        cliqueBotao();

        Intent intent = getIntent();
        if (intent.hasExtra(Constantes.CHAVE_EDICAO_CARRO)){
            eFormularioEdicao = true;
            carro = (Carro) intent.getSerializableExtra(Constantes.CHAVE_EDICAO_CARRO);
            carregaDadosFormulario();
        }
    }

    private void carregaDadosFormulario() {
        editModelo.setText(carro.getModelo());
        editPlaca.setText(carro.getPlaca());
    }

    private void cliqueBotao() {
        btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eFormularioEdicao){
                    atualizaCarro();

                    Intent intent = new Intent(FormNovoActivity.this,MainActivity.class);
                    intent.putExtra(Constantes.CHAVE_EDICAO_CARRO, carro);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }else{
                    carro = pegaCarroDoFormulario();

                    Intent intent = new Intent(FormNovoActivity.this,MainActivity.class);
                    intent.putExtra(Constantes.CHAVE_NOVO_CARRO, carro);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    private void atualizaCarro() {
        String modelo = editModelo.getText().toString();
        String placa = editPlaca.getText().toString();

        carro.setPlaca(placa);
        carro.setModelo(modelo);
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