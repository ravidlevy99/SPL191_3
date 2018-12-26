package bgu.spl.net.BGS;

public class MessageAbstractFactory {

    public Message get(int opcode, String content)
    {
        if(opcode < 9)
            return getFromClient(content);
        else if(opcode < 12)
            return getFromServer(content);
        else
            return null;
    }

    public MessageFromClient getFromClient(String content)
    {
        if(content.startsWith("REGISTER"))
            return new RegsiterMessage(content);
        else if(content.startsWith("LOGIN"))
            return new LoginMessage(content);
        else if(content.startsWith("LOGOUT"))
            return new LogoutMessage();
        else if(content.startsWith("FOLLOW"))
            return new FollowMessage(content);
        else if(content.startsWith("POST"))
            return new PostMessage(content);
        else if(content.startsWith("PM")) //Sliding into his/her DMs
            return new PrivateMessage(content);
        else if(content.startsWith("USERLIST"))
            return new UserListMessage();
        else if(content.startsWith("STAT"))
            return new StatsMessage(content);
        else
            return null;
    }

    public MessageFromServer getFromServer(String content)
    {
        if(content.startsWith("NOTIFICATION"))
            return new NotificationMessage(content);
        else if(content.startsWith("ACK"))
            return new ACKMessage(content);
        else if(content.startsWith("Error"))
            return new ErrorMessage(content);
        else
            return null;
    }
}
