package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText edName,edType,edDescriprion;
    private DatabaseReference mDataBase;
    private String EXERCISES_KEY = "Exercise";// группа определенная передается
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init()
    {
        edName = findViewById(R.id.edName);
        edType = findViewById(R.id.edType);
        edDescriprion = findViewById(R.id.edDescriprion);
        mDataBase = FirebaseDatabase.getInstance().getReference(EXERCISES_KEY);
    }
    public void onClickSave(View view)
    {
        String id = mDataBase.getKey();
        String name = edName.getText().toString();
        String  type = edType.getText().toString();
        String description = edDescriprion.getText().toString();
        Exercises newExercises = new Exercises(id,name,type,description);
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(type)&& !TextUtils.isEmpty(description))
        {
            Toast.makeText(this,"Сохранено",Toast.LENGTH_SHORT).show();
            mDataBase.push().setValue(newExercises);

        }
        else{
            Toast.makeText(this,"Пустое поле",Toast.LENGTH_LONG).show();
        }

    }
    public void onClickRead(View view){
        Intent i = new Intent(MainActivity.this, ReadActivity.class);
        startActivity(i);
    }
}