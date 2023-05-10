package com.uc.latihanbottomnavigation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.uc.latihanbottomnavigation.R;
import com.uc.latihanbottomnavigation.model.Student;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_STUDENT = "extra";
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getIntent().getParcelableExtra(EXTRA_STUDENT) != null) {
            student = getIntent().getParcelableExtra(EXTRA_STUDENT);
        }

        Toast.makeText(this, student.getEmail(), Toast.LENGTH_SHORT).show();
    }
}
