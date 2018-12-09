package com.example.jasongomez.fragmentcommunication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements RecyclerViewFragment.OnPersonSelectedListener {

    private static final String RECYCLER_VIEW_FRAGMENT_TAG = "rvFragment";
    private static final String ITEM_FRAGMENT_TAG = "itemFragment";
    private RecyclerViewFragment recyclerViewFragment;
    private ItemFragment itemFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewFragment = new RecyclerViewFragment();
        itemFragment = new ItemFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.rvFragContainer, recyclerViewFragment, RECYCLER_VIEW_FRAGMENT_TAG)
                .add(R.id.itemFragContainer, itemFragment, ITEM_FRAGMENT_TAG)
                .commit();
    }


    @Override
    public void onPersonSelected(Person person) {
        ItemFragment fragment = (ItemFragment) getSupportFragmentManager().findFragmentByTag(ITEM_FRAGMENT_TAG);
        fragment.personSelected(person);
    }
}
