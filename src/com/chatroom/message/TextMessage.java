package com.chatroom.message;

public class TextMessage extends AbstractMessage {

    private final String textMessage;

    public TextMessage(String senderUsername, String textMessage) {
        super(senderUsername, MessageType.TEXT_MESSAGE);
        this.textMessage = textMessage;
    }

    public String getTextMessage() {
        return textMessage;
    }
}
