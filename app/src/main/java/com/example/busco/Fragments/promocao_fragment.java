package com.example.busco.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.busco.R;
import com.example.busco.Ticket;
import com.example.busco.TicketAdapter;

import java.util.ArrayList;

public class promocao_fragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.promocao_fragment, container, false);

        ListView listView = view.findViewById(R.id.list_ticket);


        ArrayList<Ticket> promocoes = new ArrayList<>();
        promocoes.add(new Ticket("1,5kg de Morango", R.drawable.morango, R.drawable.morango_promocao));
        promocoes.add(new Ticket("600g de Batata", R.drawable.batata_promocao, R.drawable.batata));
        promocoes.add(new Ticket("500g de Laranja", R.drawable.laranja, R.drawable.laranja_promocao));
        promocoes.add(new Ticket("300g de Mamão", R.drawable.mamao, R.drawable.mamao_promocao));
        promocoes.add(new Ticket("250g de Maracujá", R.drawable.maracuja, R.drawable.maracuja_promocao));
        promocoes.add(new Ticket("250g de Abacate", R.drawable.abacate, R.drawable.abacate_promocao));

        TicketAdapter adapter = new TicketAdapter(getActivity(), promocoes);
        listView.setAdapter(adapter);

        return view;
    }


    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}
