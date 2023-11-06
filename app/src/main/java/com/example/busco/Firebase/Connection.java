package com.example.busco.Firebase;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Connection {
    private static Connection instance;
    private final DatabaseReference databaseReference;

    private Connection(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public static Connection getInstance(){
        if (instance == null){
            instance = new Connection();
        }
        return instance;
    }

    public DatabaseReference getDatabaseReference(){
        return databaseReference;
    }
}

