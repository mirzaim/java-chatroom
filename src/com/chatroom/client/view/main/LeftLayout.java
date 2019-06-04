package com.chatroom.client.view.main;

import javax.swing.*;
import java.awt.*;

class LeftLayout extends JPanel {

    private DefaultListModel model;
    private JList onlinePeople;

    LeftLayout() {
        super(new BorderLayout());
        JLabel label = new JLabel("Online people:");
        this.add(label, BorderLayout.PAGE_START);
        model = new DefaultListModel();
        onlinePeople = new JList(model);
        this.add(onlinePeople, BorderLayout.CENTER);

        this.setBorder(BorderFactory.createRaisedBevelBorder());
    }

    void addUser(String username) {
        model.addElement(username);
    }

    void removeUser(String username) {
        model.removeElement(username);
    }
}
