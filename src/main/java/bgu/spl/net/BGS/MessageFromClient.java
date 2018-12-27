package bgu.spl.net.BGS;

public abstract class MessageFromClient implements Message{

    protected byte[] bytes;

    public MessageFromClient()
    {
        bytes = new byte[1024];
    }
}
