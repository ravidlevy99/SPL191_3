package bgu.spl.net.BGS;

public abstract class MessageFromClient implements Message{

    protected byte[] bytes;
    protected int currentByte;

    public MessageFromClient()
    {
        bytes = new byte[1024];
        currentByte = 0;
    }
}
