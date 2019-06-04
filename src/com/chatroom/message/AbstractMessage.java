package com.chatroom.message;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class AbstractMessage implements Serializable {

    private final String senderUsername;
    private Calendar publishDate;

    protected AbstractMessage(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public void setPublishDate(Calendar publishDate) {
        this.publishDate = publishDate;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public Calendar getPublishDate() {
        return publishDate;
    }

    public String getPublishTimeInString(){
        return new SimpleDateFormat("HH:mm").format(publishDate);
    }
}
