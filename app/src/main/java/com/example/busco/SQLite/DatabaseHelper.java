package com.example.busco.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "dbBusco.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL("CREATE TABLE carrinho\n" +
//                "                (id integer primary key autoincrement,\n" +
//                "                nome text,\n" +
//                "                quantidade integer,\n" +
//                "                preco double,\n" +
//                "                foto text,\n" +
//                "                cupom text)");
        sqLiteDatabase.execSQL("CREATE TABLE usuario (id integer primary key autoincrement, email text, senha text, cep text, nome text, cpf text, telefone text, data_cadastro text, qnt_doacao integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
