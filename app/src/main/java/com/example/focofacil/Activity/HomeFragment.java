package com.example.focofacil.Activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.focofacil.DiaDaSemana;
import com.example.focofacil.DiaSemanaAdapter;

import com.example.focofacil.R;
import com.example.focofacil.Tarefa;

import java.time.Clock;
import java.util.ArrayList;


import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment  {
    private RecyclerView recyclerViewDiasSemana;
    private ListAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<DiaDaSemana> listaDeDias;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    private void setupDiasDaSemana(View view) {
        // Obter o dia atual da semana
        Calendar calendar = Calendar.getInstance();
        int diaAtual = calendar.get(Calendar.DAY_OF_WEEK);

        // Iterar pelos dias da semana
        for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
            // Configurar o dia da semana
            DiaDaSemana dia = new DiaDaSemana(obterNomeDia(i), obterListaDeTarefas());

            // Configurar o TextView correspondente ao dia
            int textViewId = getResources().getIdentifier("txt_" + dia.getNomeDia(), "id", getActivity().getPackageName());
            //TextView textView = view.findViewById(textViewId);

            if (textViewId != 0) {
                Log.e("TextView", "TextView name: " + dia.getNomeDia());
                TextView textView = view.findViewById(textViewId);

                // Calcular a data correspondente ao dia da semana
                Calendar diaSelecionado = Calendar.getInstance();
                diaSelecionado.set(Calendar.DAY_OF_WEEK, i);

                // Adicionar essa data como extra para a DetalhesDiaActivity
                dia.setDataSelecionada(diaSelecionado.getTime());

                if (listaDeDias == null) {
                    listaDeDias = new ArrayList<>();
                }

                listaDeDias.add(dia);

                // Exibir o nome do dia e suas tarefas no TextView
                //textView.setText(dia.getNomeDia());

                // Configurar o OnClickListener para o TextView correspondente ao dia
                configurarOnClickListener(textView, dia);

                // Exibir as informações de cada tarefa nos TextViews específicos
                exibirInformacoesTarefas(dia, view);
            } else {
                // Se o TextView não foi encontrado,uma mensagem de log é exibida
                Log.e("TextView", "TextView not found for day: " + dia.getNomeDia());
            }
        }
    }

    private void exibirInformacoesTarefas(DiaDaSemana dia, View view) {



        int txtAssuntoId = getResources().getIdentifier("txtTarefaAssunto", "id", getContext().getPackageName());
        int txtDataHoraId = getResources().getIdentifier("txtTarefaDataHora", "id", getContext().getPackageName());
        int txtDescricaoId = getResources().getIdentifier("txtTarefaDescricao", "id", getContext().getPackageName());

        TextView txtAssunto = view.findViewById(txtAssuntoId);
        TextView txtDataHora = view.findViewById(txtDataHoraId);
        TextView txtDescricao = view.findViewById(txtDescricaoId);


        // Limpar os TextViews
        //txtDescricao.setText("");
        // Verificar se TextViews foram encontrados
        if (txtAssunto != null && txtDataHora != null) {
            // Limpar os TextViews

            txtAssunto.setText("");
            txtDataHora.setText("");
            txtDescricao.setText("");

            // Exibir informações da última tarefa (caso haja mais de uma)
            for (Tarefa tarefa : dia.getListaDeTarefas()) {
                // Preencher os TextViews com as informações da tarefa
                txtAssunto.setText(tarefa.getAssunto());

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                txtDataHora.setText(sdf.format(tarefa.getDataHora()));

                txtDescricao.setText(tarefa.getDescricao());

                Log.e("TextView", "txtAssunto: " + txtAssunto + " | txtDataHora: " + txtDataHora + " | txtDescricao: " + txtDescricao);
            }
        } else {

            Log.e("TextView", "txtAssunto e txtDataHora são NULOS");

        }
    }

    private ArrayList<Tarefa> obterListaDeTarefas() {
        // Retorna uma lista fictícia de tarefas para cada dia

        ArrayList<Tarefa> listaDeTarefas = new ArrayList<>();
        listaDeTarefas.add(new Tarefa("Tarefa 1", "Assunto 1", new Date()));

        // Adicione mais tarefas conforme necessário.
        return listaDeTarefas;
    }

    private String obterNomeDia(int dayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        // Formato desejado: txt_segunda_feira (substitui hífens por underscores)
        String dayName = new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date(calendar.getTimeInMillis()));

        dayName = dayName
                .replaceAll("[áãâàä]", "a")
                .replaceAll("[éêèë]", "e")
                .replaceAll("[íîìï]", "i")
                .replaceAll("[óôõòö]", "o")
                .replaceAll("[úûùü]", "u")
                .replaceAll("[ç]", "c");

        return dayName.toLowerCase().replace("-", "_");
    }

    private void configurarOnClickListener(TextView textView, final DiaDaSemana dia) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetalhesDiaFragment detalhesDiaFragment = DetalhesDiaFragment.newInstance(dia);

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, detalhesDiaFragment).addToBackStack(null).commit();

                // Substituir o fragmento atual pelo DetalhesDiaFragment
                //getParentFragmentManager().beginTransaction()
                  //      .replace(R.id.fragment_container, detalhesDiaFragment)
                    //    .addToBackStack(null)
                       // .commit();

            }
        });
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewDiasSemana);
        DiaSemanaAdapter adapter = new DiaSemanaAdapter(getContext(), listaDeDias);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setupRecyclerView(view);

        // Configurar o adapter para o RecyclerView
        DiaSemanaAdapter adapter = new DiaSemanaAdapter(getContext(), listaDeDias);
        //recyclerViewDiasSemana.setAdapter(adapter);

        // Criar instâncias de DiaDaSemana e adicioná-las à listaDeDias
        //listaDeDias = new ArrayList<>();
        setupDiasDaSemana(view);

        return view;
    }



}