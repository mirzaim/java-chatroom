package com.chatroom.client;

import com.chatroom.client.view.main.ChatRoomGUI;

public class Main {

    public static void main(String[] args) {
        ChatRoomGUI.initGUI(message ->
                ChatRoomGUI.getGUI().addNewMessage(ChatRoomGUI.getGUI().getName(), message));
        ChatRoomGUI gui = ChatRoomGUI.getGUI();
        gui.setVisible(true);
        gui.addNewParticipant("Morteza");
        gui.addNewParticipant("Ali");
        gui.addNewParticipant("Mohammad");
        gui.addNewMessage("Morteza", "Hi");
        gui.addNewMessage("Mohammad", "Hello");
        gui.removeParticipant("Ali");
    }
}
