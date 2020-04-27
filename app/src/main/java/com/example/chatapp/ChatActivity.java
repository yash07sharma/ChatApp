package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chatapp.Models.Message;
import com.example.chatapp.Models.User;
import com.example.chatapp.Views.MessageView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity {

    private User sender;
    private User receiver;
    private TextView name;
    private ImageView status;
    private TextView messageText;
    private TextView send;
    private LinearLayout listOfMessages;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference parentRef =  database.getReference("conversations");;
    DatabaseReference conversationRef;

    long messageCount=0;
    boolean isConnectionSetup=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sender = (User)getIntent().getSerializableExtra("sender");
        receiver = (User)getIntent().getSerializableExtra("receiver");

        name = findViewById(R.id.Name);
        status = findViewById(R.id.status);
        messageText = findViewById(R.id.messageText);
        send = findViewById(R.id.send);
        listOfMessages = findViewById(R.id.messages);

        name.setText(receiver.getName());
        status.setImageResource((receiver.getStatus()==1)? R.drawable.active : R.drawable.inactive );

        setupConnection();
        initClickListener();
    }
    private void initClickListener() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(messageText.getText().toString().equals(""))
                    return;
                sendMessage(messageText.getText().toString(),System.currentTimeMillis());
                messageText.setText("");
            }
        });
    }

    private void sendMessage(String text,long time)
    {
        Message message = new Message(text,time,false,sender.getPhone(),receiver.getPhone());
        conversationRef.child(Long.toString(time)).getRef().setValue(message);
    }

    private void addMessage(Message message){
        MessageView messageView= new MessageView(this,message,sender.getPhone());
        listOfMessages.addView(messageView);
    }

    private void setupConnection(){

        parentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("ChatDebug","conversations count: "+dataSnapshot.getChildrenCount());
                String conversationID1 = receiver.getPhone()+" : "+sender.getPhone();
                String conversationID2 = sender.getPhone()+" : "+receiver.getPhone();
                if(dataSnapshot.hasChild(conversationID1)) {
                    conversationRef = parentRef.child(conversationID1).getRef();
                }else if(dataSnapshot.hasChild(conversationID2)){
                    conversationRef = parentRef.child(conversationID2).getRef();
                }
                else
                    conversationRef = parentRef.child(conversationID2).getRef();
                initConversationListener();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void initConversationListener(){
        conversationRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("ChatDebug","conversation data : "+dataSnapshot.getKey()+" : "+dataSnapshot.getValue().toString());
                Log.d("ChatDebug","conversation listener : count : "+dataSnapshot.getChildrenCount());
                Message mess = dataSnapshot.getValue(Message.class);
                addMessage(mess);
                messageCount++;

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
