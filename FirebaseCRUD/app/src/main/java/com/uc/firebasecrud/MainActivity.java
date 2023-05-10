package com.uc.firebasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton RegisterStudent, LoginStudent, AddLecturer, AddCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RegisterStudent = findViewById(R.id.RegisterStudent);
        LoginStudent = findViewById(R.id.LoginStudent);
        AddLecturer = findViewById(R.id.AddLecturer);
        AddCourse = findViewById(R.id.AddCourse);

        AddLecturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddLecturer.class);
                intent.putExtra("action", "add");
                startActivity(intent);
            }
        });

        AddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCourse.class);
                intent.putExtra("action", "add");
                startActivity(intent);
            }
        });

        RegisterStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddStudent.class);
                intent.putExtra("action", "add");
                startActivity(intent);
            }
        });

        LoginStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}