package com.chatroom.server;

import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        System.out.println(server.getServerIPAddress());

        new Thread(server).start();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        server.closeServer();
    }

}
