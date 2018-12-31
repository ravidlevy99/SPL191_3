package bgu.spl.net.BGS;

public abstract class MessageFromClient implements Message{

    protected byte[] bytes;
    protected int currentByte;

    public MessageFromClient()
    {
        bytes = new byte[1024];
        currentByte = 0;
    }

    public abstract Message decodeNextByte(byte b);

    public abstract MessageFromServer processMessagefromClient(Message msg, BGSDataBase database);
}
