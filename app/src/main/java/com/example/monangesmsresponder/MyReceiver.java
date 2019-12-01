package com.example.monangesmsresponder;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyReceiver extends BroadcastReceiver {
    private int state;
    private String lastsmsfrommonange;
    private boolean once;
    private String once_str;
    private Context context;
    // using anonyme number on https://receive-smss.com/sms/33752825043/
    private String num_send = "+33646729562";//"+33752825043";
    private String num_receiv = "+33646729562";

    public MyReceiver(int state, String lastsmsfrommonange, boolean once, String once_str) {
        this.state = state;
        this.lastsmsfrommonange = lastsmsfrommonange;
        this.once = once;
        this.once_str = once_str;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        assert(intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));

        // Read the last sms received
        List<Sms> allSms = readAllSMS(context);
        Sms lastSms = allSms.get(allSms.size() - 1);

        // TODO uncomment num_send to dev
        //num_receiv = "+33672316256";
        // Check if it is a new sms from MonAnge
        if (lastSms.getNumber().equals(num_receiv) && state != -1 && !lastSms.getBody().equals(this.lastsmsfrommonange)) {
            Toast.makeText(context, "Reply sms", Toast.LENGTH_SHORT).show();
            // Respond using the good state from act or once
            if(once && once_str.equals("")) {
                // nothing to do
                Log.d("onReceive", "nothing to do");
                clean_once_variables();
            }
            else if(once && !once_str.equals("")) {
                sendSMSUsingOnce(once_str);
            }
            else {
                sendSMSUsingState(state);
            }
            // Update the last sms from MonAnge
            this.lastsmsfrommonange = lastSms.getBody();
        }
        else {
            Toast.makeText(context, "Sms received from someone else " + state, Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public List<Sms> readAllSMS(Context context) {
        // create a sms list
        List<Sms> allSMS = new ArrayList<>();
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
                    @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                    String strDate = dateFormat.format(date);

                    String type;
                    switch (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(Telephony.Sms.TYPE)))) {
                        case Telephony.Sms.MESSAGE_TYPE_INBOX:
                            type = "inbox";
                            allSMS.add(new Sms(number, strDate, body));
                            java.util.Collections.sort(allSMS, new smsComparator());
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
        return allSMS;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(this.context, "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(this.context, ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void sendSMSUsingState(int state) {
        switch (state) {
            case R.id.button_miam:
                sendSMS(num_send, "Je suis en train de manger mon ange.");
                //Toast.makeText(this, "Button miam Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_work:
                sendSMS(num_send, "Je suis en train de travailler mon ange.");
                //Toast.makeText(this, "Button work Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_shopping:
                sendSMS(num_send, "Je suis en train de faire les courses mon ange.");
                //Toast.makeText(this, "Button shopping Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_guitar:
                sendSMS(num_send, "Je suis en train de faire de la guitare mon ange.");
                //Toast.makeText(this, "Button guitar Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_workout:
                sendSMS(num_send, "Je suis en train de faire du workout mon ange.");
                //Toast.makeText(this, "Button workout Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_sleep:
                sendSMS(num_send, "Je suis en train de dormir mon ange.");
                //Toast.makeText(this, "Button sleep Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void sendSMSUsingOnce(String once_str) {
        sendSMS(num_send, once_str);
        // clean once variables
        clean_once_variables();
    }

    public void clean_once_variables() {
        this.once = !this.once;
        this.once_str = "";
        // Save preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        // Once variables
        editor.putBoolean("once",this.once);
        editor.putString("once_str",this.once_str);
        editor.apply();
    }
}

