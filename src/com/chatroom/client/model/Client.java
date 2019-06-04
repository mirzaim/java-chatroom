package com.chatroom.client.model;

import com.chatroom.message.AbstractMessage;
import com.chatroom.message.CommandMessage;
import com.chatroom.message.CommandType;
import com.chatroom.message.TextMessage;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final int PORT = 1234;
    private String username;

    private ServerListener serverListener;
    private NewMessageListener messageListener;

    public Client(String username, String ipAddress, NewMessageListener messageListener) throws IOException {
        Socket socket = new Socket(ipAddress, PORT);
        this.username = username;
        this.messageListener = messageListener;
        new Thread(serverListener = new ServerListener(socket)).start();
    }


    public void sendMessage(AbstractMessage message) {
        serverListener.sendMessage(message);
    }

    public void sendTextMessage(String message) {
        serverListener.sendMessage(new TextMessage(username, message));
    }

    public void close() {
        if (serverListener != null)
            serverListener.sendMessage(new CommandMessage(username, CommandType.CLOSE_CONNECTION));
    }


    private class ServerListener implements Runnable {
        private ObjectInputStream in;
        private ObjectOutputStream out;
        private Socket socket;
        private boolean listen = true;

        private ServerListener(Socket clientSocket) {
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
            while (listen) {
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
        }


        void sendMessage(AbstractMessage message) {
            try {
                out.writeObject(message);
            } catch (IOException e) {
                if (!socket.isClosed())
                    e.printStackTrace();
                else
                    System.out.println("Connection Closed");
            }
        }

        void close() throws IOException {
            listen = false;
            in.close();
            out.close();
            socket.close();
        }

        private void handleMessage(AbstractMessage message) throws IOException {
            switch (message.getMessageType()) {
                case TEXT_MESSAGE:
                    messageListener.newMessage((TextMessage) message);
                    break;
                case COMMAND_MESSAGE:
                    handleCommandMessage((CommandMessage) message);
                    break;
            }
        }

        private void handleCommandMessage(CommandMessage message) throws IOException {
            switch (message.getCommandType()) {
                case CLOSE_CONNECTION:
                    if (message.getSenderUsername().equals("System"))
                        close();
                    break;
                case ADD_ONLINE_USERS:
                    break;
            }
        }

    }
}
