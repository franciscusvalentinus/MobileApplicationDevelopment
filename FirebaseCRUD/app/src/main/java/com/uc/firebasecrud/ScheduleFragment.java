package com.uc.firebasecrud;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uc.firebasecrud.Model.Course;
import com.uc.firebasecrud.adapter.ScheduleAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ScheduleFragment extends Fragment {

    RecyclerView rv;
    DatabaseReference mDatabase;
    ArrayList<Course> listCourse = new ArrayList<>();

    public ScheduleFragment() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_schedule, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference("student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("course");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listCourse.clear();
                rv.setAdapter(null);
                for(DataSnapshot childSnapshot : snapshot.getChildren()){
                    Course course = childSnapshot.getValue(Course.class);
                    listCourse.add(course);
                }
                showData(listCourse);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        rv = view.findViewById(R.id.rv_schedule);
        return view;
    }
    private void showData(ArrayList<Course> listCourse){
        rv.setLayoutManager(new LinearLayoutManager(ScheduleFragment.this.getActivity()));
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(ScheduleFragment.this.getActivity());
        scheduleAdapter.setListCourse(listCourse);
        rv.setAdapter(scheduleAdapter);
    }
}