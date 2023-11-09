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

        long id = database.insert("carrinho", null, info);
        c.setId((int) id);

        close();
        return c;
    }

    public List<Carrinho> listar() throws ParseException {
        List<Carrinho> listaCarrinho = new ArrayList<>();
        oppen();
        Cursor cursor = database.rawQuery("SELECT * FROM carrinho", null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String nome = cursor.getString(1);
                int quantidade = cursor.getInt(2);
                double preco = cursor.getDouble(3);
                String foto = cursor.getString(4);
                String cupom = cursor.getString(5);
                Carrinho carrinho = new Carrinho(id, nome, quantidade, preco, foto, cupom);
                listaCarrinho.add(carrinho);
            }while (cursor.moveToNext());
        }

        close();
        return listaCarrinho;
    }

    public Carrinho pesquisar(String nome){
        oppen();
        Cursor cursor = database.rawQuery("SELECT * FROM carrinho WHERE nome = ?", new String[]{nome});
        Carrinho carrinho = null;

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            int quantidade = cursor.getInt(2);
            double preco = cursor.getDouble(3);
            String foto = cursor.getString(4);
            String cupom = cursor.getString(5);

            carrinho = new Carrinho(id, nome, quantidade, preco, foto, cupom);
        }

        close();
        return carrinho;
    }

    public void atualizarQuantidadePorNome(String nome, int novaQuantidade) {
        oppen();

        ContentValues values = new ContentValues();
        values.put("quantidade", novaQuantidade);

        database.update("carrinho", values, "nome = ?", new String[]{nome});

        close();
    }


    public void remover(){
        oppen();
        database.delete("carrinho", null, null);
        close();
    }
    public int remove2(String nome){
        oppen();
        String where[] = new String[]{String.valueOf(nome)};
        int resultado = database.delete("carrinho", "nome=?", where);
        close();
        return resultado;
    }

}
