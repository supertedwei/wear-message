package com.supergigi.wearmessage;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.supergigi.wearmessage.share.MessageData;

/**
 * Created by tedwei on 3/15/17.
 */

public class WearableMessageListenerService extends WearableListenerService {

    private static final String LOG_TAG = WearableMessageListenerService.class.getSimpleName();
    private static final String START_ACTIVITY_PATH = "/start-activity";

    @Override
    public void onMessageReceived(MessageEvent event) {
        Log.d(LOG_TAG, "onMessageReceived : " + event);
        if (event.getPath().equals(START_ACTIVITY_PATH)) {
            byte[] inBytesData = event.getData();
            String jsonString = new String(inBytesData);
            MessageData messageData = MessageData.toMessageData(jsonString);
            WearMainActivity.startActivity(this, messageData);
        }
    }
}
