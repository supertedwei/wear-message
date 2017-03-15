package com.supergigi.wearmessage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.supergigi.wearmessage.share.MessageData;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String START_ACTIVITY_PATH = "/start-activity";

    private GoogleApiClient mGoogleApiClient;
    private TextView text1View;
    private TextView text2View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        findViewById(R.id.start_wearable_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStartWearableActivityClick();
            }
        });
        text1View = (TextView) findViewById(R.id.text1);
        text2View = (TextView) findViewById(R.id.text2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onDestroy() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onDestroy();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(LOG_TAG, "onConnected : " + bundle);
//        Wearable.MessageApi.addListener(mGoogleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(LOG_TAG, "onConnectionSuspended : " + i);
//        Wearable.MessageApi.removeListener(mGoogleApiClient, this);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Failed to connect to Google Api Client with error code "
                + connectionResult.getErrorCode());
    }

    private void onStartWearableActivityClick() {
        MessageData messageData = new MessageData();
        messageData.setText1(text1View.getText().toString());
        messageData.setText2(text2View.getText().toString());
        final String messageString = messageData.toJsonString();
        Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).setResultCallback(
            new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                @Override
                public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                    for (final Node node : getConnectedNodesResult.getNodes()) {
                        Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(),
                                START_ACTIVITY_PATH, messageString.getBytes()).setResultCallback(
                                getSendMessageResultCallback());
                    }
                }
            });
    }

    private ResultCallback<MessageApi.SendMessageResult> getSendMessageResultCallback() {
        return new ResultCallback<MessageApi.SendMessageResult>() {
            @Override
            public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                if (!sendMessageResult.getStatus().isSuccess()) {
                    Log.e(LOG_TAG, "Failed to connect to Google Api Client with status "
                            + sendMessageResult.getStatus());
                } else {
                    Log.d(LOG_TAG, "Successful to connect to Google Api Client with status "
                            + sendMessageResult.getStatus());
                }
            }
        };
    }

}


/*



 implements MessageApi.MessageListener,
         {

    private static final String TAG = "DelayedConfirmation";

    private static final String TIMER_SELECTED_PATH = "/timer_selected";
    private static final String TIMER_FINISHED_PATH = "/timer_finished";



    @Override
    public void onMessageReceived(final MessageEvent messageEvent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (messageEvent.getPath().equals(TIMER_SELECTED_PATH)) {
                    Toast.makeText(getApplicationContext(), R.string.toast_timer_selected,
                            Toast.LENGTH_SHORT).show();
                } else if (messageEvent.getPath().equals(TIMER_FINISHED_PATH)) {
                    Toast.makeText(getApplicationContext(), R.string.toast_timer_finished,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }






}

 */
