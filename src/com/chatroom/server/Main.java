package com.chatroom.server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        System.out.println(server.getServerIPAddress());
        try {
            server.runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
