package com.example.busco.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class tickets_fragment extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            ListView listView = findViewById(R.id.listView); // Substitua "R.id.listView" pelo ID correto da sua ListView

            // Crie uma lista de itens para exibir
            List<SeuItem> items = new ArrayList<>();
            // Adicione seus itens à lista

            // Crie um adaptador personalizado
            ArrayAdapter<SeuItem> adapter = new ArrayAdapter<SeuItem>(this, R.layout.list_item_layout, items) {
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);

                    // Configure os elementos do LinearLayout com base nos dados do item
                    // Exemplo:
                    // TextView titleTextView = view.findViewById(R.id.textView11);
                    // titleTextView.setText(items.get(position).getTitulo());

                    // Faça isso para todos os elementos do LinearLayout

                    return view;
                }
            };

            listView.setAdapter(adapter);
        }
    }

}
