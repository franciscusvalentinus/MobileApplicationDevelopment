package com.example.lottie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;

public class ShowUser extends AppCompatActivity {

    TextView Username, Userage, Useraddress;
    private User datanya1;
    Button edit,delete;
    Toolbar toolbar;

    static String EXTRA_USERS = "extra";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);
        if (getIntent().getParcelableExtra(EXTRA_USERS) != null) {
            datanya1 = getIntent().getParcelableExtra(EXTRA_USERS);
        }
        edit = findViewById(R.id.editbtn);
        delete = findViewById(R.id.deletebtn);
        Username = findViewById(R.id.userName);
        Userage = findViewById(R.id.userAge);
        Useraddress = findViewById(R.id.userAddress);
        Username.setText(datanya1.getNama());
        Userage.setText(datanya1.getUmur());
        Useraddress.setText(datanya1.getAlamat());
        toolbar = findViewById(R.id.tooladd);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(ShowUser.this, MainActivity.class);
                    startActivity(intent);

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowUser.this, AddUser.class);
                intent.putExtra(AddUser.EXTRA_USER, datanya1);
                startActivity(intent);

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Iterator<User> iter = UserArray.data.iterator();
                while (iter.hasNext()) {
                    User user = iter.next();
                    if (user.nama.equals(datanya1.getNama())) {
                        iter.remove();
                    }
                }
                Intent intent = new Intent(ShowUser.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
