package com.uc.firebasecrud.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.uc.firebasecrud.Model.Course;
import com.uc.firebasecrud.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.CardViewViewHolder> {

    private Context context;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private ArrayList<Course> listCourse;
    public ArrayList<Course> getListCourse() {
        return listCourse;
    }
    public void setListCourse(ArrayList<Course> listCourse) {
        this.listCourse = listCourse;
    }
    public ScheduleAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enroll_adapter, parent, false);
        return new ScheduleAdapter.CardViewViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.CardViewViewHolder holder, int position) {
        final Course course = getListCourse().get(position);
        holder.enrollc.setText(course.getSubject());
        holder.enrolll.setText(course.getLecturer());
        holder.enrolld.setText(course.getDay());
        holder.enrollts.setText(course.getStart());
        holder.enrollte.setText(course.getEnd());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("course").child(course.getId()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                    }
                });
            }
        });
    }
    @Override
    public int getItemCount() {
        return  getListCourse().size();
    }
    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView enrollc, enrolll, enrolld, enrollts, enrollte;
        Button btn;
        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            enrollc = itemView.findViewById(R.id.rollcourse);
            enrolll = itemView.findViewById(R.id.rolllecturer);
            enrolld = itemView.findViewById(R.id.rollday);
            enrollts = itemView.findViewById(R.id.rollstart);
            enrollte = itemView.findViewById(R.id.rollend);
            btn = itemView.findViewById(R.id.rolladd);
        }
    }
}