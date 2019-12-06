package com.example.monangesmsresponder;

import com.example.monangesmsresponder.tools.Tools;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void getNextTimerToSleep_isCorrect() {

        Tools t = new Tools();
        int sleep_time = t.getNextTimerToSleep();
        System.out.println("Sleep time " + sleep_time + " minutes");
        assertEquals(4, 2 + 2);
    }
}