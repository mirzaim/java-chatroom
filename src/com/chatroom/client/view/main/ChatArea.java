package com.chatroom.client.view.main;

import javax.swing.*;

public class ChatArea extends JTextArea {
    public ChatArea() {
        super();
        this.setEditable(false);
        this.setLineWrap(true);
    }

    void addMassage(String username, String massage) {
        this.append(username + " > " + massage + System.lineSeparator());
        this.setCaretPosition(this.getDocument().getLength());
    }
}
