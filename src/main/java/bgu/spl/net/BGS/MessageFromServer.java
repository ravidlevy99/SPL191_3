package bgu.spl.net.BGS;

public abstract class MessageFromServer implements Message{

    protected byte[] bytes;

    public MessageFromServer()
    {
        bytes = new byte[1024];
    }

    public abstract byte[] encode();
}
