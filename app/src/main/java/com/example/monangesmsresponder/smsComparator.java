package com.example.monangesmsresponder;

import java.util.Comparator;

public class smsComparator implements Comparator<Sms> {
    @Override
    public int compare(Sms o1, Sms o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
