package com.example.jasongomez.fragmentcommunication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemFragment extends Fragment {

    private TextView name, age, favoriteAnimal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_fragment, container, false);
        name = view.findViewById(R.id.tvName);
        age = view.findViewById(R.id.tvAge);
        favoriteAnimal = view.findViewById(R.id.tvFavoriteAnimal);
        return view;
    }

    public void personSelected(Person person) {
        name.setText(person.getName());
        age.setText(person.getAge());
        favoriteAnimal.setText(person.getFavoriteAnimal());

    }
}
