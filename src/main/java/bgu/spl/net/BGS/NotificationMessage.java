package bgu.spl.net.BGS;

public class NotificationMessage extends MessageFromServer {

    private boolean PMorPublic;
    private String postingUser, content;

    public NotificationMessage(String content)
    {
        super();
    }

    @Override
    public Message decodeNextByte(byte b) {
        return null;
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
