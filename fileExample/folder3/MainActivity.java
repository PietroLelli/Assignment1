package com.example.progetto;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.os.Bundle;
import com.example.progetto.RecyclerView.CardAdapter;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    private CardAdapter adapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setRecyclerView(this);
        Utilities.insertFragment(this, new HomeFragment(), HomeFragment.class.getSimpleName());
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