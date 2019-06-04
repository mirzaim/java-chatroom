package com.chatroom.client.model;

import com.chatroom.client.NewMessageListener;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private static final int PORT = 1234;

    private ListenerToServer serverListener;
    private NewMessageListener messageListener;

    public Client(String ipAddress, NewMessageListener messageListener) throws IOException {
        Socket socket = new Socket(ipAddress, PORT);
        new Thread(serverListener = new ListenerToServer(socket)).start();
        this.messageListener = messageListener;
    }

    public void sendMessage(String message) {
        serverListener.sendMessage(message);
    }

    public void close() {
        serverListener.sendMessage("CLOSE_CONNECTION");
    }


    private class ListenerToServer implements Runnable {
        private DataInputStream in;
        private DataOutputStream out;
        private Socket socket;
        private boolean listen = true;

        public ListenerToServer(Socket clientSocket) {
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
                    if(message.equals("CLOSE_CONNECTION"))
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
