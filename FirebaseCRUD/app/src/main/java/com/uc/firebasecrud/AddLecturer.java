package com.uc.firebasecrud;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.uc.firebasecrud.Model.Lecturer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddLecturer extends AppCompatActivity implements TextWatcher {

    Toolbar bar;
    Dialog dialog;
    TextInputLayout iname, iexpertise;
    RadioGroup rg_gender;
    RadioButton rb_gender;
    String name, expertise, gender = "Male", action;
    Button btn;
    Lecturer lecturer;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        dialog = GlobalVariable.loadingDialog(AddLecturer.this);

        bar = findViewById(R.id.tb_lecturer);
        iname = findViewById(R.id.namelect);
        iexpertise = findViewById(R.id.explect);
        iname.getEditText().addTextChangedListener(this);
        rg_gender = findViewById(R.id.rg_genderbox);
        iexpertise.getEditText().addTextChangedListener(this);
        btn = findViewById(R.id.buttonlect);

        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                rb_gender = findViewById(i);
                gender = rb_gender.getText().toString();
            }
        });
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddLecturer.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Intent intent = getIntent();
        action = intent.getStringExtra("action");
        if (action.equals("add")) {
            getSupportActionBar().setTitle(R.string.addlecturer);
            btn.setText(R.string.addlecturer);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = iname.getEditText().getText().toString().trim();
                    expertise = iexpertise.getEditText().getText().toString().trim();
                    addLecturer(name, gender, expertise);
                }
            });
        } else {
            getSupportActionBar().setTitle(R.string.editlecturer);
            lecturer = intent.getParcelableExtra("edit_data_lect");
            iname.getEditText().setText(lecturer.getName());
            iexpertise.getEditText().setText(lecturer.getExpertise());
            if (lecturer.getGender().equalsIgnoreCase("male")) {
                rg_gender.check(R.id.rb_gendermale);
            } else {
                rg_gender.check(R.id.rb_genderfemale);
            }
            btn.setText(R.string.editlecturer);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.show();
                    name = iname.getEditText().getText().toString().trim();
                    expertise = iexpertise.getEditText().getText().toString().trim();
                    Map<String, Object> params = new HashMap<>();
                    params.put("name", name);
                    params.put("expertise", expertise);
                    params.put("gender", gender);
                    mDatabase.child("lecturer").child(lecturer.getId()).updateChildren(params).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dialog.cancel();
                            Intent intent;
                            intent = new Intent(AddLecturer.this, LecturerData.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AddLecturer.this);
                            startActivity(intent, options.toBundle());
                            finish();
                        }
                    });
                }
            });
        }
    }
    public void addLecturer(String mnama, String mgender, String mexpertise) {
        dialog.show();
        String mid = mDatabase.child("lecturer").push().getKey();
        Lecturer lecturer = new Lecturer(mid, mnama, mgender, mexpertise);
        mDatabase.child("lecturer").child(mid).setValue(lecturer).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dialog.cancel();
                Toast.makeText(AddLecturer.this, "Add Lecturer Successfully", Toast.LENGTH_SHORT).show();
                iname.getEditText().setText("");
                iexpertise.getEditText().setText("");
                rg_gender.check(R.id.rb_gendermale);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.cancel();
                Toast.makeText(AddLecturer.this, "Add Lecturer Failed!, Please Try Again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    void getFormValue() {
        name = iname.getEditText().getText().toString().trim();
        expertise = iexpertise.getEditText().getText().toString().trim();
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        getFormValue();
        if (!name.isEmpty() && !expertise.isEmpty()) {
            btn.setEnabled(true);
        } else {
            btn.setEnabled(false);
        }
    }
    @Override
    public void afterTextChanged(Editable s) {
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lecturer_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent;
            intent = new Intent(AddLecturer.this, LecturerData.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AddLecturer.this);
            startActivity(intent, options.toBundle());
            finish();
            return true;
        } else if (id == R.id.lecturer_list) {
            Intent intent;
            intent = new Intent(AddLecturer.this, LecturerData.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AddLecturer.this);
            startActivity(intent, options.toBundle());
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent intent;
        intent = new Intent(AddLecturer.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AddLecturer.this);
        startActivity(intent, options.toBundle());
        finish();
    }
}