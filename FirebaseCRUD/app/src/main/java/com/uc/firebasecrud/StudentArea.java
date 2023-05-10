package com.uc.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentArea extends AppCompatActivity {

    Toolbar bar;
    Button btn;
    FirebaseUser mUser;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_area);

        bar = findViewById(R.id.tb_stdarea);
        btn = findViewById(R.id.buttonlogout);

//        mAuth = FirebaseAuth.getInstance();
//        mUser = mAuth.getCurrentUser();

        BottomNavigationView bottomNav = findViewById(R.id.bn_stdarea);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_s:
                    selectedFragment = new ScheduleFragment();
                    bar.setTitle("Schedule");
                    break;
                case R.id.nav_c:
                    selectedFragment = new CourseFragment();
                    bar.setTitle("Course");
                    break;
                case R.id.nav_a:
                    selectedFragment = new AccountFragment();
                    bar.setTitle("Account");
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fl_stdarea, selectedFragment).commit();
            return true;
        }
    };
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case button:
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(MainActivity.this, StartActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                return true;
//        }
//        return false;
//    }


}