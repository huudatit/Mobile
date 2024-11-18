package com.example.managestudent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<Information> studentList;
    private LinearLayout itemLinear;

    public MyAdapter(Activity activity, Context context, ArrayList<Information> studentList) {
        this.activity = activity;
        this.context = context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Information student = studentList.get(holder.getAdapterPosition());

        holder.tv_ID.setText(student.getId());
        holder.tv_FullName.setText(student.getName());
        holder.tv_Class.setText(student.getClassAc());

        holder.itemLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Information clicked = studentList.get(holder.getAdapterPosition());

                Intent myIntent = new Intent(context, InformationActivity.class);
                Bundle myBundle = new Bundle();

                myBundle.putString("id", clicked.getId());
                myBundle.putString("fullName", clicked.getName());
                myBundle.putString("class", clicked.getClassAc());

                myIntent.putExtra("student", myBundle);
                activity.startActivityForResult(myIntent, 1);
            }
        });

        holder.itemLinear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(context);
                Information clicked = studentList.get(holder.getAdapterPosition());
                boolean deleted = myDB.deleteStudent(clicked.getId());
                if (deleted) {
                    Cursor cursor = myDB.getStudentById(clicked.getId());
                    if (cursor != null && cursor.getCount() == 0) {
                        studentList.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        Toast.makeText(context, "Student deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Failed to delete student", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ID, tv_FullName, tv_Class;
        LinearLayout itemLinear;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ID = itemView.findViewById(R.id.tv_ID);
            tv_FullName = itemView.findViewById(R.id.tv_FullName);
            tv_Class = itemView.findViewById(R.id.tv_Class);
            itemLinear = itemView.findViewById(R.id.itemLinear);
        }
    }
}