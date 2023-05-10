package com.uc.firebasecrud.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.uc.firebasecrud.Model.Student;
import com.uc.firebasecrud.R;
import com.uc.firebasecrud.StudentData;
import com.uc.firebasecrud.AddStudent;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.CardViewViewHolder> {

    private Context context;
    private ArrayList<Student> listStudent;
    private ArrayList<Student> getListStudent() {
        return listStudent;
    }
    public void setListStudent(ArrayList<Student> listStudent) {
        this.listStudent = listStudent;
    }
    public StudentAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public StudentAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_adapter, parent, false);
        return new StudentAdapter.CardViewViewHolder(view);
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder (@NonNull final StudentAdapter.CardViewViewHolder holder, int position) {
        final Student student = getListStudent().get(position);
        holder.iname.setText(student.getName());
        holder.inim.setText(student.getNim());
        holder.iemail.setText(student.getEmail());
        holder.igender.setText(student.getGender());
        holder.iage.setText(student.getAge());
        holder.iaddress.setText(student.getAddress());
    }
    @Override
    public int getItemCount() {
        return getListStudent().size();
    }
    class CardViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView iname, inim, iemail, igender, iage, iaddress;
        OnCardListener onCardListener;
        Button edit, delete;

        CardViewViewHolder(View itemView) {
            super(itemView);
            iname = itemView.findViewById(R.id.std_adapt_name);
            inim = itemView.findViewById(R.id.std_adapt_nim);
            iemail = itemView.findViewById(R.id.std_adapt_email);
            igender = itemView.findViewById(R.id.std_adapt_gender);
            iage = itemView.findViewById(R.id.std_adapt_age);
            iaddress = itemView.findViewById(R.id.std_adapt_address);
            edit = itemView.findViewById(R.id.std_adapt_edit);
            delete = itemView.findViewById(R.id.std_adapt_delete);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context, AddStudent.class);
                    intent.putExtra("action", "edit");
                    intent.putExtra("data_student", listStudent.get(position));
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context, StudentData.class);
                    intent.putExtra("action", "delete");
                    intent.putExtra("data_student", listStudent.get(position));
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
        @Override
        public void onClick(View v) {
            onCardListener.onCardClick(getAdapterPosition());
        }
    }
    public interface OnCardListener {
        void onCardClick (int position);
    }
}