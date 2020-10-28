package com.example.controledefrota.ui.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controledefrota.R;
import com.example.controledefrota.database.AppDatabase;
import com.example.controledefrota.model.Carro;
import com.example.controledefrota.ui.recyclerview.adapter.listener.CarroItemClickListener;

import java.util.Collections;
import java.util.List;

public class CarroAdapter extends RecyclerView.Adapter<CarroAdapter.ViewHolder> {
    private Context context;
    private List<Carro> carros;
    private CarroItemClickListener onItemClickListener;

    public CarroAdapter(Context context,List<Carro> carros) {
        this.context = context;
        this.carros = carros;
    }

    public void setOnItemClickListener(CarroItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //A linha abaixo usa o metodo estático inflate para criar views
        //a partir do item layout que construimos para o RecyclerView
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_lista_carros,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Primeira parte: Recuperar o objeto(carro) com base na posição do recyclerview
        Carro carro = carros.get(position);

        //Juntar o objeto com o item do recyclerview
        holder.vicula(carro);
    }

    @Override
    public int getItemCount() {
        //Retornar o tamanho total da lista
        return carros.size();
    }

    public void removeItemCarro(int adapterPosition) {
        AppDatabase.getInstance(context)
                .carroDao()
                .delete(carros.get(adapterPosition));
        carros.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }

    public void alteraPosicao(int posicaoInicial, int posicaoFinal) {
        Collections.swap(carros,posicaoInicial,posicaoFinal);
        notifyItemMoved(posicaoInicial,posicaoFinal);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Para cada View dentro do layout que quisermos manipular, criar um campo com a classe
        //correspondente
        private TextView textPlaca;
        private TextView textModelo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Inicializar as views
            textPlaca = itemView.findViewById(R.id.itemTextPlaca);
            textModelo = itemView.findViewById(R.id.itemTextModelo);
        }

        public void vicula(Carro carro) {
            textPlaca.setText(carro.getPlaca());
            textModelo.setText(carro.getModelo());

            //Inicio do clickItem
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posicao = getAdapterPosition();
                    Carro carro = carros.get(getAdapterPosition());
                    onItemClickListener.itemClick(carro, posicao);
                }
            });
        }
    }
}
