package com.example.monangesmsresponder.tools;

import android.util.Log;
import android.widget.Button;

import com.example.monangesmsresponder.MainActivity;
import com.example.monangesmsresponder.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Tools {
    public List<String> mTimers = Arrays.asList("0,sleep", "500,work", "600,miam", "730,guitar", "800,workout", "1030,shopping", "1130,miam", "1330,guitar", "1400,workout", "1600,miam", "1630,work", "1800,miam", "1930,guitar", "2000,sleep");

    public Integer getNextTimerToSleep() {
        int datetime_int = currentDateTime();
        int int_timer;
        for(String timer : mTimers) {
            String[] values = timer.split(",");
            ArrayList<String> timer_split = new ArrayList<String>(Arrays.asList(values));
            int_timer = Integer.parseInt(timer_split.get(0));
            if(int_timer > datetime_int) {
                //System.out.println(timer_split.get(1));
                return int_timer - datetime_int;
            }
        }
        return 0;
    }

    public Integer currentDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("HHmm");  //it will give you the date in the formate that is given in the image
        String datetime = dateformat.format(c.getTime()); // it will give you the date
        return Integer.valueOf(datetime);
    }


    public List<? extends Serializable> getStateUsingRoutine() {
        // get current time as hhmmss
        int state = -1;
        String state_str = "";
        int datetime_int = currentDateTime();
        if((datetime_int >= 2000 && datetime_int <= 2359) ||
                (datetime_int >= 0 && datetime_int <= 500)) {
            //Log.d("Test", "Sleep");
            state = R.id.button_sleep;
            state_str = "Sleep";
        }
        if((datetime_int >= 500 && datetime_int <= 559) ||
                (datetime_int >= 1630 && datetime_int <= 1759)) {
            //Log.d("Test", "Work");
            state = R.id.button_work;
            state_str = "Work";
        }
        if((datetime_int >= 600 && datetime_int <= 659) ||
                (datetime_int >= 1130 && datetime_int <= 1329) ||
                (datetime_int >= 1600 && datetime_int <= 1629) ||
                (datetime_int >= 1800 && datetime_int <= 1929)) {
            //Log.d("Test", "Miam");
            state = R.id.button_miam;
            state_str = "Miam";
        }
        if((datetime_int >= 730 && datetime_int <= 759) ||
                (datetime_int >= 1330 && datetime_int <= 1359) ||
                (datetime_int >= 1930 && datetime_int <= 1959)) {
            //Log.d("Test", "Guitar");
            state = R.id.button_guitar;
            state_str = "Guitar";
        }
        if((datetime_int >= 800 && datetime_int <= 1029) ||
                (datetime_int >= 1400 && datetime_int <= 1559)) {
            //Log.d("Test", "Workout");
            state = R.id.button_workout;
            state_str = "Workout";
        }
        if(datetime_int >= 1030 && datetime_int <= 1129) {
            //Log.d("Test", "Shopping");
            state = R.id.button_shopping;
            state_str = "Shopping";
        }
        //Log.d("Test", String.valueOf(currentDateTime()));
        //Log.d("Test", String.valueOf(state));
        //Log.d("Test", state_str);
        //Log.d("Test", "Other");
        return Arrays.asList(state, state_str);
    }
}
