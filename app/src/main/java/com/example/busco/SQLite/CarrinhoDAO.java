package com.example.busco.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.busco.Api.Models.Carrinho;
import com.example.busco.Api.Models.Usuarios;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoDAO {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public CarrinhoDAO(Context c){
        context = c;
    }

    //abrir o database
    public CarrinhoDAO oppen(){
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    //fechar o database
    public void close(){
        database.close();
        dbHelper.close();
    }
    public Carrinho salvar(Carrinho c){
        oppen();
        ContentValues info = new ContentValues();
        info.put("id", c.getId());
        info.put("nome", c.getNome());
        info.put("quantidade", c.getQuantidade());
        info.put("preco", c.getPreco());
        info.put("foto", c.getFoto());
        info.put("cupom", c.getCupom());

        database.delete("carrinho", null, null);
        long id = database.insert("carrinho", null, info);
        c.setId((int) id);

        close();
        return c;
    }

    public List<Usuarios> listar() throws ParseException {
        List<Usuarios> listaCliente = new ArrayList<>();
        oppen();
        Cursor cursor = database.rawQuery("SELECT * FROM carrinho", null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String email = cursor.getString(1);
                String senha = cursor.getString(2);
                String cep = cursor.getString(3);
                String nome = cursor.getString(4);
                String cpf = cursor.getString(5);
                String telefone = cursor.getString(6);
                String dataCadastro = cursor.getString(7);
                int qnt_doacao = cursor.getInt(8);
                Usuarios usuario = new Usuarios(id, email, senha, cep, nome, cpf, telefone);
                listaCliente.add(usuario);
            }while (cursor.moveToNext());
        }

        close();
        return listaCliente;
    }

}
