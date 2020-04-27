package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chatapp.Models.User;
import com.example.chatapp.Views.UserItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowUsers extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference parentRef = database.getReference("users");
    List<User> users = new ArrayList<>();
    private LinearLayout listOfUsers;
    private LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private TextView loading;
    private User current_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);

        listOfUsers = findViewById(R.id.listOfUsers);
        loading = findViewById(R.id.Loading);

        current_user = (User) getIntent().getSerializableExtra("current_user");

        retrieveData();
    }

    private void retrieveData() {
        parentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dU : dataSnapshot.getChildren()) {
                    Log.d("Show_Users",dU.getKey()+" : "+dU.getValue().toString());
                    User user = dU.getValue(User.class);
                    if(user!=null) user.setPhone(dU.getKey());
                    users.add(user);
                }
                listUsers();
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void listUsers()
    {
        for(User user : users) {
            if (!user.getPhone().equals(current_user.getPhone())) {
                addToList(user);
            }
        }
    }

    private void addToList(final User user){
        UserItem userItem = new UserItem(this, user);
        userItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowUsers.this, ChatActivity.class);
                intent.putExtra("sender",current_user);
                intent.putExtra("receiver",user);
                startActivity(intent);
            }
        });
        listOfUsers.addView(userItem);
    }
}
