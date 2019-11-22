package com.example.monangesmsresponder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        requestSmsPermission();
    }


    private void requestSmsPermission() {
        String permission = Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }


    public void buttonClick(View view) {
        switch (view.getId()) {
            case R.id.button_miam:
                Toast.makeText(this, "Button miam Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_work:
                Toast.makeText(this, "Button work Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_shopping:
                Toast.makeText(this, "Button shopping Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_guitar:
                Toast.makeText(this, "Button guitar Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_workout:
                Toast.makeText(this, "Button workout Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_meeting:
                Toast.makeText(this, "Button meeting Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }


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
    }
}
