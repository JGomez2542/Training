package com.example.jasongomez.fragmentcommunication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFragment extends Fragment {

    private OnPersonSelectedListener personSelectedCallback;
    private RecyclerView recyclerView;
    private List<Person> personList;

    public interface OnPersonSelectedListener {
        void onPersonSelected(Person person);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            personSelectedCallback = (OnPersonSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement OnPersonSelectedListener");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        personList = new ArrayList<>();
        personList.add(new Person("Jerry", "30", "Sloth"));
        personList.add(new Person("Jones", "55", "Bear"));
        personList.add(new Person("Anna", "14", "Cat"));
        personList.add(new Person("Jay", "23", "Butterfly"));
        personList.add(new Person("YoMomma", "45", "Tiger"));
        personList.add(new Person("YoDaddy", "45", "Lion"));
        personList.add(new Person("YoBaldHeadedGranny", "80", "Dog"));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(personList, personSelectedCallback);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}
