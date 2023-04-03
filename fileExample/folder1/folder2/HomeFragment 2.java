package com.example.progetto;

import android.app.Activity;
import android.os.BugreportManager;
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

import com.example.progetto.RecyclerView.CardAdapter;
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

    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState){
        super.onViewCreated(view ,saveInstanceState);
        final Activity activity = getActivity();
        if (activity != null){
            setRecyclerView(activity);

            FloatingActionButton floatingActionButton = view.findViewById(R.id.fab_add);
            floatingActionButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Utilities.insertFragment((AppCompatActivity) activity, new AddFragment(), AddFragment.class.getSimpleName());
                }
            });
        }else{
            Log.e("Home", "Activity is null");
        }
    }
    private void setRecyclerView(final Activity activity){
        recyclerView = activity.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        List<CardItem> list = new ArrayList<>();
        list.add(new CardItem("ic_android_black_24dp", "Place name", "14/03/2022", "description"));
        list.add(new CardItem("ic_android_black_24dp", "Place name", "14/03/2022", "description"));
        adapter = new CardAdapter(list, activity);
        recyclerView.setAdapter(adapter);
    }
}
