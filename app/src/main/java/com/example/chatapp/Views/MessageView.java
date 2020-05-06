package com.example.chatapp.Views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.chatapp.Models.Message;
import com.example.chatapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MessageView extends LinearLayout {
    Message message;
    String sender;
    TextView messageText;
    CardView messageCard;
    TextView tick;
    public MessageView(Context context, Message message, String sender) {
        super(context);
        this.message = message;
        this.sender = sender;
        init();
    }

    public MessageView(Context context, AttributeSet attrs, Message message, String sender) {
        super(context, attrs);
        this.message = message;
        this.sender = sender;
        init();
    }

    public MessageView(Context context, AttributeSet attrs, int defStyle,Message message,String sender) {
        super(context, attrs, defStyle);
        this.message = message;
        this.sender = sender;
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.sample_message_view,this);

        messageText = findViewById(R.id.messageText);
        messageCard = findViewById(R.id.messageCard);
        tick = findViewById(R.id.tick);
        Log.d("ChatDebug","this.sender : "+this.sender);
        Log.d("ChatDebug","message.sender : "+message.getSender());
        if(this.sender.equals(message.getSender())) {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            param.gravity = Gravity.END;
            messageCard.setLayoutParams(param);
            messageCard.setBackground(getResources().getDrawable(R.drawable.sender_message_background,null));
//            messageCard.setCardBackgroundColor(Color.parseColor("#673AB7"));
            messageText.setTextColor(Color.WHITE);
            setSeenListener();
        }
        else {
            tick.setVisibility(View.GONE);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            param.gravity = Gravity.START;
            messageCard.setBackground(getResources().getDrawable(R.drawable.receiver_message_background,null));
            messageCard.setLayoutParams(param);
        }
        messageText.setText(message.getText());
    }

    public void setSeenListener(){
        message.seenRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue().toString().equals("true")) {
                    message.setSeen(true);
                    Log.d("MessageSeen", "Message : "+message.getText()+"Seen : " + message.isSeen());
                    tick.setTextColor(Color.parseColor("#FF31BBFA"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
