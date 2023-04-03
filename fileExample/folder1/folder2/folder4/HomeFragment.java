package com.example.progettomobile;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettomobile.RecyclerView.CardAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private CardAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Activity activity = getActivity();
        if(activity != null){
            setRecyclerView(getActivity());

            FloatingActionButton floatingActionButton = view.findViewById(R.id.fab_add);

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.insertFragment((AppCompatActivity) activity, new AddFragment(), AddFragment.class.getSimpleName());
                }
            });

        }else{
            Log.e("HomeFragment", "Activity is null");
        }

    }

    private void setRecyclerView(final Activity activity) {
        recyclerView = activity.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        List<CardItem> list = new ArrayList<>();
        list.add(new CardItem("ic_baseline_add_24", "Product name", "25/04/2022", "Description"));
        list.add(new CardItem("ic_baseline_add_24", "Product name", "25/04/2022", "Description"));

        adapter = new CardAdapter(list, activity);


    }
}
