package com.example.monangesmsresponder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.List;

public class MyReceiver extends BroadcastReceiver {
    private MainActivity act;
    public MyReceiver(MainActivity act) {
        this.act = act;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        assert(intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));

        // Read the last sms received
        List<Sms> allSms = act.readAllSMS(context);
        Sms lastSms = allSms.get(allSms.size() - 1);

        // Check if it is a new sms from MonAnge
        if (lastSms.getNumber().equals("+33646729562") && act.state != -1) {
            Toast.makeText(act, "Reply sms", Toast.LENGTH_SHORT).show();
            // TODO respond using the good state from act
            act.sendSMSUsingState(act.state);
        }
        else {
            Toast.makeText(act, "Sms received from someone else", Toast.LENGTH_SHORT).show();
        }
    }
}

