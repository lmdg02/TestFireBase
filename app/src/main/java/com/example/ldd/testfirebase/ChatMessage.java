package com.example.ldd.testfirebase;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 17/10/2017.
 */

public class ChatMessage {
    private String id;
    private String messageText;
    private String messageUser;
    private String messageTime;

    public ChatMessage(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);

        // Trả về giá trị từ 0 - 11
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);


        // Initialize to current time
        messageTime =  year + "-" + (month + 1) + "-" + day //
                + " (" + hour + ":" + minute + ":" + second+")" ;
    }

    public ChatMessage(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    @Override
    public String toString() {
        return messageUser + " : " + messageText + messageTime;
    }
}
