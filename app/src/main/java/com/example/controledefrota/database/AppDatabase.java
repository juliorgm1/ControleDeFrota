package com.example.controledefrota.database;

import android.content.Context;
import android.widget.Adapter;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.controledefrota.dao.CarroDao;
import com.example.controledefrota.model.Carro;

@Database(entities = {Carro.class},version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract CarroDao carroDao();

    public static AppDatabase getInstance(Context context){

        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    AppDatabase.class,
                    "contro_frota.db").allowMainThreadQueries().
                    fallbackToDestructiveMigration().build();
        }

        return instance;
    }
}
