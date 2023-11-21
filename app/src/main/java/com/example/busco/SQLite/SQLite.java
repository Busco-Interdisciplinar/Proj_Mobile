package com.example.busco.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLite extends SQLiteOpenHelper {

    public SQLite(Context context) {
        super(context, "Busco", null, 32);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE usuario (id integer, email text, senha text, cep text, nome text, cpf text, telefone text, data_cadastro text, qnt_doacao integer, foto text)";
        String script2 = "CREATE TABLE carrinho (id integer, nome text, quantidade integer, preco double, foto text, cupom text)";
        db.execSQL(script);
        db.execSQL(script2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String addColumnQuery = "ALTER TABLE usuario ADD COLUMN foto text";
        db.execSQL(addColumnQuery);
    }
}
