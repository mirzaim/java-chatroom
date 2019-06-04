package com.chatroom.client.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private static final int PORT = 1234;

    private ServerListener serverListener;
    private NewMessageListener messageListener;

    public Client(String ipAddress, NewMessageListener messageListener) throws IOException {
        Socket socket = new Socket(ipAddress, PORT);
        this.messageListener = messageListener;
        new Thread(serverListener = new ServerListener(socket)).start();
    }

    public void sendMessage(String message) {
        serverListener.sendMessage(message);
    }

    public void close() {
        if (serverListener != null)
            serverListener.sendMessage("CLOSE_CONNECTION");
    }


    private class ServerListener implements Runnable {
        private DataInputStream in;
        private DataOutputStream out;
        private Socket socket;
        private boolean listen = true;

        private ServerListener(Socket clientSocket) {
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
            while (listen) {
                try {
                    String message = in.readUTF();
                    if (message.equals("CLOSE_CONNECTION"))
                        close();
                    messageListener.newMessage(message);
                } catch (IOException e) {
                    if (!socket.isClosed())
                        e.printStackTrace();
                }
            }
        }


        void sendMessage(String message) {
            try {
                out.writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void close() throws IOException {
            listen = false;
            in.close();
            out.close();
            socket.close();
        }

    }
}
