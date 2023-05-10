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

import com.uc.firebasecrud.Model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddStudent extends AppCompatActivity implements TextWatcher {

    Toolbar bar;
    Dialog dialog;
    TextInputLayout iemail, ipass, iname, inim, iage, iaddress;
    RadioGroup rg_gender;
    RadioButton rb_gender;
    Button btn;
    int position = 0;
    Student student;
    String uid, email, pass, name, nim, age, gender = "Male", address, action;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bar = findViewById(R.id.tb_reg_student);
        iemail = findViewById(R.id.emailreg);
        ipass = findViewById(R.id.passreg);
        iname = findViewById(R.id.namereg);
        inim = findViewById(R.id.nimreg);
        iage = findViewById(R.id.agereg);
        iaddress = findViewById(R.id.addressreg);
        rg_gender = findViewById(R.id.rg_genderbox);
        btn = findViewById(R.id.buttonreg);

        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dialog = GlobalVariable.loadingDialog(AddStudent.this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("student");

        iemail.getEditText().addTextChangedListener(this);
        ipass.getEditText().addTextChangedListener(this);
        iname.getEditText().addTextChangedListener(this);
        inim.getEditText().addTextChangedListener(this);
        iage.getEditText().addTextChangedListener(this);
        iaddress.getEditText().addTextChangedListener(this);

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
                Intent intent = new Intent(AddStudent.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Intent intent = getIntent();
        action = intent.getStringExtra("action");
        position = intent.getIntExtra("position", 0);
        student = intent.getParcelableExtra("data_student");
        if (action.equalsIgnoreCase("add")) {
            bar.setTitle("Register Student");
            btn.setText("Register Student");
        } else if (action.equalsIgnoreCase("edit")) {
            bar.setTitle("Edit Student");
            btn.setText("Edit Student");
            iemail.getEditText().setText(student.getEmail());
            ipass.getEditText().setText(student.getPassword());
            iname.getEditText().setText(student.getName());
            inim.getEditText().setText(student.getNim());
            iage.getEditText().setText(student.getAge());
            iaddress.getEditText().setText(student.getAddress());
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (action.equalsIgnoreCase("add")) {
                    RegisterStudent();
                } else if (action.equalsIgnoreCase("edit")) {
                    EditStudent();
                }
            }
        });
    }
    public void getFormValue() {
        email = iemail.getEditText().getText().toString().trim();
        pass = ipass.getEditText().getText().toString().trim();
        name = iname.getEditText().getText().toString().trim();
        nim = inim.getEditText().getText().toString().trim();
        age = iage.getEditText().getText().toString().trim();
        address = iaddress.getEditText().getText().toString().trim();
    }
    void RegisterStudent() {
        getFormValue();
        dialog.show();
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(AddStudent.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    dialog.cancel();
                    uid = mAuth.getCurrentUser().getUid();
                    Student student = new Student(uid, email, pass, name, nim, gender, age, address);
                    mDatabase.child(uid).setValue(student).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddStudent.this, "Student register Successful", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(AddStudent.this, Login.class);
                            startActivity(intent1);
                            finish();
                        }
                    });
                    mAuth.signOut();
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidCredentialsException malFormed) {
                        Toast.makeText(AddStudent.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    } catch (FirebaseAuthUserCollisionException existEmail) {
                        Toast.makeText(AddStudent.this, "Email already registered", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(AddStudent.this, "Register failed!", Toast.LENGTH_SHORT).show();
                    }
                    dialog.cancel();
                }
            }
        });
    }
    void EditStudent() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        email = iemail.getEditText().getText().toString().trim();
        pass = ipass.getEditText().getText().toString().trim();
        name = iname.getEditText().getText().toString().trim();
        nim = inim.getEditText().getText().toString().trim();
        age = iage.getEditText().getText().toString().trim();
        address = iaddress.getEditText().getText().toString().trim();

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("pass", pass);
        params.put("name", name);
        params.put("nim", nim);
        params.put("age", age);
        params.put("address", address);

        mDatabase.child(uid).setValue(student).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dialog.cancel();
                Intent intent;
                intent = new Intent(AddStudent.this, StudentData.class);
                intent.putExtra("action", "edit");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AddStudent.this);
                startActivity(intent, options.toBundle());
                finish();
            }
        });
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        getFormValue();
        if (!email.isEmpty() && !pass.isEmpty() && !name.isEmpty() && !nim.isEmpty() && !age.isEmpty() && !address.isEmpty()) {
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
        getMenuInflater().inflate(R.menu.student_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.student_list) {
            Intent intent;
            intent = new Intent(AddStudent.this, StudentData.class);
            intent.putExtra("action", "not_delete");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AddStudent.this);
            startActivity(intent, options.toBundle());
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent intent;
        intent = new Intent(AddStudent.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AddStudent.this);
        startActivity(intent, options.toBundle());
        finish();
    }
}