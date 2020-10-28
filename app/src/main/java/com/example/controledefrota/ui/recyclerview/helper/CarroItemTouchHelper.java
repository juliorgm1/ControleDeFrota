package com.example.controledefrota.ui.recyclerview.helper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controledefrota.model.Carro;
import com.example.controledefrota.ui.recyclerview.adapter.CarroAdapter;


public class CarroItemTouchHelper extends ItemTouchHelper.Callback {

    private CarroAdapter adapter;

    public CarroItemTouchHelper(CarroAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int movimentoSwipe = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int movimentoDrag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(movimentoDrag, movimentoSwipe);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int posicaoInicial = viewHolder.getAdapterPosition();
        int posicaoFinal = target.getAdapterPosition();

        adapter.alteraPosicao(posicaoInicial, posicaoFinal);
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        //Exclua o item do recyclerview e BANCO DE DADOS(LISTA)
        adapter.removeItemCarro(viewHolder.getAdapterPosition());
        //ATUALIZA O RECYCLERVIEW

    }
}
