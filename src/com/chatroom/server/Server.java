package com.chatroom.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;

public class Server implements Runnable {
    private static final int PORT = 1234;

    private ServerSocket serverSocket;
    private LinkedList<ClientHandler> clientHandlers;
    private boolean flag = true;

    private Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        clientHandlers = new LinkedList<>();
    }

    public Server() {
        this(PORT);
    }

    @Override
    public void run() {
        while (flag) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket);
                clientHandlers.add(client);
                new Thread(client).start();
                System.out.println("A client connect to server with ip address: " + socket.getInetAddress());
            } catch (IOException e) {
                if (!serverSocket.isClosed())
                    e.printStackTrace();
            }
        }
    }

    public void closeServer() {
        flag = false;
        try {
            for (ClientHandler client : clientHandlers)
                client.closeConnection();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Server closed.");
    }

    private void broadcast(String message) {
        for (ClientHandler clientHandler : clientHandlers)
            clientHandler.sendMessage(message);
        System.out.println(message);
    }


    @Deprecated
    public String getServerIPAddress() {
        try {
            return NetworkInterface.getByName("wlan0").getInetAddresses().nextElement().getHostAddress();
        } catch (SocketException e) {
            return "Couldn't find server ip address";
        }
    }

    private class ClientHandler implements Runnable {
        private DataInputStream in;
        private DataOutputStream out;
        private Socket socket;
        private boolean listen = true;

        public ClientHandler(Socket clientSocket) {
            socket = clientSocket;
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            while (listen)
                try {
                    String message = in.readUTF();
                    if (message.equals("CLOSE_CONNECTION")) {
                        sendMessage(message);
                        closeConnection();
                        clientHandlers.remove(this);
                        System.out.println(this + "closed Connection");
                    } else
                        broadcast(message);
                } catch (IOException e) {
                    if (!socket.isClosed())
                        e.printStackTrace();
                }
        }

        void sendMessage(String message) {
            try {
                out.writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void closeConnection() throws IOException {
            listen = false;
            in.close();
            out.close();
            socket.close();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }

}
