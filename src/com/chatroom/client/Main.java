package com.chatroom.client;

import com.chatroom.client.model.Client;
import com.chatroom.client.view.main.ChatRoomGUI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        ChatRoomGUI.initGUI();
        ChatRoomGUI gui = ChatRoomGUI.getGUI();

        try {
            Client client = new Client(gui.getIpAddress(), message -> gui.addNewMessage("me", message));
            gui.setNewMessageListener(client::sendMessage);

            gui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    client.close();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
