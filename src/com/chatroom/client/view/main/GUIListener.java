package com.chatroom.client.view.main;

/**
 * This interface do main functionality {@code ChatRoomGUI} object.
 * It must be implemented for using {@code ChatRoomGUI} class.
 *
 * @author Morteza Mirzai
 * @see ChatRoomGUI
 */
public interface GUIListener {

    /**
     * This method invokes when client type valid input in message textbox.
     *
     * @param message message that client typed
     */
    void newMessage(String message);

    /**
     * This method invokes when main GUI close button pressed.
     */
    void closeWindowFunc();
}
