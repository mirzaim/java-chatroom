package com.chatroom.client.view.main;

import javafx.scene.control.ScrollPane;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public final class ChatRoomGUI extends JFrame {
    private final String TITLE = "ChatRoom";
    private final int WIDTH = 500, HEIGHT = 500;
    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    private ChatArea chatArea;
    private LeftLayout leftLayout;
    private BottomLayout bottomLayout;

    private static ChatRoomGUI gui;

    private ChatRoomGUI(NewMessageListener newMessageListener) throws HeadlessException {
        super();

        //for better experience
        setLookAndFeel();

        getUserData();

        setupGUI();

        setNewMessageListener(newMessageListener);
    }

    public static void initGUI(NewMessageListener newMessageListener) {
        if (gui == null)
            gui = new ChatRoomGUI(newMessageListener);
    }

    public static ChatRoomGUI getGUI() {
        return gui;
    }

    public void addNewMessage(String username, String massage) {
        chatArea.addMassage(username, massage);
    }

    public void addNewParticipant(String username) {
        leftLayout.addUser(username);
    }

    public void removeParticipant(String username) {
        leftLayout.removeUser(username);
    }

    private void setNewMessageListener(NewMessageListener newMessageListener) {
        bottomLayout.setNewMessageListener(newMessageListener);
    }

    private void setupGUI() {
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(WIDTH, HEIGHT));
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        chatArea = new ChatArea();
        this.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        this.add(leftLayout = new LeftLayout(), BorderLayout.LINE_START);

        this.add(bottomLayout = new BottomLayout(), BorderLayout.PAGE_END);
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void getUserData() {
        UsernameFrame userData = new UsernameFrame();

        while (!isInputDataProper(userData)) {
            int result = JOptionPane.showConfirmDialog(this, userData,
                    "Fill The Blanks", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.CANCEL_OPTION)
                closeApplication();
        }

    }

    private boolean isInputDataProper(UsernameFrame frame) {
        return !frame.getUserName().isEmpty() &&
                Pattern.compile(IPADDRESS_PATTERN).matcher(frame.getIP()).matches();

    }

    private void closeApplication() {
        System.exit(0);
    }
}
