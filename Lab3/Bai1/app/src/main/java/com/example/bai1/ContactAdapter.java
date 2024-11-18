package com.example.bai1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {
    private ArrayList<Contact> contactList;
    private Context context;

    public ContactAdapter(Context context, ArrayList<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
        }

        TextView textViewID = convertView.findViewById(R.id.tv_ID);
        TextView textViewName = convertView.findViewById(R.id.tv_Name);
        TextView textViewPhone = convertView.findViewById(R.id.tv_Phone);

        Contact contact = contactList.get(position);
        textViewID.setText(String.valueOf(contact.getID())); // Assuming getID() returns an int
        textViewName.setText(contact.getName());
        textViewPhone.setText(contact.getPhoneNumber());

        return convertView;
    }
}

