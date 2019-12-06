package com.example.monangesmsresponder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class NotificationService extends Service {

    private NotificationManager mNM;

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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // prepare data to send to the service
        int state = intent.getIntExtra("state", -1);
        String state_str = intent.getStringExtra("state_str");
        String lastsmsfrommonange = intent.getStringExtra("lastsmsfrommonange");
        boolean once = intent.getBooleanExtra("once", false);
        String once_str = "";
        once_str = intent.getStringExtra("once_str");
        boolean routine = intent.getBooleanExtra("routine", true);
        //Log.d("onStartCommand", String.valueOf(once));
        //Log.d("onStartCommand", once_str);
        // launch responder
        MyReceiver receiver = new MyReceiver(state, lastsmsfrommonange, once, once_str, routine);
        IntentFilter filter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(receiver, filter);
        assert state_str != null;
        if(!routine) {
            Toast.makeText(this,
                    "Mon Ange launched with button " + state_str.toLowerCase() ,
                    Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,
                    "Mon Ange launched with routine",
                    Toast.LENGTH_LONG).show();
        }
        if(state_str != null) {
            mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            showNotification(state_str, once_str, routine);

        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNM.cancel(R.string.app_name);
        Toast.makeText(getApplicationContext(),
                "Mon Ange stopped ",
                Toast.LENGTH_LONG).show();
    }

    /**
     * Show a notification while this service is running.
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showNotification(String state_str, String once_str, boolean routine) {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = "";
        if(routine) {
            text = "Routine";
        }
        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(this, MainActivity.class).setAction(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER), 0);

        // Set the info for the views that show in the notification panel.
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)  // the status icon
                .setTicker(text)  // the status text
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentTitle(state_str)  // the label of the entry
                .setContentText(text)  // the contents of the entry
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicke
                .build();
        notification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
        // Send the notification.
        mNM.notify(R.string.app_name, notification);
    } // showNotification()
}