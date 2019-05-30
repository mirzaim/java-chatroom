package com.chatroom.client;

import com.chatroom.client.view.main.ChatRoomGUI;

public class Main {

    public static void main(String[] args) {
        ChatRoomGUI gui = new ChatRoomGUI(System.out::println);
        gui.setVisible(true);
        gui.addNewParticipant("Morteza");
        gui.addNewParticipant("Ali");
        gui.addNewParticipant("Mohammad");
        gui.addNewMessage("Morteza", "Hi");
        gui.addNewMessage("Mohammad", "Hello");
        gui.removeParticipant("Ali");
    }
}
