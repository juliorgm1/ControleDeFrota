package com.example.controledefrota.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.controledefrota.R;
import com.example.controledefrota.database.AppDatabase;
import com.example.controledefrota.model.Carro;
import com.example.controledefrota.ui.recyclerview.adapter.CarroAdapter;
import com.example.controledefrota.ui.recyclerview.adapter.listener.CarroItemClickListener;
import com.example.controledefrota.ui.recyclerview.helper.CarroItemTouchHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Declarando Views que serão manipuladas na MainActivity
    private RecyclerView recyclerViewCarros;
    private FloatingActionButton fabNovoCarro;
    //Declarando atributo estático, Essa lista por enquanto armazena os dados que seriam do banco de dados
    static List<Carro> carros;
    //
    private CarroAdapter adapter;

    private int posicaoItemClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Esse metodo carrega a lista, é apenas uma simulação para fazermos testes no recyclerview
        geraListaCarros();
        //Nesse metodo vamos realiza a manipulação do recyclerview
        configuraRecyclerView();
        //Nesse metodo vamos manipular o botão
        cliqueBotao();
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

                //Adicionar objeto no banco
                AppDatabase
                        .getInstance(getApplicationContext())
                        .carroDao()
                        .insert(carro);
                //Adicionando o objeto(Carro) naa lista
                carros.add(carro);

                //Notificando o Adapter para que o recyclerview seja atualizado
            }
        }

        if (requestCode == Constantes.SOLICITA_EDICAO_CARRO && data.hasExtra(Constantes.CHAVE_EDICAO_CARRO)){
            if (resultCode == Activity.RESULT_OK){
                //Aqui precisamos do objeto carro e também da posição da lista
                Carro carro = (Carro) data.getSerializableExtra(Constantes.CHAVE_EDICAO_CARRO);
                AppDatabase
                        .getInstance(getApplicationContext())
                        .carroDao().update(carro);

                carros.set(posicaoItemClick,carro);
                adapter.notifyItemChanged(posicaoItemClick);
            }
        }
    }

    private void configuraRecyclerView() {
        recyclerViewCarros = findViewById(R.id.recyclerViewCarros);
        //Aqui estamos configurando o LayoutManager, além no linearlayot temos outras como GridLayoutManager (depois dá uma pesquisada)
        recyclerViewCarros.setLayoutManager(new LinearLayoutManager(this));

        //Juntar os dados da lista com os itens das Views do recyclerView
        adapter = new CarroAdapter(getApplicationContext(),carros);
        recyclerViewCarros.setAdapter(adapter);
        adapter.setOnItemClickListener(new CarroItemClickListener() {
            @Override
            public void itemClick(Carro carro, int posicao) {
                //Criar intent e mandar o objeto pra outra tela
                posicaoItemClick = posicao;
                Intent intent = new Intent(MainActivity.this,
                        FormNovoActivity.class);
                intent.putExtra(Constantes.CHAVE_EDICAO_CARRO,carro);
                startActivityForResult(intent, Constantes.SOLICITA_EDICAO_CARRO);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new CarroItemTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerViewCarros);
    }

    void geraListaCarros(){
        carros = AppDatabase.
                getInstance(getApplicationContext())
                .carroDao().getAll();

    }
}