package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
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
    private List<Exercises> listTemp;
    private DatabaseReference mDataBase;
    private String EXERCISE_KEY = "Exercise";// группа определенная передается
    //private String EXERCISES_KEY = "Exercises";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_layout);
        init();
        getDataFromDB();
        setOnClickItem();
    }
    private void init(){
        listView = findViewById(R.id.listview);
        listData = new ArrayList<>();
        listTemp = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listData);
        listView.setAdapter(adapter);

        mDataBase = FirebaseDatabase.getInstance().getReference(EXERCISE_KEY);
        //mDataBase = FirebaseDatabase.getInstance().getReference(EXERCISES_KEY);
    }
    private void getDataFromDB()
    {
        ValueEventListener  vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (listData.size()>0){
                    listData.clear();
                }
                if(listTemp.size()>0)listTemp.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Exercises exercise = ds.getValue(Exercises.class);
                    assert  exercise !=null;
                    listData.add(exercise.description);
                    listTemp.add(exercise);
                    /*Exercises exercises = ds.getValue(Exercises.class);
                    assert  exercises !=null;
                    listData.add(exercises.description);*/
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDataBase.addValueEventListener(vListener);
    }

    private void setOnClickItem(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exercises exercises = listTemp.get(position);
                Intent i = new Intent(ReadActivity.this, ShowActivity.class);
                i.putExtra(Constants.EXERCISES_FIRST, exercises.name);
                i.putExtra(Constants.EXERCISES_SECOND, exercises.type);
                i.putExtra(Constants.EXERCISES_THIRD, exercises.description);
                startActivity(i);
            }
        });
    }
}
