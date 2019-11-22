package com.example.monangesmsresponder;
// @source: https://android.jlelse.eu/detecting-sending-sms-on-android-8a154562597f
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    private Context context;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkForSmsPermission();



        //MyReceiver receiver = new MyReceiver(this);

        //IntentFilter filter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        //registerReceiver(receiver, filter);



    }

// https://google-developer-training.github.io/android-developer-phone-sms-course/Lesson%202/2_p_sending_sms_messages.html
    private void enableSmsButton() {
        //ImageButton smsButton = (ImageButton) findViewById(R.id.message_icon);
        //smsButton.setVisibility(View.VISIBLE);
    }

    private void checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            //Log.d(TAG, getString(R.string.permission_not_granted));
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.RECEIVE_SMS,
                                Manifest.permission.SEND_SMS,
                                Manifest.permission.READ_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            // Permission already granted. Enable the SMS button.
            enableSmsButton();
        }
    }


    public void buttonClick(View view) {
        switch (view.getId()) {
            case R.id.button_miam:
                sendSMS("0672316256", "Je suis en train de manger mon ange.");
                //Toast.makeText(this, "Button miam Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_work:
                sendSMS("0672316256", "Je suis en train de travailler mon ange.");
                //Toast.makeText(this, "Button work Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_shopping:
                sendSMS("0672316256", "Je suis en train de faire les courses mon ange.");
                //Toast.makeText(this, "Button shopping Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_guitar:
                sendSMS("0672316256", "Je suis en train de faire de la guitare mon ange.");
                //Toast.makeText(this, "Button guitar Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_workout:
                sendSMS("0672316256", "Je suis en train de faire du workout mon ange.");
                //Toast.makeText(this, "Button workout Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_meeting:
                sendSMS("0672316256", "Je suis en train de dormir mon ange.");
                //Toast.makeText(this, "Button sleep Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void readSMS(Context context) {

        List<String> smsMonAnge = new ArrayList<String>();
        ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        int totalSMS = 0;
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
                            if (number.equals("+33646729562")) {
                                smsMonAnge.add(strDate + " " +body);
                                java.util.Collections.sort(smsMonAnge);
                                //Log.d("ReadSMS", strDate + " " +body);
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
            Toast.makeText(this, "No message to show!", Toast.LENGTH_SHORT).show();
        }

        for (String sms : smsMonAnge) {
            Log.d("readSMS", sms);
        }
        final TextView textViewToChange = (TextView) findViewById(R.id.my_text);
        textViewToChange.setText(
                smsMonAnge.get(smsMonAnge.size() - 1));
        Toast.makeText(this, smsMonAnge.get(0), Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
        readSMS(this.getApplicationContext());
        //readSMS();
    }
}
