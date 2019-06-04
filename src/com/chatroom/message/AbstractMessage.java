package com.chatroom.message;

import java.util.Calendar;

public abstract class AbstractMessage {

    private final String senderUsername;
    private Calendar publishDate;

    public AbstractMessage(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public void setPublishDate(Calendar publishDate) {
        this.publishDate = publishDate;
    }
}
