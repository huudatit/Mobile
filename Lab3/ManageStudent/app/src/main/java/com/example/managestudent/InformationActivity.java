package com.example.managestudent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InformationActivity extends AppCompatActivity {
    EditText edt_FullName, edt_ID, edt_Class;
    Button btn_Back, btn_Update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.informationLinear), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edt_FullName = findViewById(R.id.edt_FullName);
        edt_ID = findViewById(R.id.edt_ID);
        edt_Class = findViewById(R.id.edt_Class);
        btn_Back = findViewById(R.id.btn_Back);
        btn_Update = findViewById(R.id.btn_Update);

        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getBundleExtra("student");

        edt_FullName.setText(myBundle.getString("fullName"));
        edt_ID.setText(myBundle.getString("id"));
        edt_Class.setText(myBundle.getString("class"));

        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edt_ID.getText().toString();
                String fullName = edt_FullName.getText().toString();
                String classAc = edt_Class.getText().toString();

                Information studentUpdated = new Information(id, fullName, classAc);
                MyDatabaseHelper myDB = new MyDatabaseHelper(InformationActivity.this);
                myDB.updateStudent(studentUpdated);

                Intent updatedIntent = new Intent();
                updatedIntent.putExtra("id", id);
                updatedIntent.putExtra("fullName", fullName);
                updatedIntent.putExtra("classAc", classAc);

                setResult(RESULT_OK, updatedIntent);

                Toast.makeText(InformationActivity.this, "Update Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}