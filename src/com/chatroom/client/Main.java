package com.chatroom.client;

import com.chatroom.client.model.Client;
import com.chatroom.client.view.main.ChatRoomGUI;
import com.chatroom.client.view.main.GUIListener;

import java.io.IOException;

public class Main {
    private static Client client;
    private static ChatRoomGUI gui;

    public static void main(String[] args) {
        ChatRoomGUI.initGUI(new GUIListener() {
            @Override
            public void newMessage(String message) {
                client.sendMessage(message);
            }

            @Override
            public void closeWindowFunc() {
                client.close();
            }
        });

        gui = ChatRoomGUI.getGUI();

        try {
            client = new Client(gui.getIpAddress(),
                    message -> gui.addNewMessage("me", message));
        } catch (IOException e) {
            gui.showErrorAndExit("Can't connect to server.");
        }
    }
}
