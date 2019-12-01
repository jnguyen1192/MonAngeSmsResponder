package com.example.monangesmsresponder;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.util.Log;
import android.widget.Toast;

public class NotificationService extends Service {

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //Toast.makeText(this,"Service créé", Toast.LENGTH_SHORT).show();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // prepare data to send to the service
        int state = intent.getIntExtra("state", -1);
        String stateStr = intent.getStringExtra("state_str");
        String lastsmsfrommonange = intent.getStringExtra("lastsmsfrommonange");
        boolean once = intent.getBooleanExtra("once", false);
        String once_str = intent.getStringExtra("once_str");
        Log.d("onStartCommand", String.valueOf(once));
        Log.d("onStartCommand", once_str);
        // launch responder
        MyReceiver receiver = new MyReceiver(state, lastsmsfrommonange, once, once_str);
        IntentFilter filter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(receiver, filter);
        assert stateStr != null;
        Toast.makeText(this,
                "MonAngeResponder service launched with button " + stateStr.toLowerCase(),
                Toast.LENGTH_LONG).show();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(getApplicationContext(),
                "MonAngeResponder service stopped ",
                Toast.LENGTH_LONG).show();
    }
}