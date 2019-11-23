package com.example.monangesmsresponder;
// @source: https://android.jlelse.eu/detecting-sending-sms-on-android-8a154562597f
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    private Intent serviceIntent;

    public int state = -1;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkForSmsPermission();

    }

    public void onRestart() {
        super.onRestart();
        stopService(new Intent(MainActivity.this, NotificationService.class));
        Log.d("restart","ok");
    } // onRestart()


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if (state != -1) {
            serviceIntent = new Intent(MainActivity.this, NotificationService.class);
            serviceIntent.putExtra("state", state);
            serviceIntent.putExtra("state_str", ((Button) findViewById(state)).getText());
            //Log.d("DEBUG","---------------------------------"+mChronometer.getTimeElapsed());
            startService(serviceIntent);
        }
    } // onSaveInstanceState()

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void updateTextUsingLastSms() {
        // TODO le texte doit être mis à jour lors de l'appui d'un bouton
        //Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
    }

// https://google-developer-training.github.io/android-developer-phone-sms-course/Lesson%202/2_p_sending_sms_messages.html
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
            updateTextUsingLastSms();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void buttonClick(View view) {
        switch (view.getId()) {
            case R.id.button_miam:
                // update colors and variable state
                updateState(R.id.button_miam);
                updateButtonsColor(R.id.button_miam);
                //updateTextUsingLastSms();
                //Toast.makeText(this, "Button miam Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_work:
                // update colors and variable state
                updateState(R.id.button_work);
                updateButtonsColor(R.id.button_work);
                //updateTextUsingLastSms();
                //Toast.makeText(this, "Button work Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_shopping:
                // update colors and variable state
                updateState(R.id.button_shopping);
                updateButtonsColor(R.id.button_shopping);
                //updateTextUsingLastSms();
                //Toast.makeText(this, "Button shopping Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_guitar:
                // update colors and variable state
                updateState(R.id.button_guitar);
                updateButtonsColor(R.id.button_guitar);
                //updateTextUsingLastSms();
                //Toast.makeText(this, "Button guitar Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_workout:
                // update colors and variable state
                updateState(R.id.button_workout);
                updateButtonsColor(R.id.button_workout);
                //updateTextUsingLastSms();
                //Toast.makeText(this, "Button workout Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_sleep:
                // update colors and variable state
                updateState(R.id.button_sleep);
                updateButtonsColor(R.id.button_sleep);
                //updateTextUsingLastSms();
                //Toast.makeText(this, "Button sleep Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void updateState(int id) {
        this.state = id;
    }

    public void updateButtonsColor(int id) {
        this.state = id;
        // update all buttons color to gray
        ViewGroup layout = findViewById(R.id.main_layout);
        for (int i = 0; i < layout.getChildCount(); i++) {

            View child = layout.getChildAt(i);
            if(child instanceof Button)
            {
                Button button = (Button) child;
                button.setBackgroundColor(Color.GRAY);
            }
        }
        // TODO update current button state color to green
        Button button = findViewById(this.state);
        button.setBackgroundColor(Color.GREEN);
    }
}
