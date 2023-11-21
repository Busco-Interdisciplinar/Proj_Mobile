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
        promocoes.add(new Ticket("Morango", "R$ 5.1",R.drawable.morango, R.drawable.morango_promocao));
        promocoes.add(new Ticket("Batata", "R$ 8,55",R.drawable.batata_promocao, R.drawable.batata));
        promocoes.add(new Ticket("Laranja", "R$ 2.4",R.drawable.laranja, R.drawable.laranja_promocao));
        promocoes.add(new Ticket("Mamão", "R$ 4.3",R.drawable.mamao, R.drawable.mamao_promocao));
        promocoes.add(new Ticket("Alface","R$ 3,2", R.drawable.alface, R.drawable.maracuja_promocao));
        promocoes.add(new Ticket("Abacate", "R$ 4.5", R.drawable.abacate, R.drawable.abacate));

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
