package com.example.managestudent;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edt_ID, edt_FullName, edt_Class;
    Button btn_Add;
    RecyclerView rv;
    MyDatabaseHelper myDB;
    ArrayList<Information> myList;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLinear), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edt_ID = findViewById(R.id.edt_ID);
        edt_FullName = findViewById(R.id.edt_FullName);
        edt_Class = findViewById(R.id.edt_Class);
        btn_Add = findViewById(R.id.btn_Add);
        rv = findViewById(R.id.rv);

        myDB = new MyDatabaseHelper(this);
        myList = new ArrayList<>();
        myAdapter = new MyAdapter(MainActivity.this, this, myList);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(myAdapter);

        displayListStudents();

        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edt_ID.getText().toString();
                String fullName = edt_FullName.getText().toString();
                String classAc = edt_Class.getText().toString();

                if (!id.isEmpty() && !fullName.isEmpty() && !classAc.isEmpty()) {
                    myDB.addStudent(new Information(id, fullName, classAc));
                    Toast.makeText(MainActivity.this, "Student added", Toast.LENGTH_SHORT).show();
                    displayListStudents();
                } else {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

                edt_ID.setText("");
                edt_FullName.setText("");
                edt_Class.setText("");
                edt_ID.requestFocus();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK) {
            String id = data.getStringExtra("id");
            String fullName = data.getStringExtra("fullName");
            String classAc = data.getStringExtra("class");
            displayListStudents();
        }
    }

    void displayListStudents() {
        myList.clear();
        Cursor cursor = myDB.getAllStudents();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No students found", Toast.LENGTH_SHORT).show();
            return;
        }
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String fullName = cursor.getString(1);
            String classAc = cursor.getString(2);
            myList.add(new Information(id, fullName, classAc));
        }
        myAdapter.notifyDataSetChanged();
        cursor.close();
    }
}