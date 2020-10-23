package com.example.controledefrota.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.view.View;

import com.example.controledefrota.R;
import com.example.controledefrota.model.Carro;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Declarando Views que serão manipuladas na MainActivity
    private RecyclerView recyclerViewCarros;
    private FloatingActionButton fabNovoCarro;
    //Declarando atributo estático, Essa lista por enquanto armazena os dados que seriam do banco de dados
    static List<Carro> carros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Nesse metodo vamos realiza a manipulação do recyclerview
        configuraRecyclerView();
        //Nesse metodo vamos manipular o botão
        cliqueBotao();
        //Esse metodo carrega a lista, é apenas uma simulação para fazermos testes no recyclerview
        geraListaCarros();
    }

    private void cliqueBotao() {
        //Recuperando a View do botão
        fabNovoCarro = findViewById(R.id.fabNovoCarro);

        //Fazendo o clique do botão
        fabNovoCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Quando o usuário clicar nesse botão significa que ele quer criar um novo carro
                //logo é necessário criar uma intent para abrir a activity de formulario de carro
                Intent intent = new Intent(MainActivity.this, FormNovoActivity.class);

                //Usamos o metodo abaixo para dizer ao android que a proxima tela deve devolver um objeto de carro
                startActivityForResult(intent,Constantes.SOLICITA_NOVO_CARRO);
            }
        });
    }

    //Esse método recupera os dados quando uma activity retorna uma resposta
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Aqui verificamos se o request(codigo de requisição) code e o extra estão corretos
        if (requestCode == Constantes.SOLICITA_NOVO_CARRO && data.hasExtra(Constantes.CHAVE_NOVO_CARRO)){
            //Se o result code vier como ok significa que podemos continuar com a processo de um novo carro
            if (resultCode == Activity.RESULT_OK){
                //recuperando os dados(Objeto Carro) vindos da FormCarroActivity
                Carro carro = (Carro) data.getSerializableExtra(Constantes.CHAVE_NOVO_CARRO);
                //Adicionando o objeto(Carro) naa lista
                carros.add(carro);

                //Notificando o Adapter para que o recyclerview seja atualizado
            }
        }

        if (requestCode == Constantes.SOLICITA_EDICAO_CARRO && data.hasExtra(Constantes.CHAVE_EDICAO_CARRO)){
            if (resultCode == Activity.RESULT_OK){
                Carro carro = (Carro) data.getSerializableExtra(Constantes.CHAVE_EDICAO_CARRO);

                //Aqui precisamos do objeto carro e também da posição da lista
            }
        }
    }

    private void configuraRecyclerView() {
        recyclerViewCarros = findViewById(R.id.recyclerViewCarros);
        //Aqui estamos configurando o LayoutManager, além no linearlayot temos outras como GridLayoutManager (depois dá uma pesquisada)
        recyclerViewCarros.setLayoutManager(new LinearLayoutManager(this));

    }

    static void geraListaCarros(){
        carros = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0)
                carros.add(new Carro("ABC-123"+i,"Corsa 201"+i));
            else
                carros.add(new Carro("ZYW-321"+i,"Palio 201"+i));
        }
    }
}