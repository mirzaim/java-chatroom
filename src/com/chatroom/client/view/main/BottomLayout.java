package com.chatroom.client.view.main;


import com.chatroom.client.NewMessageListener;

import javax.swing.*;
import java.awt.*;


public class BottomLayout extends JPanel {
    private JTextField messageField;
    private JButton sendButton;
    private NewMessageListener newMessageListener;


    public BottomLayout() {
        super(new BorderLayout());

        messageField = new JTextField();
        this.add(messageField, BorderLayout.CENTER);
        messageField.addActionListener(e -> sendMessage());

        sendButton = new JButton("Send");
        this.add(sendButton, BorderLayout.LINE_END);
        sendButton.addActionListener(e -> sendMessage());
    }

    private void sendMessage() {
        if (!messageField.getText().isEmpty())
            newMessageListener.newMessage(messageField.getText());
        messageField.setText("");
    }

    void setNewMessageListener(NewMessageListener newMessageListener) {
        this.newMessageListener = newMessageListener;
    }
}
