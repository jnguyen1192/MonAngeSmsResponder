package com.example.monangesmsresponder;

public class Sms {
    private String number;
    private String date;
    private String body;

    public Sms(String number, String date, String body) {
        this.number = number;
        this.date = date;
        this.body = body;
    }

    String getNumber() {
        return this.number;
    }

    String getDate() {
        return this.date;
    }

    String getBody() {
        return this.body;
    }

}

