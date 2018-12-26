package bgu.spl.net.BGS;

public class MessageAbstractFactory {

    public MessageFromClient getFromClient(String opcode)
    {
        return null;
    }

    public MessageFromServer getFromServer(String opcode)
    {
        return null;
    }
}
