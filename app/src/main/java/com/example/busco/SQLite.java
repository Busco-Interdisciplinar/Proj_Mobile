package com.example.busco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLite extends SQLiteOpenHelper {

    public SQLite(Context context) {
        super(context, "Busco", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE carrinho (id serial PRIMARY KEY, nome VARCHAR(100), estoque INT, data_colheita DATE, preco DECIMAL(10,0), tipo VARCHAR(40), cupom VARCHAR(20))";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
