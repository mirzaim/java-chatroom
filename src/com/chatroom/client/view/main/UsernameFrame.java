package com.chatroom.client.view.main;

import javax.swing.*;
import java.awt.*;

class UsernameFrame extends JPanel {

    private JTextField nameTextField;
    private JTextField ipTextField;
    private JLabel messageLabel;

    UsernameFrame() throws HeadlessException {
        super();
        this.setLayout(new GridLayout(0, 2));


        JLabel nameLabel = new JLabel("Choose your username: ");
        this.add(nameLabel);
        nameTextField = new JTextField();
        this.add(nameTextField);

        JLabel ipLabel = new JLabel("Enter server's IP: ");
        this.add(ipLabel);
        ipTextField = new JTextField();
        this.add(ipTextField);

        this.add(this.messageLabel = new JLabel(""));

        //delete it in future
        justForTesting();
    }

    String getUserName() {
        return nameTextField.getText();
    }

    String getIP() {
        return ipTextField.getText();
    }

    void setMessage(String message) {
        this.messageLabel.setText(message);
    }

    private void justForTesting() {
//        nameTextField.setText("Morteza");
        ipTextField.setText("172.24.26.139");
    }
}
