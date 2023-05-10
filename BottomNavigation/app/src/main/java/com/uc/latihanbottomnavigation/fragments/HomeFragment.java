package com.uc.latihanbottomnavigation.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.uc.latihanbottomnavigation.R;
import com.uc.latihanbottomnavigation.activities.DetailActivity;
import com.uc.latihanbottomnavigation.adapter.CardStudent;
import com.uc.latihanbottomnavigation.model.Student;
import com.uc.latihanbottomnavigation.utils.ItemClickSupport;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class HomeFragment extends Fragment {

    public HomeFragment() {

    }

    private RecyclerView rvStudent;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvStudent = view.findViewById(R.id.rv_student);
        progressBar = view.findViewById(R.id.progressBar);
        showLoading(true);
        getStudent();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void getStudent() {
        final ArrayList<Student> students = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://tryapi101.000webhostapp.com/api/list.php";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("student");
                    for (int i = 0; i < list.length(); i++){
                        JSONObject obj = list.getJSONObject(i);
                        Student s = new Student(obj.getString("id"), obj.getString("nim"), obj.getString("name")
                                , obj.getString("email"), obj.getString("phone"), obj.getString("gender"));
                        students.add(s);
                    }
                    showStudent(students);
                } catch (Exception e){
                    Log.d("ExceptionStudent", "onSuccess: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("OnFailureStudent", "onFailure: " + error.getMessage());
            }
        });
    }

    private void showStudent(final ArrayList<Student> students) {
        rvStudent.setLayoutManager(new LinearLayoutManager(getActivity()));
        CardStudent cardStudent = new CardStudent(getContext());
        cardStudent.setListStudent(students);
        showLoading(false);
        rvStudent.setAdapter(cardStudent);
        ItemClickSupport.addTo(rvStudent).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_STUDENT, students.get(position));
                startActivity(intent);
                Toast.makeText(getContext(), students.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
