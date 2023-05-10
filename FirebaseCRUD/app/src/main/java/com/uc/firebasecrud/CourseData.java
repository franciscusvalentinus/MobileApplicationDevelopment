package com.uc.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.uc.firebasecrud.Model.Course;
import com.uc.firebasecrud.adapter.CourseAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseData extends AppCompatActivity {

    Toolbar bar;
    DatabaseReference mDatabase;
    ArrayList<Course> listCourse = new ArrayList<>();
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_data);

        bar = findViewById(R.id.tb_crse_data);
        rv = findViewById(R.id.rv_crse_data);

        setSupportActionBar(bar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference("course");
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseData.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        fetchCourseData();
    }
    private void fetchCourseData() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listCourse.clear();
                rv.setAdapter(null);
                for(DataSnapshot childSnapshot : snapshot.getChildren()){
                    Course course = childSnapshot.getValue(Course.class);
                    listCourse.add(course);
                }
                showCourseData(listCourse);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void showCourseData(ArrayList<Course> list) {
        rv.setLayoutManager(new LinearLayoutManager(CourseData.this));
        CourseAdapter courseAdapter = new CourseAdapter(CourseData.this);
        courseAdapter.setListCourse(list);
        rv.setAdapter(courseAdapter);
    }
    @Override
    public void onBackPressed() {
        Intent intent;
        intent = new Intent(CourseData.this, AddCourse.class);
        intent.putExtra("action", "add");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(CourseData.this);
        startActivity(intent, options.toBundle());
        finish();
    }
}