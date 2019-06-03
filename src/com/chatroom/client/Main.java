package com.chatroom.client;

import com.chatroom.client.model.Client;
import com.chatroom.client.view.main.ChatRoomGUI;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        ChatRoomGUI.initGUI();
        ChatRoomGUI gui = ChatRoomGUI.getGUI();
        gui.setVisible(true);

        try {
            Client client = new Client(gui.getIpAddress(), message -> gui.addNewMessage("me", message));
            gui.setNewMessageListener(client::sendMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
