package com.chatroom.client.model;

/**
 * This interface listen to massage come from server.
 *
 * @author Morteza Mirzai
 * @see Client
 */
public interface NewMessageListener {
    /**
     * This method is invoked when new message arrive.
     *
     * @param message The message that is received from server
     */
    void newMessage(String message);
}
