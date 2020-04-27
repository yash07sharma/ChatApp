package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chatapp.Models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    private TextView phone;
    private TextView name;
    private Button nextButton;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference parentRef = database.getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone = findViewById(R.id.phone);
        name = findViewById(R.id.name);
        nextButton = findViewById(R.id.Login);
        initListener();
    }
    private void initListener() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phone.getText().toString();
                DatabaseReference ref = parentRef.child(phoneNumber).getRef();
                ref.child("name").setValue(name.getText().toString());
                ref.child("status").setValue(1);//user is active
                User user = new User();
                user.setName(name.getText().toString());
                user.setPhone(phone.getText().toString());
                user.setStatus(1);
                Intent intent = new Intent(Login.this, ShowUsers.class);
                intent.putExtra("current_user", user);
                startActivity(intent);
            }
        });
    }
}
