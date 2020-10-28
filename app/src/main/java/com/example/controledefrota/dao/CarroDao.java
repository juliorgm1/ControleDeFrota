package com.example.controledefrota.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.controledefrota.model.Carro;

import java.util.List;

@Dao
public interface CarroDao {
    @Insert
    void insert(Carro carro);

    @Delete
    void delete(Carro carro);

    @Update
    void update(Carro carro);

    @Query("SELECT * FROM carro")
    List<Carro> getAll();
}
