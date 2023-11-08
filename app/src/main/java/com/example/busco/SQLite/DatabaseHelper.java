package com.example.busco.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "dbBusco.db", null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE usuario (id integer, email text, senha text, cep text, nome text, cpf text, telefone text, data_cadastro text, qnt_doacao integer);");
        sqLiteDatabase.execSQL("CREATE TABLE carrinho (id integer, nome text, quantidade integer, preco double, foto text, cupom text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
