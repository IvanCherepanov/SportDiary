package com.example.myapplication;

import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;//элемент один
    private List<String> listData;
    private DatabaseReference mDataBase;
    private String EXERCISES_KEY = "Exercise";// группа определенная передается
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_layout);
        init();
        getDataFromDB();
    }
    private void init(){
        listView = findViewById(R.id.listview);
        listData = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listData);
        listView.setAdapter(adapter);
        mDataBase = FirebaseDatabase.getInstance().getReference(EXERCISES_KEY);
    }
    private void getDataFromDB()
    {
        ValueEventListener  vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (listData.size()>0){
                    listData.clear();
                }
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Exercises exercises = ds.getValue(Exercises.class);
                    assert  exercises !=null;
                    listData.add(exercises.description);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDataBase.addValueEventListener(vListener);
    }
}
