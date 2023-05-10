package com.uc.latihanbottomnavigation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uc.latihanbottomnavigation.R;
import com.uc.latihanbottomnavigation.model.Student;

import java.util.ArrayList;

public class CardStudent extends RecyclerView.Adapter<CardStudent.StudentViewHolder> {

    private Context context;
    private ArrayList<Student> listStudent = new ArrayList<>();

    public CardStudent(Context context) {
        this.context = context;
    }

    public void setListStudent(ArrayList<Student> list){
        listStudent.clear();
        listStudent.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = listStudent.get(position);
        if(Integer.parseInt(student.getId()) < 10){
            String ids = "0"+ student.getId();
            holder.id.setText(ids);
        }else{
            holder.id.setText(student.getId());
        }
        holder.name.setText(student.getName());
        holder.email.setText(student.getEmail());
    }

    @Override
    public int getItemCount() {
        return listStudent.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, email;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tv_student_id);
            name = itemView.findViewById(R.id.tv_student_name);
            email = itemView.findViewById(R.id.tv_student_email);
        }
    }
}
