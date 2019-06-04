package com.chatroom.client.view.main;

import javax.swing.*;
import java.awt.*;


class BottomLayout extends JPanel {
    private GUIListener listener;
    private JTextField messageField;
    private JButton sendButton;

    BottomLayout(GUIListener listener) {
        super(new BorderLayout());
        this.listener = listener;

        messageField = new JTextField();
        this.add(messageField, BorderLayout.CENTER);
        messageField.addActionListener(e -> sendMessage());

        sendButton = new JButton("Send");
        this.add(sendButton, BorderLayout.LINE_END);
        sendButton.addActionListener(e -> sendMessage());
    }

    private void sendMessage() {
        if (!messageField.getText().isEmpty())
            listener.newMessage(messageField.getText());
        messageField.setText("");
    }
}
