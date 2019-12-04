package com.example.monangesmsresponder;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.monangesmsresponder.tools.*;

import java.io.Serializable;
import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.monangesmsresponder", appContext.getPackageName());
    }

    @Test
    public void useGetStateUsingRoutine() {
        Tools t = new Tools();
        List<? extends Serializable> state_statestr = t.getStateUsingRoutine();
        int state = (Integer) state_statestr.get(0);
        String state_str = (String) state_statestr.get(1);
        Log.d("Test", String.valueOf(state) + " " + state_str);
    }
}
