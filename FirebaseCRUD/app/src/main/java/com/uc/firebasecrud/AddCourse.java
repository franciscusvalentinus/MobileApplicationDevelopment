package com.uc.firebasecrud;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.uc.firebasecrud.Model.Course;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import android.view.MenuItem;
import android.view.View;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCourse extends AppCompatActivity implements TextWatcher {

    Toolbar bar;
    TextInputLayout subject;
    Spinner day, start, end, lect;
    Button btn;
    String c, d, ts, te, l, act;
    private DatabaseReference mDatabase;
    Course course;
    ArrayAdapter<CharSequence> ender;

    List<String> lecturer_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        bar = findViewById(R.id.tb_course);
        day = findViewById(R.id.daycourse);
        start = findViewById(R.id.startcourse);
        end = findViewById(R.id.endcourse);
        lect = findViewById(R.id.lectcourse);
        subject = findViewById(R.id.subcourse);
        btn = findViewById(R.id.buttoncourse);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ArrayAdapter<CharSequence> adapter_day = ArrayAdapter.createFromResource(this, R.array.Spinner_Day, android.R.layout.simple_spinner_item);
        adapter_day.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(adapter_day);

        ArrayAdapter<CharSequence> adapter_time = ArrayAdapter.createFromResource(this, R.array.time, android.R.layout.simple_spinner_item);
        adapter_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        start.setAdapter(adapter_time);

        start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ender = null;
                set_spinner_time_end(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        lecturer_list = new ArrayList<>();
        mDatabase.child("lecturer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot:snapshot.getChildren()) {
                    String spinner_lecturer = childSnapshot.child("name").getValue(String.class);
                    lecturer_list.add(spinner_lecturer);
                }
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(AddCourse.this, android.R.layout.simple_spinner_item,lecturer_list);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                lect.setAdapter(arrayAdapter);
                if (act.equalsIgnoreCase("edit")){
                    int index = arrayAdapter.getPosition(course.getLecturer());
                    lect.setSelection(index);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        act = getIntent().getStringExtra("action");
        if(act.equalsIgnoreCase("add")){
            getSupportActionBar().setTitle("Add Course");
            subject.getEditText().addTextChangedListener(this);
            btn.setText("Add Course");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c = subject.getEditText().getText().toString().trim();
                    l = lect.getSelectedItem().toString();
                    d = day.getSelectedItem().toString();
                    ts = start.getSelectedItem().toString();
                    te = end.getSelectedItem().toString();
                    addCourse(c, l, d, ts, te);
                }
            });
        }else if(act.equalsIgnoreCase("edit")){
            getSupportActionBar().setTitle("Edit Course");
            btn.setText("Edit Course");
            btn.setEnabled(true);
            course = getIntent().getParcelableExtra("edit_data_course");
            subject.getEditText().setText(course.getSubject());
            int dayIndex = adapter_day.getPosition(course.getDay());
            day.setSelection(dayIndex);
            int startIndex = adapter_time.getPosition(course.getStart());
            start.setSelection(startIndex);
            set_spinner_time_end(startIndex);
            final int endIndex = ender.getPosition(course.getEnd());
            end.setSelection(endIndex);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c = subject.getEditText().getText().toString().trim();
                    l = lect.getSelectedItem().toString();
                    d = day.getSelectedItem().toString();
                    ts = start.getSelectedItem().toString();
                    te = end.getSelectedItem().toString();
                    Map<String, Object> params = new HashMap<>();
                    params.put("subject", c);
                    params.put("lecturer", l);
                    params.put("day", d);
                    params.put("start", ts);
                    params.put("end", te);
                    mDatabase.child("course").child(course.getId()).updateChildren(params).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent;
                            intent = new Intent(AddCourse.this, CourseData.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AddCourse.this);
                            startActivity(intent, options.toBundle());
                            finish();
                        }
                    });
                }
            });
        }
        ArrayAdapter<CharSequence> adapter_lecturer = ArrayAdapter.createFromResource(this, R.array.lecturer, android.R.layout.simple_spinner_item);
        adapter_lecturer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lect.setAdapter(adapter_lecturer);
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCourse.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void set_spinner_time_end(int position){
        if (position == 0) {
            ender = ArrayAdapter.createFromResource(AddCourse.this, R.array.time0730, android.R.layout.simple_spinner_item);
        } else if (position == 1) {
            ender = ArrayAdapter.createFromResource(AddCourse.this, R.array.time0800, android.R.layout.simple_spinner_item);
        } else if (position == 2) {
            ender = ArrayAdapter.createFromResource(AddCourse.this, R.array.time0830, android.R.layout.simple_spinner_item);
        } else if (position == 3) {
            ender = ArrayAdapter.createFromResource(AddCourse.this, R.array.time0900, android.R.layout.simple_spinner_item);
        } else if (position == 4) {
            ender = ArrayAdapter.createFromResource(AddCourse.this, R.array.time0930, android.R.layout.simple_spinner_item);
        } else if (position == 5) {
            ender = ArrayAdapter.createFromResource(AddCourse.this, R.array.time1000, android.R.layout.simple_spinner_item);
        } else if (position == 6) {
            ender = ArrayAdapter.createFromResource(AddCourse.this, R.array.time1030, android.R.layout.simple_spinner_item);
        } else if (position == 7) {
            ender = ArrayAdapter.createFromResource(AddCourse.this, R.array.time1100, android.R.layout.simple_spinner_item);
        } else if (position == 8) {
            ender = ArrayAdapter.createFromResource(AddCourse.this, R.array.time1130, android.R.layout.simple_spinner_item);
        } else if (position == 9) {
            ender = ArrayAdapter.createFromResource(AddCourse.this, R.array.time1200, android.R.layout.simple_spinner_item);
        } else if (position == 10) {
            ender = ArrayAdapter.createFromResource(AddCourse.this, R.array.time1230, android.R.layout.simple_spinner_item);
        } else if (position == 11) {
            ender = ArrayAdapter.createFromResource(AddCourse.this, R.array.time1300, android.R.layout.simple_spinner_item);
        } else if (position == 12) {
            ender = ArrayAdapter.createFromResource(AddCourse.this, R.array.time1330, android.R.layout.simple_spinner_item);
        } else if (position == 13) {
            ender = ArrayAdapter.createFromResource(AddCourse.this, R.array.time1400, android.R.layout.simple_spinner_item);
        } else if (position == 14) {
            ender = ArrayAdapter.createFromResource(AddCourse.this, R.array.time1430, android.R.layout.simple_spinner_item);
        } else if (position == 15) {
            ender = ArrayAdapter.createFromResource(AddCourse.this, R.array.time1500, android.R.layout.simple_spinner_item);
        } else if (position == 16) {
            ender = ArrayAdapter.createFromResource(AddCourse.this, R.array.time1530, android.R.layout.simple_spinner_item);
        } else if (position == 17) {
            ender = ArrayAdapter.createFromResource(AddCourse.this, R.array.time1600, android.R.layout.simple_spinner_item);
        } else if (position == 18) {
            ender = ArrayAdapter.createFromResource(AddCourse.this, R.array.time1630, android.R.layout.simple_spinner_item);
        }
        ender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        end.setAdapter(ender);
    }
    void getFormValue (){
        c = subject.getEditText().getText().toString().trim();
        d = day.getSelectedItem().toString();
        ts = start.getSelectedItem().toString();
        te = end.getSelectedItem().toString();
        l = lect.getSelectedItem().toString();
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        getFormValue();
        if (!c.isEmpty()) {
            btn.setEnabled(true);
        } else {
            btn.setEnabled(false);
        }
    }
    @Override
    public void afterTextChanged(Editable s) {
    }
    void undetectable (){
        subject.getEditText().setText("");
        lect.setSelection(0);
        day.setSelection(0);
        start.setSelection(0);
        end.setSelection(0);
    }
    public void addCourse(String course, String lecturer, String day, String time, String time_end) {
        String mid = mDatabase.child("course").push().getKey();
        Course course_temp = new Course(mid,course, day, time, time_end, lecturer);
        mDatabase.child("course").child(mid).setValue(course_temp).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddCourse.this, "Add Course Successfully", Toast.LENGTH_SHORT).show();
                undetectable();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddCourse.this, "Add Course Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent intent;
            intent = new Intent(AddCourse.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AddCourse.this);
            startActivity(intent, options.toBundle());
            finish();
            return true;
        }else if(id == R.id.course_list){
            Intent intent;
            intent = new Intent(AddCourse.this, CourseData.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AddCourse.this);
            startActivity(intent, options.toBundle());
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent intent;
        intent = new Intent(AddCourse.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AddCourse.this);
        startActivity(intent, options.toBundle());
        finish();
    }
}