package com.chatroom.server;

import com.chatroom.message.AbstractMessage;
import com.chatroom.message.CommandMessage;
import com.chatroom.message.CommandType;
import com.chatroom.message.TextMessage;

import java.io.*;
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
        while (flag)
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

    private void broadcast(AbstractMessage message) {
        for (ClientHandler clientHandler : clientHandlers)
            clientHandler.sendMessage(message);
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
        private ObjectInputStream in;
        private ObjectOutputStream out;
        private Socket socket;
        private boolean listen = true;

        private ClientHandler(Socket clientSocket) {
            socket = clientSocket;
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            while (listen)
                try {
                    AbstractMessage message = (AbstractMessage) in.readObject();
                    handleMessage(message);
                } catch (IOException e) {
                    if (!socket.isClosed())
                        e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
        }

        private void sendMessage(AbstractMessage message) {
            try {
                out.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void closeConnection() throws IOException {
            sendMessage(new CommandMessage(CommandType.CLOSE_CONNECTION));
            listen = false;
            in.close();
            out.close();
            socket.close();
        }

        private void handleMessage(AbstractMessage message) throws IOException {
            switch (message.getMessageType()) {
                case TEXT_MESSAGE:
                    broadcast(message);
                    break;
                case COMMAND_MESSAGE:
                    handleCommandMessage((CommandMessage) message);
                    break;
            }
        }

        private void handleCommandMessage(CommandMessage message) throws IOException {
            switch (message.getCommandType()) {
                case CLOSE_CONNECTION:
                    closeConnection();
                    clientHandlers.remove(this);
                    System.out.println(this + "closed Connection");
                    break;
                case ADD_ONLINE_USERS:
                    break;
            }
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }

}
