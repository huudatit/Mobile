package com.example.bai1;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ContactAdapter contactAdapter;
    ArrayList<Contact> contacts;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lv = findViewById(R.id.lv);

        db = new DatabaseHandler(this);
        db.resetContactsTable();

        contacts = new ArrayList<>();
        contactAdapter = new ContactAdapter(MainActivity.this, contacts);

        lv.setAdapter(contactAdapter);

        // Inserting contacts and logging each insertion
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));

        displayListContacts();

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Contact selectedContact = contacts.get(position);
                boolean deleted = db.deleteContact(selectedContact.getID());
                if (deleted) {
                    contacts.remove(position);
                    contactAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Contact deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to delete contact", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    void displayListContacts() {
        contacts.clear();
        Cursor cursor = db.getAllContacts();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No contacts found", Toast.LENGTH_SHORT).show();
            return;
        }
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String phone = cursor.getString(2);
            contacts.add(new Contact(id, name, phone));
        }
        contactAdapter.notifyDataSetChanged();
        cursor.close();
    }
}
