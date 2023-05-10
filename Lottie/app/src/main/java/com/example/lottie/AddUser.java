package com.example.lottie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Iterator;

public class AddUser extends AppCompatActivity implements TextWatcher {
    TextInputLayout inputnama, inputumur, inputaddress;
    TextInputEditText textnama, textage, textaddress;
    Button button;
    String name, address, age;
    Toolbar toolbar;
    ProgressDialog progressDialog;
    private User datanya;
    static String EXTRA_USER = "extra";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        textnama = findViewById(R.id.tname);
        textage = findViewById(R.id.tage);
        textaddress = findViewById(R.id.taddress);
        inputnama = findViewById(R.id.input_fname);
        inputumur = findViewById(R.id.input_age);
        inputaddress = findViewById(R.id.input_address);
        button = findViewById(R.id.button_tambah);
        inputnama.getEditText().addTextChangedListener(this);
        inputumur.getEditText().addTextChangedListener(this);
        inputaddress.getEditText().addTextChangedListener(this);
        toolbar = findViewById(R.id.tooladd);
        if (getIntent().getParcelableExtra(EXTRA_USER) != null) {
            datanya = getIntent().getParcelableExtra(EXTRA_USER);
            textnama.setText(datanya.getNama());
            textage.setText(datanya.getUmur());
            textaddress.setText(datanya.getAlamat());
            toolbar.setTitle("Edit User");
            button.setText("Update User");
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getParcelableExtra(EXTRA_USER) == null) {
                    Intent intent = new Intent(AddUser.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AddUser.this, ShowUser.class);
                    intent.putExtra(ShowUser.EXTRA_USERS, datanya);
                    startActivity(intent);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getParcelableExtra(EXTRA_USER) == null) {
                    progressDialog = new ProgressDialog(AddUser.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.ini_loading);
                    UserArray.data.add(new User(name, address, age));
                    Intent intent = new Intent(AddUser.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    if (!datanya.getNama().equals(name) || !datanya.getAlamat().equals(address) || !datanya.getUmur().equals(age)) {
                        Iterator<User> userIterator = UserArray.data.iterator();
                        while (userIterator.hasNext()) {
                            User user = userIterator.next();
                            if (user.nama.equals(datanya.getNama())) {
                                userIterator.remove();
                            }
                        }
                        progressDialog = new ProgressDialog(AddUser.this);
                        progressDialog.show();
                        progressDialog.setContentView(R.layout.ini_loading);
                        UserArray.data.add(new User(name, address, age));
                        Intent intent = new Intent(AddUser.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(AddUser.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        name = inputnama.getEditText().getText().toString().trim();
        age = inputumur.getEditText().getText().toString().trim();
        address = inputaddress.getEditText().getText().toString().trim();
        if (!name.isEmpty() && !address.isEmpty() && !age.isEmpty()) {
            button.setEnabled(true);
        } else {
            button.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

