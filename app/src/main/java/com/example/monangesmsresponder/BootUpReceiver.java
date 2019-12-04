package com.example.monangesmsresponder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.Telephony;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.example.monangesmsresponder.tools.Tools;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BootUpReceiver extends BroadcastReceiver {
    private Intent serviceIntent;

    public int state = -1;
    public String state_str = "";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "MonAnge launched", Toast.LENGTH_LONG).show();
        //Intent i = new Intent(context, Main.class);
        //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //context.startActivity(i);
        Tools t = new Tools();
        List<? extends Serializable> state_statestr = t.getStateUsingRoutine();
        state = (Integer) state_statestr.get(0);
        state_str = (String) state_statestr.get(1);

        //Log.d("Test", String.valueOf(state_statestr.get(0)) + " " + state_statestr.get(1));
        if (state != -1) {
            serviceIntent = new Intent(context, NotificationService.class);
            serviceIntent.putExtra("state", state);
            //serviceIntent.putExtra("state_str", ((Button) findViewById(state)).getText());
            serviceIntent.putExtra("state_str", state_str);//((Button) findViewById(state)).getText());
            // Read the last sms received
            List<Sms> allSms = readAllSMSFromMonAnge(context);
            // get body of last message from MonAnge
            String lastSmsFromAnge = allSms.get(allSms.size() - 1).getBody();
            // put this message on an Extra called "lastsmsfrommonange"
            serviceIntent.putExtra("lastsmsfrommonange", lastSmsFromAnge);

            context.startService(serviceIntent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public List<Sms> readAllSMSFromMonAnge(Context context) {
        // create a sms list
        List<Sms> allSMSFromMonAnge = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        int totalSMS;
        if (c != null) {
            totalSMS = c.getCount();
            if (c.moveToFirst()) {
                for (int j = 0; j < totalSMS; j++) {
                    String smsDate = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE));
                    String number = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                    String body = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY));
                    Date date= new Date(Long.valueOf(smsDate));
                    @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    String strDate = dateFormat.format(date);

                    String type;
                    switch (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(Telephony.Sms.TYPE)))) {
                        case Telephony.Sms.MESSAGE_TYPE_INBOX:
                            type = "inbox";
                            if(number.equals("+33646729562")) {
                                allSMSFromMonAnge.add(new Sms(number, strDate, body));
                                java.util.Collections.sort(allSMSFromMonAnge, new smsComparator());
                            }
                            break;
                        case Telephony.Sms.MESSAGE_TYPE_SENT:
                            type = "sent";
                            break;
                        case Telephony.Sms.MESSAGE_TYPE_OUTBOX:
                            type = "outbox";
                            break;
                        default:
                            break;
                    }
                    c.moveToNext();
                }
            }

            c.close();

        } else {
            Toast.makeText(context, "No message to show!", Toast.LENGTH_SHORT).show();
        }

        // return the sms list
        return allSMSFromMonAnge;
    }
}