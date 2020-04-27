package com.example.chatapp.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chatapp.Models.User;
import com.example.chatapp.R;

public class UserItem extends LinearLayout {
    private User user;
    private TextView name;
    private ImageView status;
    public UserItem(Context context, User user) {
        super(context);
        this.user = user;
        init();
    }

    public UserItem(Context context, AttributeSet attrs, User user) {
        super(context, attrs);
        this.user = user;
        init();
    }

    public UserItem(Context context, AttributeSet attrs, int defStyle,User user) {
        super(context, attrs, defStyle);
        this.user = user;
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.sample_user_item,this);

        name = findViewById(R.id.Name);
        status = findViewById(R.id.status);

        name.setText(user.getName());
        if(user.getStatus()==0)// inactive
            status.setImageResource(R.drawable.inactive);
        else//active
            status.setImageResource(R.drawable.active);
    }
}
