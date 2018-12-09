package com.example.jasongomez.fragmentcommunication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<Person> personList;
    RecyclerViewFragment.OnPersonSelectedListener personSelectedListener;

    public RecyclerViewAdapter(List<Person> personList, RecyclerViewFragment.OnPersonSelectedListener personSelectedListener) {
        this.personList = personList;
        this.personSelectedListener = personSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Person personTemp = personList.get(position);
        holder.name.setText(personTemp.getName());
        holder.age.setText(personTemp.getAge());
        holder.favoriteAnimal.setText(personTemp.getFavoriteAnimal());
        holder.itemView.setOnClickListener(view -> personSelectedListener.onPersonSelected(personTemp));
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, age, favoriteAnimal;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            age = itemView.findViewById(R.id.tvAge);
            favoriteAnimal = itemView.findViewById(R.id.tvFavoriteAnimal);
        }
    }
}
