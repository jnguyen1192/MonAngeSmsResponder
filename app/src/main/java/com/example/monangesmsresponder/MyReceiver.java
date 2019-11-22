package com.example.monangesmsresponder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class MyReceiver extends BroadcastReceiver {
    private MainActivity act;
    public MyReceiver(MainActivity act) {
        this.act = act;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        assert(intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));
        SmsMessage [] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

        for(SmsMessage message : messages) {
            String body = message.getMessageBody();
            Log.d("Mes messages:", body);
        }
        Log.d("Mes messages:", "JE RECOIS QUELQUE CHOSE");

    }
}

