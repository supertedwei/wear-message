package com.supergigi.wearmessage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.TextView;

import com.supergigi.wearmessage.share.MessageData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WearMainActivity extends WearableActivity {

    private BoxInsetLayout mContainerView;
    private TextView text1View;
    private TextView text2View;

    public static final void startActivity(Context context, MessageData messageData) {
        Intent intent = new Intent(context, WearMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("MessageData", messageData);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1View = (TextView) findViewById(R.id.text1);
        text2View = (TextView) findViewById(R.id.text2);

        MessageData messageData = (MessageData) getIntent().getSerializableExtra("MessageData");
        if (messageData != null) {
            text1View.setText(messageData.getText1());
            text2View.setText(messageData.getText2());
        }

    }

}
