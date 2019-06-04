package com.chatroom.client.view.main;

import com.chatroom.client.NewMessageListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

public final class ChatRoomGUI extends JFrame {
    private final String TITLE = "ChatRoom";
    private final int WIDTH = 500, HEIGHT = 500;

    private ChatArea chatArea;
    private LeftLayout leftLayout;
    private BottomLayout bottomLayout;
    private UsernameFrame userData;

    private static ChatRoomGUI gui;

    private GUIListener guiListener;

    private ChatRoomGUI() throws HeadlessException {
        super();

        //for better experience
        setLookAndFeel();

        setupGUI();

        getUserData();

    }

    private ChatRoomGUI(GUIListener guiListener) throws HeadlessException {
        this();

        this.guiListener = guiListener;
    }


    public static void initGUI(GUIListener guiListener) {
        if (gui == null)
            gui = new ChatRoomGUI(guiListener);
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

    public void addNewParticipants(String[] usernames) {
        for (String username : usernames)
            addNewParticipant(username);
    }

    public void removeParticipant(String username) {
        leftLayout.removeUser(username);
    }


    private void setupGUI() {
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(WIDTH, HEIGHT));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guiListener.closeWindowFunc();

            }
        });

        this.add(new JScrollPane(chatArea = new ChatArea()), BorderLayout.CENTER);

        this.add(leftLayout = new LeftLayout(), BorderLayout.LINE_START);

        this.add(bottomLayout = new BottomLayout(guiListener), BorderLayout.PAGE_END);

        this.setVisible(true);
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
        try {
            EventQueue.invokeAndWait(() -> {
                userData = new UsernameFrame();

                while (!userData.isInputDataProper()) {
                    int result = JOptionPane.showConfirmDialog(ChatRoomGUI.this, userData,
                            "Fill The Blanks", JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION)
                        closeApplication();

                }
            });
        } catch (InterruptedException | InvocationTargetException ignored) {
        }

    }

    private void closeApplication() {
        System.exit(0);
    }

    public String getUsername() {
        return userData.getUserName();
    }

    public String getIpAddress() {
        return userData.getIP();
    }

    public void showErrorAndExit(String message) {
        message += "\nTry again later!";
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);

        try {
            guiListener.closeWindowFunc();
        } catch (Exception ignored) {
        }

        closeApplication();
    }
}
