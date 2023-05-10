package com.uc.firebasecrud;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.uc.firebasecrud.Model.Lecturer;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LecturerDetail extends AppCompatActivity {

    AlphaAnimation klik = new AlphaAnimation(1F, 0.6F);
    Toolbar bar;
    DatabaseReference mDatabase;
    int pos = 0;
    TextView lname, lgender, lexpertise;
    Lecturer lecturer;
    ImageView edit, del;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_detail);

        bar = findViewById(R.id.tb_lectdetail);
        lname = findViewById(R.id.lbl_nama_lect_detail);
        lgender = findViewById(R.id.lbl_gender_lect_detail);
        lexpertise = findViewById(R.id.lbl_expertise_lect_detail);
        edit = findViewById(R.id.imgedit);
        del = findViewById(R.id.imgdelete);
        dialog = GlobalVariable.loadingDialog(LecturerDetail.this);

        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference("lecturer");

        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LecturerDetail.this, LecturerData.class);
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent();
        pos = intent.getIntExtra("position",0);
        lecturer = intent.getParcelableExtra("data_lecturer");

        lname.setText(lecturer.getName());
        lgender.setText(lecturer.getGender());
        lexpertise.setText(lecturer.getExpertise());

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(klik);
                new AlertDialog.Builder(LecturerDetail.this)
                        .setTitle("Confirm")
                        .setIcon(R.drawable.ic_baseline_mood_bad_24)
                        .setMessage("Are you sure to delete "+lecturer.getName()+" data?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int i) {
                                dialog.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.cancel();
                                        mDatabase.child(lecturer.getId()).removeValue(new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                Intent in = new Intent(LecturerDetail.this, LecturerData.class);
                                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                Toast.makeText(LecturerDetail.this, "Delete success!", Toast.LENGTH_SHORT).show();
                                                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LecturerDetail.this);
                                                startActivity(in, options.toBundle());
                                                finish();
                                                dialogInterface.cancel();
                                            }
                                        });

                                    }
                                }, 1000);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create()
                        .show();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(klik);
                Intent in = new Intent(LecturerDetail.this, AddLecturer.class);
                in.putExtra("action", "edit");
                in.putExtra("edit_data_lect", lecturer);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LecturerDetail.this);
                startActivity(in, options.toBundle());
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent;
        intent = new Intent(LecturerDetail.this, LecturerData.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LecturerDetail.this);
        startActivity(intent, options.toBundle());
        finish();
    }
}
