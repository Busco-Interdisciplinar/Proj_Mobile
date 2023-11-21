package com.example.busco.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.busco.Api.Models.Usuarios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public UsuarioDAO(Context c){
        context = c;
    }

    //abrir o database
    public UsuarioDAO oppen(){
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    //fechar o database
    public void close(){
        database.close();
        dbHelper.close();
    }

    public Usuarios salvar(Usuarios c){
        oppen();
        ContentValues info = new ContentValues();
        info.put("id", c.getId());
        info.put("email", c.getEmail());
        info.put("senha", c.getSenha());
        info.put("cep", c.getCep());
        info.put("nome", c.getNome());
        info.put("cpf", c.getCpf());
        info.put("telefone", c.getTelefone());
        info.put("qnt_doacao", c.getQnt_doacao());

        database.delete("usuario", null, null);
        long id = database.insert("usuario", null, info);
        c.setId((int) id);


        close();
        return c;
    }

    public List<Usuarios> listar() throws ParseException {
        List<Usuarios> listaCliente = new ArrayList<>();
        oppen();
        Cursor cursor = database.rawQuery("SELECT * FROM usuario", null);
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
                String foto = cursor.getString(9);
                Usuarios usuario = new Usuarios(id, email, senha, cep, nome, cpf, telefone, foto);
                listaCliente.add(usuario);
            }while (cursor.moveToNext());
        }

        close();
        return listaCliente;
    }

    public void atualizarFoto(int usuarioId, String novaFoto) {
        oppen();
        ContentValues values = new ContentValues();
        values.put("foto", novaFoto);
        database.update("usuario", values, "id=?", new String[]{String.valueOf(usuarioId)});
        close();
    }

    public void remover(){
        oppen();
        database.delete("usuario", null, null);
        close();
    }
}


