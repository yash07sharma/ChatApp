package com.example.chatapp.Views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.chatapp.Models.Message;
import com.example.chatapp.R;

public class MessageView extends LinearLayout {
    Message message;
    String sender;
    TextView messageText;
    CardView messageCard;
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
        Log.d("ChatDebug","this.sender : "+this.sender);
        Log.d("ChatDebug","message.sender : "+message.getSender());
        if(this.sender.equals(message.getSender())) {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            param.gravity = Gravity.RIGHT;
            messageCard.setLayoutParams(param);
            messageCard.setCardBackgroundColor(Color.parseColor("#673AB7"));
            messageText.setTextColor(Color.WHITE);
        }
        else {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            param.gravity = Gravity.LEFT;
            messageCard.setLayoutParams(param);
        }
        messageText.setText(message.getText());
    }
}
