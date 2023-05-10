package com.uc.firebasecrud;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements TextWatcher {

    Toolbar bar;
    TextInputLayout iemail, ipass;
    String e, p;
    Button btn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bar = findViewById(R.id.tb_login);
        btn = findViewById(R.id.buttonlog);
        iemail = findViewById(R.id.emaillog);
        ipass = findViewById(R.id.passlog);
        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        iemail.getEditText().addTextChangedListener(this);
        ipass.getEditText().addTextChangedListener(this);

        btn.setEnabled(false);

        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(e) && TextUtils.isEmpty(p)){
                    Toast.makeText(Login.this, "All field are required!", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.signInWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Login.this, "Welcome!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, StudentArea.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(Login.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        e = iemail.getEditText().getText().toString().trim();
        p = ipass.getEditText().getText().toString().trim();
        if(e.isEmpty() && p.isEmpty()){
            btn.setEnabled(false);
        }else{
            btn.setEnabled(true);
        }
    }
    @Override
    public void afterTextChanged(Editable s) {
    }
}