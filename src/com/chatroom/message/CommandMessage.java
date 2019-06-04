package com.chatroom.message;

public class CommandMessage extends AbstractMessage {

    private CommandType commandType;
    private String[] onlineUsers;

    public CommandMessage(String senderUsername, CommandType commandType) {
        super(senderUsername, MessageType.COMMAND_MESSAGE);
        this.commandType = commandType;
    }

    public CommandMessage(CommandType commandType) {
        this("System", commandType);
    }

    private void setOnlineUsers(String[] onlineUsers) {
        this.onlineUsers = onlineUsers.clone();
    }

    public static CommandMessage getAddOnlineUsersCommand(String[] onlineUsers) {
        CommandMessage commandMessage = new CommandMessage(CommandType.ADD_ONLINE_USERS);
        commandMessage.setOnlineUsers(onlineUsers);
        return commandMessage;
    }

    public CommandType getCommandType() {
        return commandType;
    }

}
