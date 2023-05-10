package com.uc.firebasecrud.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.uc.firebasecrud.Model.Course;
import com.uc.firebasecrud.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EnrollAdapter extends RecyclerView.Adapter<EnrollAdapter.CardViewViewHolder> {

    private Context context;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private ArrayList<Course> listCourse;
    boolean parallel = false;

    public ArrayList<Course> getListCourse() {
        return listCourse;
    }

    public void setListCourse(ArrayList<Course> listCourse) {
        this.listCourse = listCourse;
    }

    public EnrollAdapter(final Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public EnrollAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enroll_adapter, parent, false);
        return new EnrollAdapter.CardViewViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull EnrollAdapter.CardViewViewHolder holder, int position) {
        final Course course = getListCourse().get(position);
        holder.enrollc.setText(course.getSubject());
        holder.enrolll.setText(course.getLecturer());
        holder.enrolld.setText(course.getDay());
        holder.enrollts.setText(course.getStart());
        holder.enrollte.setText(course.getEnd());

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collide(course);
                Toast.makeText(context, "Adding Course...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return getListCourse().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView enrollc, enrolll, enrolld, enrollts, enrollte;
        Button btn;

        public CardViewViewHolder(View itemView) {
            super(itemView);
            enrollc = itemView.findViewById(R.id.rollcourse);
            enrolll = itemView.findViewById(R.id.rolllecturer);
            enrolld = itemView.findViewById(R.id.rollday);
            enrollts = itemView.findViewById(R.id.rollstart);
            enrollte = itemView.findViewById(R.id.rollend);
            btn = itemView.findViewById(R.id.rolladd);
        }
    }
    MutableLiveData<Course> addCourse = new MutableLiveData<>();

    public MutableLiveData<Course> getAddCourse() {
        return addCourse;
    }

    public void Collide(final Course course_temp) {

        final int course_temp_time = Integer.parseInt(course_temp.getStart().replace(":", ""));
        final int course_temp_time_end = Integer.parseInt(course_temp.getEnd().replace(":", ""));
        final String course_temp_day = course_temp.getDay();

        mDatabase.child("student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("course").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                parallel = false;
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Course course = childSnapshot.getValue(Course.class);
                    String course_day = course.getDay();
                    int course_time = Integer.parseInt(course.getStart().replace(":", ""));
                    int course_time_end = Integer.parseInt(course.getEnd().replace(":", ""));
//                    cek hari sama
                    if (course_day.equalsIgnoreCase(course_temp_day)) {
//                        cek waktu start sama
                        if (course_temp_time >= course_time && course_temp_time <= course_time_end) {
                            parallel = true;
                            break;
                        }
//                            cek waktu end sama
                        if (course_temp_time_end >= course_time && course_temp_time_end <= course_time_end) {
                            parallel = true;
                            break;
                        }
                    }
                }
                if (parallel == true) {
                    Toast.makeText(context, "Add Failed! Schedule time collided with your current schedule(s)!", Toast.LENGTH_SHORT).show();
                } else {
                    addCourse.setValue(course_temp);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}